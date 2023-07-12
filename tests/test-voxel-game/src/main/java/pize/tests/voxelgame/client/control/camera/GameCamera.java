package pize.tests.voxelgame.client.control.camera;

import pize.Pize;
import pize.graphics.camera.PerspectiveCamera;
import pize.math.Maths;
import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.main.chunk.LevelChunk;
import pize.tests.voxelgame.main.chunk.storage.ChunkPos;
import pize.tests.voxelgame.client.ClientGame;
import pize.tests.voxelgame.client.entity.LocalPlayer;
import pize.tests.voxelgame.client.options.Options;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.HEIGHT;
import static pize.tests.voxelgame.main.chunk.ChunkUtils.SIZE;

public class GameCamera extends PerspectiveCamera{

    private final ClientGame game;
    
    private final LocalPlayer player;
    private CameraTarget target;
    private final CameraTarget firstPerson, thirdPersonFront, thirdPersonBack;
    private PerspectiveType perspective;
    private float notInterpolatedFov;
    private float zoom = 1;

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
        
        float fov = options.getFOV() / zoom;
        if(player.isSprinting())
            fov *= 1.27F;
        
        setFov(fov);
        
        // Interpolate FOV
        final float currentFOV = getFov();
        super.setFov(currentFOV + (notInterpolatedFov - currentFOV) * Pize.getDt() * 9);
        super.update();
    }
    
    
    public void setDistance(int renderDistanceInChunks){
        setFar(Math.max((renderDistanceInChunks + 0.5F) * SIZE, HEIGHT * 4));
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
            chunkX * SIZE, 0, chunkZ * SIZE,
            chunkX * SIZE + SIZE, HEIGHT, chunkZ * SIZE + SIZE
        );
    }
    
    public boolean isChunkSeen(ChunkPos pos){
        return isChunkSeen(pos.x, pos.z);
    }
    
    public boolean isChunkSeen(LevelChunk chunk){
        ChunkPos pos = chunk.getPosition();
        
        return getFrustum().isBoxInFrustum(
            pos.x * SIZE, 0, pos.z * SIZE,
            pos.x * SIZE + SIZE, (chunk.getHighestSectionIndex() + 1) * SIZE, pos.z * SIZE + SIZE
        );
    }
    
    
    public int chunkX(){
        return Maths.floor(getX() / SIZE);
    }
    
    public int chunkZ(){
        return Maths.floor(getZ() / SIZE);
    }

}
