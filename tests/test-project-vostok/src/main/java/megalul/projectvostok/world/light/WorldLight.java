package megalul.projectvostok.world.light;

import megalul.projectvostok.block.BlockProperties;
import megalul.projectvostok.block.blocks.Block;
import megalul.projectvostok.chunk.Chunk;
import megalul.projectvostok.chunk.storage.ChunkPos;
import megalul.projectvostok.world.World;
import pize.math.vecmath.vector.Vec2i;
import pize.math.vecmath.vector.Vec3i;
import pize.util.time.Stopwatch;

import java.util.ArrayDeque;
import java.util.Queue;

import static megalul.projectvostok.chunk.ChunkUtils.*;

public class WorldLight{
    
    public static final Vec2i[] normal2DFromIndex = {new Vec2i(1, 0), new Vec2i(0, 1), new Vec2i(-1, 0), new Vec2i(0, -1)};
    public static final Vec3i[] normal3DFromIndex = {new Vec3i(1, 0, 0), new Vec3i(0, 1, 0), new Vec3i(0, 0, 1), new Vec3i(-1, 0, 0), new Vec3i(0, -1, 0), new Vec3i(0, 0, -1)};
    
    public static float increaseTime, decreaseTime;
    
    
    private final World worldOF;
    
    public WorldLight(World worldOF){
        this.worldOF = worldOF;
        
        blockLightIncreaseQueue = new ArrayDeque<>();
        blockLightDecreaseQueue = new ArrayDeque<>();
    }
    
    public World getWorldOf(){
        return worldOF;
    }
    
    
    /** BLOCK LIGHT **/
    
    // INCREASE
    
    private static class LightNode{
        
        public final Chunk chunkOF;
        public final byte x;
        public final short y;
        public final byte z;
        public final byte level;
        
        public LightNode(Chunk chunkOF, int x, int y, int z, int level){
            this.chunkOF = chunkOF;
            this.x = (byte) x;
            this.y = (short) y;
            this.z = (byte) z;
            this.level = (byte) level;
        }
    }
    
    public final Queue<LightNode> blockLightIncreaseQueue;
    
    public void increaseBlockLight(int x, int y, int z, int level){
        increaseBlockLight(worldOF.getChunk(x, z), getLocalPos(x), y, getLocalPos(z), level);
    }
    
    public void increaseBlockLight(Chunk chunkOF, int lx, int ly, int lz, int level){
        if(chunkOF.getBlockLight(lx, ly, lz) < level){
            addInQueueIncrease(chunkOF, lx, ly, lz, level);
            chunkOF.setBlockLight(lx, ly, lz, level);
            
            Stopwatch stopwatch = new Stopwatch().start();
            propagateIncrease();
            increaseTime = stopwatch.getNanos() / 1000000F;
            
            rebuildNeighborChunks(chunkOF, lx, lz, level);
        }
    }
    
    private void addInQueueIncrease(Chunk chunkOF, int lx, int ly, int lz, int level){
        blockLightIncreaseQueue.add(new LightNode(chunkOF, lx, ly, lz, level));
    }
    
    private void propagateIncrease(){
        Chunk neighborChunk;
        int neighborX, neighborY, neighborZ;
        int targetLevel;
        
        while(!blockLightIncreaseQueue.isEmpty()){
            final LightNode lightEntry = blockLightIncreaseQueue.poll();
            
            final Chunk chunk = lightEntry.chunkOF;
            final byte x = lightEntry.x;
            final short y = lightEntry.y;
            final byte z = lightEntry.z;
            final byte level = lightEntry.level;
            
            for(int i = 0; i < 6; i++){
                final Vec3i normal = normal3DFromIndex[i];
                
                neighborX = x + normal.x;
                neighborZ = z + normal.z;
                
                if(neighborX > SIZE_IDX || neighborZ > SIZE_IDX || neighborX < 0 || neighborZ < 0){
                    neighborChunk = getNeighborChunk(chunk, normal.x, normal.z);
                    if(neighborChunk == null)
                        continue;
                    
                    neighborX = getLocalPos(neighborX);
                    neighborZ = getLocalPos(neighborZ);
                }else
                    neighborChunk = chunk;
                
                neighborY = y + normal.y;
                if(neighborY < 0 || neighborY > HEIGHT_IDX)
                    continue;
                
                int neighborLevel = neighborChunk.getBlockLight(neighborX, neighborY, neighborZ);
                if(neighborLevel >= level - 1)
                    continue;
                
                final BlockProperties neighborProperties = neighborChunk.getBlockProps(neighborX, neighborY, neighborZ);
                targetLevel = level - Math.max(1, neighborProperties.getOpacity());
                
                if(targetLevel > neighborLevel){
                    neighborChunk.setBlockLight(neighborX, neighborY, neighborZ, targetLevel);
                    addInQueueIncrease(neighborChunk, neighborX, neighborY, neighborZ, targetLevel);
                }
            }
        }
    }
    
