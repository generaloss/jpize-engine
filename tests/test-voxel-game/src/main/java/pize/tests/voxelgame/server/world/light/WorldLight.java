package pize.tests.voxelgame.server.world.light;

import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.clientserver.chunk.Chunk;
import pize.tests.voxelgame.server.chunk.ServerChunk;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkPos;
import pize.tests.voxelgame.server.world.ServerWorld;
import pize.math.vecmath.vector.Vec2i;
import pize.math.vecmath.vector.Vec3i;
import pize.util.time.Stopwatch;

import java.util.ArrayDeque;
import java.util.Queue;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.*;

public class WorldLight{
    
    public static final Vec2i[] normal2DFromIndex = {new Vec2i(1, 0), new Vec2i(0, 1), new Vec2i(-1, 0), new Vec2i(0, -1)};
    public static final Vec3i[] normal3DFromIndex = {new Vec3i(1, 0, 0), new Vec3i(0, 1, 0), new Vec3i(0, 0, 1), new Vec3i(-1, 0, 0), new Vec3i(0, -1, 0), new Vec3i(0, 0, -1)};
    
    public static float increaseTime, decreaseTime;
    
    
    private final ServerWorld worldOF;
    
    public WorldLight(ServerWorld worldOF){
        this.worldOF = worldOF;
        
        blockLightIncreaseBfsQueue = new ArrayDeque<>();
        blockLightDecreaseBfsQueue = new ArrayDeque<>();
    }
    
    public ServerWorld getWorldOf(){
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
    
    public final Queue<LightNode> blockLightIncreaseBfsQueue;
    
    public void increaseBlockLight(ServerChunk chunkOF, int lx, int ly, int lz, int level){
        if(chunkOF.getBlockLight(lx, ly, lz) < level){
            addInQueueIncrease(chunkOF, lx, ly, lz, level);
            chunkOF.setBlockLight(lx, ly, lz, level);
            
            Stopwatch stopwatch = new Stopwatch().start();
            propagateIncrease();
            increaseTime = stopwatch.getNanos() / 1000000F;
        }
    }
    
    private void addInQueueIncrease(Chunk chunkOF, int lx, int ly, int lz, int level){
        blockLightIncreaseBfsQueue.add(new LightNode(chunkOF, lx, ly, lz, level));
    }
    
    private void propagateIncrease(){
        Chunk neighborChunk;
        int neighborX, neighborY, neighborZ;
        int targetLevel;
        
        while(!blockLightIncreaseBfsQueue.isEmpty()){
            final LightNode lightEntry = blockLightIncreaseBfsQueue.poll();
            
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
    
    public final Queue<LightNode> blockLightDecreaseBfsQueue;
    
    public void decreaseBlockLight(int x, int y, int z, int level){
        decreaseBlockLight(worldOF.getChunkFromBlockPos(x, z), getLocalPos(x), y, getLocalPos(z), level);
    }
    
    public void decreaseBlockLight(ServerChunk chunkOF, int lx, int ly, int lz, int level){
        if(chunkOF.getBlockLight(lx, ly, lz) <= level){
            addInQueueDecrease(chunkOF, lx, ly, lz, level);
            chunkOF.setBlockLight(lx, ly, lz, 0);
            
            Stopwatch stopwatch = new Stopwatch().start();
            propagateDecrease();
            decreaseTime = stopwatch.getNanos() / 1000000F;
        }
    }
    
    private void addInQueueDecrease(Chunk chunkOF, int lx, int ly, int lz, int level){
        blockLightDecreaseBfsQueue.add(new LightNode(chunkOF, lx, ly, lz, level));
    }
    
    private void propagateDecrease(){
        Chunk neighborChunk;
        int neighborX, neighborY, neighborZ;
        
        while(!blockLightDecreaseBfsQueue.isEmpty()){
            final LightNode lightEntry = blockLightDecreaseBfsQueue.poll();
            
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
    
    public void updatePlaceBlockLight(ServerChunk chunk, int lx, int ly, int lz){
        decreaseBlockLight(chunk, lx, ly, lz, chunk.getBlockLight(lx, ly, lz));
    }
    
    public void updateBrokeBlockLight(Chunk chunk, int lx, int ly, int lz){
        Chunk neighborChunk;
        int neighborX, neighborY, neighborZ;
        int maxLevel = 0;
        
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
                maxLevel = Math.max(maxLevel, neighborLevel);
                addInQueueIncrease(neighborChunk, neighborX, neighborY, neighborZ, neighborLevel);
                propagateIncrease();
            }
        }
    }
    
    // OTHER
    
    /** SKY LIGHT **/
    
    public void updateChunkSkyLight(ServerChunk chunk){
        // for(int x = 0; x < SIZE; x++)
        //     for(int z = 0; z < SIZE; z++)
        //         updateChunkSkyLight(chunk, x, z);
        
        // chunk.rebuild();
    }
    
    private void updateSideSkyLight(ServerChunk chunk, int x, int z){
        final ChunkPos chunkPos = chunk.getPosition();
        
        final int height = chunk.getStorage().getHeight(x, z);
        
        for(int i = 0; i < 4; i++){
            Vec2i dirNor = normal2DFromIndex[i];
            final int globalSideX = chunkPos.globalX() + x + dirNor.x;
            final int globalSideZ = chunkPos.globalZ() + z + dirNor.y;
            
            ServerChunk sideChunk = worldOF.getChunkFromBlockPos(globalSideX, globalSideZ);
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
                chunk.setBlock(localSideX, checkY, localSideZ, Block.GLASS.getState(), true);
            }
        }
        
    }
    
    public void updateChunkSkyLight(ServerChunk chunk, int x, int z){
        // int height = chunk.getStorage().getHeight(x, z);
        // for(int y = height + 1; y < HEIGHT; y++)
        //     chunk.getStorage().setSkyLight(x, y, z, MAX_LIGHT_LEVEL);
        
        // updateSideSkyLight(chunk, x, z);
    }
    
}
