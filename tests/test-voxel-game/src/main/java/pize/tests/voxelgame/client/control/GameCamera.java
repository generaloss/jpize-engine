package pize.tests.voxelgame.client.control;

import pize.graphics.camera.PerspectiveCamera;
import pize.math.Maths;
import pize.tests.voxelgame.client.entity.LocalPlayer;
import pize.tests.voxelgame.clientserver.chunk.Chunk;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkPos;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.HEIGHT;
import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.SIZE;

public class GameCamera extends PerspectiveCamera{

    private final LocalPlayer playerOF;
    
    private CameraTarget target;
    private final CameraTarget firstPerson, thirdPersonFront, thirdPersonBack;
    private PerspectiveType perspective;

    public GameCamera(LocalPlayer playerOF, double near, double far, double fieldOfView){
        super(near, far, fieldOfView);
        this.playerOF = playerOF;
        
        firstPerson = new FirstPersonPlayerCameraTarget(playerOF);
        thirdPersonFront = new ThirdPersonFrontCameraTarget(playerOF);
        thirdPersonBack = new ThirdPersonBackCameraTarget(playerOF);
        
        perspective = PerspectiveType.FIRST_PERSON;
        target = firstPerson;
        
        setImaginaryOrigins(true, false, true);
    }
    
    public LocalPlayer getPlayerOf(){
        return playerOF;
    }


    public void update(){
        if(target == null)
            return;
            
        getPos().set(target.getPosition());
        getRot().set(target.getRotation());
        
        if(playerOF.isOnGround())
            setFov(70);
        
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
    
    
    public boolean isChunkSeen(int chunkX, int chunkZ){
        return getFrustum().isBoxInFrustum(
            chunkX * SIZE, 0, chunkZ * SIZE,
            chunkX * SIZE + SIZE, HEIGHT, chunkZ * SIZE + SIZE
        );
    }
    
    public boolean isChunkSeen(ChunkPos pos){
        return isChunkSeen(pos.x, pos.z);
    }
    
    public boolean isChunkSeen(Chunk chunk){
        ChunkPos pos = chunk.getPosition();
        
        return getFrustum().isBoxInFrustum(
            pos.x * SIZE, 0, pos.z * SIZE,
            pos.x * SIZE + SIZE, chunk.getStorage().getMaxY() + 1, pos.z * SIZE + SIZE
        );
    }
    
    
    public int chunkX(){
        return Maths.floor(getX() / SIZE);
    }
    
    public int chunkZ(){
        return Maths.floor(getZ() / SIZE);
    }

}