    // DECREASE
    
    public final Queue<LightNode> blockLightDecreaseQueue;
    
    public void decreaseBlockLight(int x, int y, int z, int level){
        decreaseBlockLight(worldOF.getChunk(x, z), getLocalPos(x), y, getLocalPos(z), level);
    }
    
    public void decreaseBlockLight(Chunk chunkOF, int lx, int ly, int lz, int level){
        if(chunkOF.getBlockLight(lx, ly, lz) <= level){
            addInQueueDecrease(chunkOF, lx, ly, lz, level);
            chunkOF.setBlockLight(lx, ly, lz, 0);
            
            Stopwatch stopwatch = new Stopwatch().start();
            propagateDecrease();
            decreaseTime = stopwatch.getNanos() / 1000000F;
            
            rebuildNeighborChunks(chunkOF, lx, lz, level);
        }
    }
    
    private void addInQueueDecrease(Chunk chunkOF, int lx, int ly, int lz, int level){
        blockLightDecreaseQueue.add(new LightNode(chunkOF, lx, ly, lz, level));
    }
    
    private void propagateDecrease(){
        Chunk neighborChunk;
        int neighborX, neighborY, neighborZ;
        
        while(!blockLightDecreaseQueue.isEmpty()){
            final LightNode lightEntry = blockLightDecreaseQueue.poll();
            
            final Chunk chunk = lightEntry.chunkOF;
            final byte x = lightEntry.x;
            final short y = lightEntry.y;
            final byte z = lightEntry.z;
            final byte level = lightEntry.level;
            
            for(int i = 0; i < 6; i++){
                final Vec3i normal = normal3DFromIndex[i];
                
                neighborX = x + normal.x;
                neighborZ = z + normal.z;
                
                if(neighborX > SIZE_IDX || neighborZ > SIZE_IDX || neighborX < 0 || neighborZ < 0){
                    neighborChunk = getNeighborChunk(chunk, normal.x, normal.z);
                    if(neighborChunk == null)
                        continue;
                    
                    neighborX = getLocalPos(neighborX);
                    neighborZ = getLocalPos(neighborZ);
                }else
                    neighborChunk = chunk;
                
                neighborY = y + normal.y;
                if(neighborY < 0 || neighborY > HEIGHT_IDX)
                    continue;
                
                int neighborLevel = neighborChunk.getBlockLight(neighborX, neighborY, neighborZ);
                if(neighborLevel != 0 && level > neighborLevel){
                    neighborChunk.setBlockLight(neighborX, neighborY, neighborZ, 0);
                    
                    BlockProperties neighborBlock = neighborChunk.getBlockProps(neighborX, neighborY, neighborZ);
                    
                    addInQueueDecrease(neighborChunk, neighborX, neighborY, neighborZ, Math.max(level, neighborLevel + neighborBlock.getOpacity()) );
                }else if(level <= neighborLevel)
                    addInQueueIncrease(neighborChunk, neighborX, neighborY, neighborZ, neighborLevel);
                
            }
        }
        
        propagateIncrease();
    }
    
    // UPDATE BLOCKS
    
    public void updateBlockLight(Chunk chunk, int lx, int ly, int lz, boolean placed){
        Chunk neighborChunk;
        int neighborX, neighborY, neighborZ;
        
        for(int i = 0; i < 6; i++){
            final Vec3i normal = normal3DFromIndex[i];
            
            neighborX = lx + normal.x;
            neighborZ = lz + normal.z;
            
            if(neighborX > SIZE_IDX || neighborZ > SIZE_IDX || neighborX < 0 || neighborZ < 0){
                neighborChunk = getNeighborChunk(chunk, normal.x, normal.z);
                if(neighborChunk == null)
                    continue;
                
                neighborX = getLocalPos(neighborX);
                neighborZ = getLocalPos(neighborZ);
            }else
                neighborChunk = chunk;
            
            neighborY = ly + normal.y;
            if(neighborY < 0 || neighborY > HEIGHT_IDX)
                continue;
            
            int neighborLevel = neighborChunk.getBlockLight(neighborX, neighborY, neighborZ);
            if(neighborLevel > 1){
                addInQueueIncrease(neighborChunk, neighborX, neighborY, neighborZ, neighborLevel);
                propagateIncrease();
            }
            
        }
        
    }
    
