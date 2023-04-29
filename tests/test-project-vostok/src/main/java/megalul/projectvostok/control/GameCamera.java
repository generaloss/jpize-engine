package megalul.projectvostok.control;

import megalul.projectvostok.chunk.Chunk;
import megalul.projectvostok.chunk.storage.ChunkPos;
import pize.graphics.camera.PerspectiveCamera;
import pize.math.Maths;

import static megalul.projectvostok.chunk.ChunkUtils.HEIGHT;
import static megalul.projectvostok.chunk.ChunkUtils.SIZE;

public class GameCamera extends PerspectiveCamera{

    private CameraTarget target;

    public GameCamera(double near, double far, double fieldOfView){
        super(near, far, fieldOfView);
        
        setImaginaryOrigins(true, false, true);
    }


    public void update(){
        if(target == null)
            return;
            
        getPos().set(target.getPosition());
        getRot().set(target.getDirection());
        
        super.update();
    }
    
    
    public void setDistance(int renderDistanceInChunks){
        setFar(Math.max((renderDistanceInChunks + 0.5F) * SIZE, HEIGHT * 4));
    }
    
    
    public void setTarget(CameraTarget target){
        this.target = target;
    }
    
    public CameraTarget getTarget(){
        return target;
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
        ChunkPos pos = chunk.getPos();
        
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
