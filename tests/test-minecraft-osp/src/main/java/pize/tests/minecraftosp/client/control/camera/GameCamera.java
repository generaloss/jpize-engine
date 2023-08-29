package pize.tests.minecraftosp.client.control.camera;

import pize.Jize;
import pize.graphics.camera.PerspectiveCamera;
import pize.math.Maths;
import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.physic.Velocity3f;
import pize.tests.minecraftosp.client.ClientGame;
import pize.tests.minecraftosp.client.block.BlockProps;
import pize.tests.minecraftosp.client.block.Blocks;
import pize.tests.minecraftosp.client.chunk.ClientChunk;
import pize.tests.minecraftosp.client.control.camera.perspective.CameraTarget;
import pize.tests.minecraftosp.client.control.camera.perspective.FirstPersonPlayerCameraTarget;
import pize.tests.minecraftosp.client.control.camera.perspective.ThirdPersonBackCameraTarget;
import pize.tests.minecraftosp.client.control.camera.perspective.ThirdPersonFrontCameraTarget;
import pize.tests.minecraftosp.client.entity.LocalPlayer;
import pize.tests.minecraftosp.client.options.Options;
import pize.tests.minecraftosp.main.chunk.ChunkUtils;
import pize.tests.minecraftosp.main.chunk.storage.ChunkPos;

public class GameCamera extends PerspectiveCamera{

    private final ClientGame game;
    
    private final LocalPlayer player;
    private CameraTarget target;
    private final CameraTarget firstPerson, thirdPersonFront, thirdPersonBack;
    private PerspectiveType perspective;
    private float notInterpolatedFov;
    private float zoom = 1;
    private boolean inWater;
    private final Velocity3f hitDirection;

    public GameCamera(ClientGame game, double near, double far, double fieldOfView){
        super(near, far, fieldOfView);
        
        this.game = game;
        this.player = game.getPlayer();
        
        this.firstPerson = new FirstPersonPlayerCameraTarget(player);
        this.thirdPersonFront = new ThirdPersonFrontCameraTarget(player);
        this.thirdPersonBack = new ThirdPersonBackCameraTarget(player);
        
        this.perspective = PerspectiveType.FIRST_PERSON;
        this.target = firstPerson;
        this.hitDirection = new Velocity3f().setMax(3);
        
        setImaginaryOrigins(true, false, true);
    }
    
    public ClientGame getGame(){
        return game;
    }
    
    public LocalPlayer getPlayer(){
        return player;
    }


    public void update(){
        final float deltaTime = Jize.getDt();

        // Follow target
        if(target == null)
            return;
        final Vec3f position = target.getPosition().copy();
        final EulerAngles rotation = target.getRotation().copy();
        
        // Shaking
        // final GameTime gameTime = game.getTime();
        // final float time = gameTime.getSeconds() * 10;

        // final float playerSpeed = player.getVelocity().xz().len() * 20;
        // final float shakingHorizontal = Mathc.sin(time) * 0.02F * playerSpeed;
        // final float shakingVertical = (Mathc.pow(Mathc.sin(time) * 0.02F, 2) - 0.02F) * playerSpeed;
        // final Vec2f shakingShift = new Vec2f(0, shakingHorizontal);
        // shakingShift.rotDeg(rotation.yaw);
        // position.add(shakingShift.x, shakingVertical, shakingShift.y);
        
        // Follow to target
        getPosition().set(position);
        getRotation().set(rotation);
        
        // Player
        final Options options = game.getSession().getOptions();
        
        float fov = options.getFieldOfView() / zoom;
        if(player.isSprinting())
            fov *= 1.27F;
        
        setFov(fov);

        // Directed Hit
        hitDirection.mul(0.8);
        hitDirection.clampToMax();
        final Vec3f hitLocalDirection = hitDirection.copy().rotY(rotation.yaw);
        this.rotation.pitch -= hitLocalDirection.x * 1.5F;
        this.rotation.roll -= hitLocalDirection.z * 1.5F;

        // Jumps
        this.rotation.pitch -= Maths.clamp(player.getVelocity().y / 2, -10, 10);

        // Interpolate FOV
        final float currentFOV = getFov();
        super.setFov(currentFOV + (notInterpolatedFov - currentFOV) * deltaTime * 9);
        this.rotation.clampPitch90();
        super.update();

        // Update is camera in water
        final BlockProps block = game.getLevel().getBlockProps(position.xf(), position.yf(), position.zf());
        inWater = block.getID() == Blocks.WATER.getID();
    }
    
    
    public void setDistance(int renderDistanceInChunks){
        // setFar(Math.max((renderDistanceInChunks + 0.5F) * SIZE, HEIGHT * 4));
    }
    
    
    public PerspectiveType getPerspective(){
        return perspective;
    }
    
    public void setPerspective(PerspectiveType perspective){
        this.perspective = perspective;
        switch(perspective){
            case FIRST_PERSON -> target = firstPerson;
            case THIRD_PERSON_BACK -> target = thirdPersonBack;
            case THIRD_PERSON_FRONT -> target = thirdPersonFront;
        }
    }


    public void push(Vec3f hitDirection){
        this.hitDirection.add(hitDirection.nor());
    }
    
    
    public float getZoom(){
        return zoom;
    }
    
    public void setZoom(float zoom){
        this.zoom = Maths.clamp(zoom, 1, 200);
    }
    
    
    public void setFov(float fieldOfView){
        notInterpolatedFov = fieldOfView;
    }

    
    public boolean isChunkSeen(ClientChunk chunk){
        ChunkPos pos = chunk.getPosition();
        
        return getFrustum().isBoxInFrustum(
            pos.x * ChunkUtils.SIZE,
            0,
            pos.z * ChunkUtils.SIZE,

            pos.x * ChunkUtils.SIZE + ChunkUtils.SIZE,
            (chunk.getMaxY() + 1),
            pos.z * ChunkUtils.SIZE + ChunkUtils.SIZE
        );
    }
    
    
    public int chunkX(){
        return Maths.floor(getX() / ChunkUtils.SIZE);
    }
    
    public int chunkZ(){
        return Maths.floor(getZ() / ChunkUtils.SIZE);
    }


    public boolean isInWater(){
        return inWater;
    }

}