    // OTHER
    
    private void rebuildNeighborChunks(Chunk chunk, int lx, int lz, int level){ //: (HARAM ALGORITHM) and works only with MAX_LIGHT_LEVEL = 15
        boolean nx = lx - level <= 0;
        boolean px = SIZE_IDX - lx - level <= 0;
        boolean nz = lz - level <= 0;
        boolean pz = SIZE_IDX - lz - level <= 0;
        
        Chunk neighbor;
        if(nx){
            neighbor = getNeighborChunk(chunk, -1, 0); if(neighbor != null) neighbor.rebuild();
            if(nz){ neighbor = getNeighborChunk(chunk, -1, -1); if(neighbor != null) neighbor.rebuild();}
            if(pz){ neighbor = getNeighborChunk(chunk, -1, 1); if(neighbor != null) neighbor.rebuild();}
        }
        if(px){
            neighbor = getNeighborChunk(chunk, 1, 0); if(neighbor != null) neighbor.rebuild();
            if(nz){ neighbor = getNeighborChunk(chunk, 1, -1); if(neighbor != null) neighbor.rebuild();}
            if(pz){ neighbor = getNeighborChunk(chunk, 1, 1); if(neighbor != null) neighbor.rebuild();}
        }
        if(nz){
            neighbor = getNeighborChunk(chunk, 0, -1); if(neighbor != null) neighbor.rebuild();
            if(nx){ neighbor = getNeighborChunk(chunk, -1, -1); if(neighbor != null) neighbor.rebuild();}
            if(px){ neighbor = getNeighborChunk(chunk, 1, -1); if(neighbor != null) neighbor.rebuild();}
        }
        if(pz){
            neighbor = getNeighborChunk(chunk, 0, 1); if(neighbor != null) neighbor.rebuild();
            if(nx){ neighbor = getNeighborChunk(chunk, -1, 1); if(neighbor != null) neighbor.rebuild();}
            if(px){ neighbor = getNeighborChunk(chunk, 1, 1); if(neighbor != null) neighbor.rebuild();}
        }
    }
    
    
    /** SKY LIGHT **/
    
    public void updateChunkSkyLight(Chunk chunk){
        // for(int x = 0; x < SIZE; x++)
        //     for(int z = 0; z < SIZE; z++)
        //         updateChunkSkyLight(chunk, x, z);
        
        // chunk.rebuild();
    }
    
    private void updateSideSkyLight(Chunk chunk, int x, int z){
        final ChunkPos chunkPos = chunk.getPos();
        
        final int height = chunk.getStorage().getHeight(x, z);
        
        for(int i = 0; i < 4; i++){
            Vec2i dirNor = normal2DFromIndex[i];
            final int globalSideX = chunkPos.globalX() + x + dirNor.x;
            final int globalSideZ = chunkPos.globalZ() + z + dirNor.y;
            
            Chunk sideChunk = worldOF.getChunk(globalSideX, globalSideZ);
            if(sideChunk == null)
                continue;
            
            final int localSideX = getLocalPos(globalSideX);
            final int localSideZ = getLocalPos(globalSideZ);
            
            final int sideHeight = sideChunk.getStorage().getHeight(localSideX, localSideZ);
            if(sideHeight <= height)
                continue;
            
            int checkY = height + 1;
            if(checkY == sideHeight)
                continue;
            
            for(; checkY < sideHeight; checkY++){
                if(chunk.getBlockID(localSideX, checkY, localSideZ) != Block.AIR.ID)
                    continue;
                
                // increaseBlockLight(sideChunk, localSideX, checkY, localSideZ, 15);
                chunk.setBlock(localSideX, checkY, localSideZ, Block.GLASS.getState());
            }
        }
        
    }
    
    public void updateChunkSkyLight(Chunk chunk, int x, int z){
        // int height = chunk.getStorage().getHeight(x, z);
        // for(int y = height + 1; y < HEIGHT; y++)
        //     chunk.getStorage().setSkyLight(x, y, z, MAX_LIGHT_LEVEL);
        
        // updateSideSkyLight(chunk, x, z);
    }
    
}
