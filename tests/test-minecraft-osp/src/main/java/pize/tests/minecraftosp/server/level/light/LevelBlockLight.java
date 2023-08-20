package pize.tests.minecraftosp.server.level.light;

import pize.math.vecmath.vector.Vec3i;
import pize.tests.minecraftosp.client.block.BlockProps;
import pize.tests.minecraftosp.main.Dir;
import pize.tests.minecraftosp.server.chunk.ServerChunk;
import pize.tests.minecraftosp.server.level.ServerLevel;

import java.util.concurrent.ConcurrentLinkedQueue;

import static pize.tests.minecraftosp.main.chunk.ChunkUtils.*;

public class LevelBlockLight{

    private final ServerLevel level;
    private final ConcurrentLinkedQueue<LightNode> bfsIncreaseQueue, bfsDecreaseQueue;

    public LevelBlockLight(ServerLevel level){
        this.level = level;
        this.bfsIncreaseQueue = new ConcurrentLinkedQueue<>();
        this.bfsDecreaseQueue = new ConcurrentLinkedQueue<>();
    }


    public synchronized void increase(ServerChunk chunk, int lx, int y, int lz, int level){
        if(chunk.getBlockLight(lx, y, lz) >= level)
            return;

        chunk.setBlockLight(lx, y, lz, level);
        addIncrease(chunk, lx, y, lz, level);

        propagateIncrease();
    }

    private synchronized void addIncrease(ServerChunk chunk, int lx, int y, int lz, int level){
        bfsIncreaseQueue.add(new LightNode(chunk, lx, y, lz, level));
    }

    private synchronized void propagateIncrease(){
        ServerChunk neighborChunk;
        int neighborX, neighborY, neighborZ;
        int targetLevel;

        while(!bfsIncreaseQueue.isEmpty()){
            final LightNode lightEntry = bfsIncreaseQueue.poll();

            final ServerChunk chunk = lightEntry.chunk;
            final byte x = lightEntry.lx;
            final short y = lightEntry.y;
            final byte z = lightEntry.lz;
            final byte level = lightEntry.level;

            for(int i = 0; i < 6; i++){
                final Vec3i normal = Dir.values()[i].getNormal();

                neighborX = x + normal.x;
                neighborZ = z + normal.z;

                if(neighborX > SIZE_IDX || neighborZ > SIZE_IDX || neighborX < 0 || neighborZ < 0){
                    neighborChunk = getNeighborChunk(chunk, normal.x, normal.z);
                    if(neighborChunk == null)
                        continue;

                    neighborX = getLocalCoord(neighborX);
                    neighborZ = getLocalCoord(neighborZ);
                }else
                    neighborChunk = chunk;

                neighborY = y + normal.y;
                if(neighborY < 0 || neighborY > HEIGHT_IDX)
                    continue;

                int neighborLevel = neighborChunk.getBlockLight(neighborX, neighborY, neighborZ);
                if(neighborLevel >= level - 1)
                    continue;

                final BlockProps neighborProperties = neighborChunk.getBlockProps(neighborX, neighborY, neighborZ);
                targetLevel = level - Math.max(1, neighborProperties.getOpacity());

                if(targetLevel > neighborLevel){
                    neighborChunk.setBlockLight(neighborX, neighborY, neighborZ, targetLevel);
                    addIncrease(neighborChunk, neighborX, neighborY, neighborZ, targetLevel);
                }
            }
        }
    }


    public synchronized void decrease(ServerChunk chunk, int lx, int y, int lz, int level){
        if(chunk.getBlockLight(lx, y, lz) >= level)
            return;

        chunk.setBlockLight(lx, y, lz, 0);
        addDecrease(chunk, lx, y, lz, level);

        propagateDecrease();
    }

    private synchronized void addDecrease(ServerChunk chunk, int lx, int y, int lz, int level){
        bfsDecreaseQueue.add(new LightNode(chunk, lx, y, lz, level));
    }

    private synchronized void propagateDecrease(){
        ServerChunk neighborChunk;
        int neighborX, neighborY, neighborZ;

        while(!bfsDecreaseQueue.isEmpty()){
            final LightNode lightEntry = bfsDecreaseQueue.poll();

            final ServerChunk chunk = lightEntry.chunk;
            final byte x = lightEntry.lx;
            final short y = lightEntry.y;
            final byte z = lightEntry.lz;
            final byte level = lightEntry.level;

            for(int i = 0; i < 6; i++){
                final Vec3i normal = Dir.normal3DFromIndex(i);

                neighborX = x + normal.x;
                neighborZ = z + normal.z;

                if(neighborX > SIZE_IDX || neighborZ > SIZE_IDX || neighborX < 0 || neighborZ < 0){
                    neighborChunk = getNeighborChunk(chunk, normal.x, normal.z);
                    if(neighborChunk == null)
                        continue;

                    neighborX = getLocalCoord(neighborX);
                    neighborZ = getLocalCoord(neighborZ);
                }else
                    neighborChunk = chunk;

                neighborY = y + normal.y;
                if(neighborY < 0 || neighborY > HEIGHT_IDX)
                    continue;

                int neighborLevel = neighborChunk.getBlockLight(neighborX, neighborY, neighborZ);
                if(neighborLevel != 0 && level > neighborLevel){
                    neighborChunk.setBlockLight(neighborX, neighborY, neighborZ, 0);

                    final BlockProps neighborBlock = neighborChunk.getBlockProps(neighborX, neighborY, neighborZ);

                    addDecrease(neighborChunk, neighborX, neighborY, neighborZ, Math.max(level, neighborLevel + neighborBlock.getOpacity()) );
                }else if(level <= neighborLevel)
                    addIncrease(neighborChunk, neighborX, neighborY, neighborZ, neighborLevel);

            }
        }

        propagateIncrease();
    }

}
