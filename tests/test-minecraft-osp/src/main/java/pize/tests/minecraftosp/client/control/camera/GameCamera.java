package pize.tests.minecraftosp.client.control.camera;

import pize.Pize;
import pize.graphics.camera.PerspectiveCamera;
import pize.math.Maths;
import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.minecraftosp.client.ClientGame;
import pize.tests.minecraftosp.client.block.BlockProperties;
import pize.tests.minecraftosp.client.block.Blocks;
import pize.tests.minecraftosp.client.options.Options;
import pize.tests.minecraftosp.main.chunk.ChunkUtils;
import pize.tests.minecraftosp.main.chunk.LevelChunk;
import pize.tests.minecraftosp.main.chunk.storage.ChunkPos;
import pize.tests.minecraftosp.client.entity.LocalPlayer;

public class GameCamera extends PerspectiveCamera{

    private final ClientGame game;
    
    private final LocalPlayer player;
    private CameraTarget target;
    private final CameraTarget firstPerson, thirdPersonFront, thirdPersonBack;
    private PerspectiveType perspective;
    private float notInterpolatedFov;
    private float zoom = 1;
    private boolean inWater;

    public GameCamera(ClientGame game, double near, double far, double fieldOfView){
        super(near, far, fieldOfView);
        
        this.game = game;
        this.player = game.getPlayer();
        
        this.firstPerson = new FirstPersonPlayerCameraTarget(player);
        this.thirdPersonFront = new ThirdPersonFrontCameraTarget(player);
        this.thirdPersonBack = new ThirdPersonBackCameraTarget(player);
        
        this.perspective = PerspectiveType.FIRST_PERSON;
        this.target = firstPerson;
        
        setImaginaryOrigins(true, false, true);
    }
    
    public ClientGame getGame(){
        return game;
    }
    
    public LocalPlayer getPlayer(){
        return player;
    }


    public void update(){
        // Follow target
        if(target == null)
            return;
        final Vec3f position = target.getPosition().copy();
        final EulerAngles rotation = target.getRotation().copy();
        
        // Shaking
        // final float playerSpeed = (float) player.getMotion().xz().len();
        // time += playerSpeed * 3;
        // final float shakingHorizontal = Mathc.sin(time) * 0.02F;
        // final float shakingVertical = Mathc.pow(Mathc.sin(time) * 0.02F, 2) - 0.02F;
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
        
        // Interpolate FOV
        final float currentFOV = getFov();
        super.setFov(currentFOV + (notInterpolatedFov - currentFOV) * Pize.getDt() * 9);
        super.update();

        // Update is camera in water
        final BlockProperties block = game.getLevel().getBlockProps(position.xf(), position.yf(), position.zf());
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
    
    
    public float getZoom(){
        return zoom;
    }
    
    public void setZoom(float zoom){
        this.zoom = Maths.clamp(zoom, 1, 200);
    }
    
    
    public void setFov(float fieldOfView){
        notInterpolatedFov = fieldOfView;
    }
    
    
    public boolean isChunkSeen(int chunkX, int chunkZ){
        return getFrustum().isBoxInFrustum(
            chunkX * ChunkUtils.SIZE, 0, chunkZ * ChunkUtils.SIZE,
            chunkX * ChunkUtils.SIZE + ChunkUtils.SIZE, ChunkUtils.HEIGHT, chunkZ * ChunkUtils.SIZE + ChunkUtils.SIZE
        );
    }
    
    public boolean isChunkSeen(ChunkPos pos){
        return isChunkSeen(pos.x, pos.z);
    }
    
    public boolean isChunkSeen(LevelChunk chunk){
        ChunkPos pos = chunk.getPosition();
        
        return getFrustum().isBoxInFrustum(
            pos.x * ChunkUtils.SIZE, 0, pos.z * ChunkUtils.SIZE,
            pos.x * ChunkUtils.SIZE + ChunkUtils.SIZE, (chunk.getHighestSectionIndex() + 1) * ChunkUtils.SIZE, pos.z * ChunkUtils.SIZE + ChunkUtils.SIZE
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
