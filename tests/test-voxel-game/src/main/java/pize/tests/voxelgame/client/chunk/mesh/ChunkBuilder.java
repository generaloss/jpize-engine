package pize.tests.voxelgame.client.chunk.mesh;

import pize.graphics.texture.Region;
import pize.tests.voxelgame.base.chunk.ChunkUtils;
import pize.tests.voxelgame.base.chunk.LevelChunk;
import pize.tests.voxelgame.base.chunk.storage.HeightmapType;
import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.client.block.model.BlockTextureRegion;
import pize.util.time.Stopwatch;

import java.util.ArrayList;
import java.util.List;

import static pize.tests.voxelgame.base.chunk.ChunkUtils.*;

public class ChunkBuilder{

    public static double buildTime;
    public static int vertexCount;
    
    private static LevelChunk[] neighborChunks;
    private static int vertexIndex;
    private static final List<Float> verticesList = new ArrayList<>();

    public static float[] build(LevelChunk chunk){
        final Stopwatch timer = new Stopwatch().start();

        ChunkBuilder.vertexIndex = 0;
        ChunkBuilder.neighborChunks = new LevelChunk[]{ // Rows - X, Columns - Z
            chunk.getNeighbor(-1, -1), chunk.getNeighbor(0, -1) , chunk.getNeighbor(1, -1),
            chunk.getNeighbor(-1,  0), chunk                    , chunk.getNeighbor(1,  0),
            chunk.getNeighbor(-1,  1), chunk.getNeighbor(0,  1) , chunk.getNeighbor(1,  1)
        };
        
        if(!chunk.isEmpty())
            for(int lx = 0; lx < SIZE; lx++){
                for(int lz = 0; lz < SIZE; lz++){
                    
                    final int height = chunk.getHeightMap(HeightmapType.SURFACE).getHeight(lx, lz) + 1;
                    for(int y = 0; y < height; y++){
                        
                        final BlockProperties block = chunk.getBlockProps(lx, y, lz);
                        if(block.isEmpty())
                            continue;
                        
                        if(block.isSolid()){
                            final BlockTextureRegion region = block.getTextureRegion();
                            if(isGenFace(lx - 1, y,     lz    , block)) addNxFace(lx, y, lz, region.getNx());
                            if(isGenFace(lx + 1, y,     lz    , block)) addPxFace(lx, y, lz, region.getPx());
                            if(isGenFace(lx,     y - 1, lz    , block)) addNyFace(lx, y, lz, region.getNy());
                            if(isGenFace(lx,     y + 1, lz    , block)) addPyFace(lx, y, lz, region.getPy());
                            if(isGenFace(lx,     y,     lz - 1, block)) addNzFace(lx, y, lz, region.getNz());
                            if(isGenFace(lx,     y,     lz + 1, block)) addPzFace(lx, y, lz, region.getPz());
                        }
                    }
                }
            }
        
        // addVertex(0 , 128, 0 , 0, 0, 1);
        // addVertex(16, 128, 0 , 1, 0, 1);
        // addVertex(16, 128, 16, 1, 1, 1);
        // addVertex(16, 128, 16, 1, 1, 1);
        // addVertex(0 , 128, 16, 0, 1, 1);
        // addVertex(0 , 128, 0 , 0, 0, 1);
        
        float[] array = new float[verticesList.size()];
        for(int i = 0; i < array.length; i++)
            array[i] = verticesList.get(i);
        verticesList.clear();
        
        buildTime = timer.getMillis();
        vertexCount = vertexIndex;
        
        return array;
    }
    
    private static boolean isGenFace(int lx, int y, int lz, BlockProperties block){
        if(isOutOfBounds(y))
            return true;
        
        final BlockProperties neighbor = getBlockProps(lx, y, lz);
        return neighbor.isEmpty() || (neighbor.isTransparent() && !block.isTransparent());
    }
    
    
    public static float getAO(int x1, int y1, int z1, int x2, int y2, int z2, int x3, int y3, int z3){
        final BlockProperties block1 = getBlockProps(x1, y1, z1);
        final BlockProperties block2 = getBlockProps(x2, y2, z2);
        final BlockProperties block3 = getBlockProps(x3, y3, z3);
        
        float result = 0;
        if(!(block1.isEmpty() || block1.isTransparent())) result++;
        if(!(block2.isEmpty() || block2.isTransparent())) result++;
        if(!(block3.isEmpty() || block3.isTransparent())) result++;
        
        return 1 - result / 4;
    }
    
    public static float getLight(int x1, int y1, int z1, int x2, int y2, int z2, int x3, int y3, int z3, int x4, int y4, int z4){
        float result = 0;
        byte n = 0;
        
        if(getBlockProps(x1, y1, z1).isTransparent()){
            result += getLight(x1, y1, z1);
            n++;
        }
        
        if(getBlockProps(x2, y2, z2).isTransparent()){
            result += getLight(x2, y2, z2);
            n++;
        }
        
        if(getBlockProps(x3, y3, z3).isTransparent()){
            result += getLight(x3, y3, z3);
            n++;
        }
        
        if(getBlockProps(x4, y4, z4).isTransparent()){
            result += getLight(x4, y4, z4);
            n++;
        }
        
        if(n == 0)
            return 0;
        
        return result / n;
    }
    
    
    private static BlockProperties getBlockProps(int lx, int y, int lz){
        // Находим соседний чанк в массиве 3x3
        int signX = 0;
        int signZ = 0;
        
        if(lx > SIZE_IDX){
            signX = 1;
            lx -= SIZE;
        }else if(lx < 0){
            signX = -1;
            lx += SIZE;
        }
        
        if(lz > SIZE_IDX){
            signZ = 1;
            lz -= SIZE;
        }else if(lz < 0){
            signZ = -1;
            lz += SIZE;
        }
        
        final LevelChunk chunk = neighborChunks[(signZ + 1) * 3 + (signX + 1)];
        if(chunk == null)
            return Block.AIR.properties;
        
        // Возвращаем блок
        return chunk.getBlockProps(lx, y, lz);
    }
    
    private static byte getLight(int lx, int y, int lz){
        // Находим соседний чанк в массиве 3x3
        int signX = 0;
        int signZ = 0;
        
        if(lx > SIZE_IDX){
            signX = 1;
            lx -= SIZE;
        }else if(lx < 0){
            signX = -1;
            lx += SIZE;
        }
        
        if(lz > SIZE_IDX){
            signZ = 1;
            lz -= SIZE;
        }else if(lz < 0){
            signZ = -1;
            lz += SIZE;
        }
        
        final LevelChunk chunk = neighborChunks[(signZ + 1) * 3 + (signX + 1)];
        if(chunk == null)
            return 0;
        
        // Возвращаем уровень света
        return chunk.getLight(lx, y, lz);
    }
    

    private static void addNxFace(final int x, final int y, final int z, final Region region){
        final float light0 = getLight(x-1, y  , z,  x-1, y, z+1,  x-1, y+1, z+1,  x-1, y+1, z  ) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light1 = getLight(x-1, y  , z,  x-1, y, z+1,  x-1, y-1, z+1,  x-1, y-1, z  ) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light2 = getLight(x-1, y  , z,  x-1, y, z-1,  x-1, y-1, z-1,  x-1, y-1, z  ) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light3 = getLight(x-1, y  , z,  x-1, y, z-1,  x-1, y+1, z-1,  x-1, y+1, z  ) / ChunkUtils.MAX_LIGHT_LEVEL;
        
        final float ao0 = getAO(x-1, y+1, z,  x-1, y, z+1,  x-1, y+1, z+1);
        final float ao1 = getAO(x-1, y-1, z,  x-1, y, z+1,  x-1, y-1, z+1);
        final float ao2 = getAO(x-1, y-1, z,  x-1, y, z-1,  x-1, y-1, z-1);
        final float ao3 = getAO(x-1, y+1, z,  x-1, y, z-1,  x-1, y+1, z-1);
        
        final float shadow = 0.8F;
        
        addVertex(x  , y+1, z+1, region.u2f(), region.v1f(), shadow * ao0 * light0);
        addVertex(x  , y  , z+1, region.u2f(), region.v2f(), shadow * ao1 * light1);
        addVertex(x  , y  , z  , region.u1f(), region.v2f(), shadow * ao2 * light2);
        addVertex(x  , y  , z  , region.u1f(), region.v2f(), shadow * ao2 * light2);
        addVertex(x  , y+1, z  , region.u1f(), region.v1f(), shadow * ao3 * light3);
        addVertex(x  , y+1, z+1, region.u2f(), region.v1f(), shadow * ao0 * light0);
    }

    private static void addPxFace(final int x, final int y, final int z, final Region region){
        final float light0 = getLight(x+1, y, z,  x+1, y, z-1,  x+1, y+1, z-1,  x+1, y+1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light1 = getLight(x+1, y, z,  x+1, y, z-1,  x+1, y-1, z-1,  x+1, y-1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light2 = getLight(x+1, y, z,  x+1, y, z+1,  x+1, y-1, z+1,  x+1, y-1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light3 = getLight(x+1, y, z,  x+1, y, z+1,  x+1, y+1, z+1,  x+1, y+1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        
        final float ao0 = getAO(x+1, y+1, z,  x+1, y, z-1,  x+1, y+1, z-1);
        final float ao1 = getAO(x+1, y-1, z,  x+1, y, z-1,  x+1, y-1, z-1);
        final float ao2 = getAO(x+1, y-1, z,  x+1, y, z+1,  x+1, y-1, z+1);
        final float ao3 = getAO(x+1, y+1, z,  x+1, y, z+1,  x+1, y+1, z+1);
        
        final float shadow = 0.8F;
        
        addVertex(x+1, y+1, z  , region.u2f(), region.v1f(), shadow * ao0 * light0);
        addVertex(x+1, y  , z  , region.u2f(), region.v2f(), shadow * ao1 * light1);
        addVertex(x+1, y  , z+1, region.u1f(), region.v2f(), shadow * ao2 * light2);
        addVertex(x+1, y  , z+1, region.u1f(), region.v2f(), shadow * ao2 * light2);
        addVertex(x+1, y+1, z+1, region.u1f(), region.v1f(), shadow * ao3 * light3);
        addVertex(x+1, y+1, z  , region.u2f(), region.v1f(), shadow * ao0 * light0);
    }

    private static void addNyFace(final int x, final int y, final int z, final Region region){
        final float light0 = getLight(x, y-1, z,  x, y-1, z-1,  x+1, y-1, z-1,  x+1, y-1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light1 = getLight(x, y-1, z,  x, y-1, z-1,  x-1, y-1, z-1,  x-1, y-1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light2 = getLight(x, y-1, z,  x, y-1, z+1,  x-1, y-1, z+1,  x-1, y-1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light3 = getLight(x, y-1, z,  x, y-1, z+1,  x+1, y-1, z+1,  x+1, y-1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        
        final float ao0 = getAO(x+1, y-1, z,  x, y-1, z-1,  x+1, y-1, z-1);
        final float ao1 = getAO(x-1, y-1, z,  x, y-1, z-1,  x-1, y-1, z-1);
        final float ao2 = getAO(x-1, y-1, z,  x, y-1, z+1,  x-1, y-1, z+1);
        final float ao3 = getAO(x+1, y-1, z,  x, y-1, z+1,  x+1, y-1, z+1);
        
        final float shadow = 0.6F;
        
        addVertex(x+1, y  , z  , region.u2f(), region.v2f(), shadow * ao0 * light0);
        addVertex(x  , y  , z  , region.u1f(), region.v2f(), shadow * ao1 * light1);
        addVertex(x  , y  , z+1, region.u1f(), region.v1f(), shadow * ao2 * light2);
        addVertex(x  , y  , z+1, region.u1f(), region.v1f(), shadow * ao2 * light2);
        addVertex(x+1, y  , z+1, region.u2f(), region.v1f(), shadow * ao3 * light3);
        addVertex(x+1, y  , z  , region.u2f(), region.v2f(), shadow * ao0 * light0);
    }

    private static void addPyFace(final int x, final int y, final int z, final Region region){
        final float light0 = getLight(x, y+1, z,  x, y+1, z-1,  x-1, y+1, z-1,  x-1, y+1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light1 = getLight(x, y+1, z,  x, y+1, z-1,  x+1, y+1, z-1,  x+1, y+1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light2 = getLight(x, y+1, z,  x, y+1, z+1,  x+1, y+1, z+1,  x+1, y+1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light3 = getLight(x, y+1, z,  x, y+1, z+1,  x-1, y+1, z+1,  x-1, y+1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        
        final float ao0 = getAO(x-1, y+1, z,  x, y+1, z-1,  x-1, y+1, z-1);
        final float ao1 = getAO(x+1, y+1, z,  x, y+1, z-1,  x+1, y+1, z-1);
        final float ao2 = getAO(x+1, y+1, z,  x, y+1, z+1,  x+1, y+1, z+1);
        final float ao3 = getAO(x-1, y+1, z,  x, y+1, z+1,  x-1, y+1, z+1);
        
        final float shadow = 1;
        
        addVertex(x  , y+1, z  , region.u1f(), region.v1f(), shadow * ao0 * light0);
        addVertex(x+1, y+1, z  , region.u2f(), region.v1f(), shadow * ao1 * light1);
        addVertex(x+1, y+1, z+1, region.u2f(), region.v2f(), shadow * ao2 * light2);
        addVertex(x+1, y+1, z+1, region.u2f(), region.v2f(), shadow * ao2 * light2);
        addVertex(x  , y+1, z+1, region.u1f(), region.v2f(), shadow * ao3 * light3);
        addVertex(x  , y+1, z  , region.u1f(), region.v1f(), shadow * ao0 * light0);
    }

    private static void addNzFace(final int x, final int y, final int z, final Region region){
        final float light0 = getLight(x, y, z-1,  x, y-1, z-1,  x-1, y-1, z-1,  x, y-1, z-1) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light1 = getLight(x, y, z-1,  x, y-1, z-1,  x+1, y-1, z-1,  x, y-1, z-1) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light2 = getLight(x, y, z-1,  x, y+1, z-1,  x+1, y+1, z-1,  x, y+1, z-1) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light3 = getLight(x, y, z-1,  x, y+1, z-1,  x-1, y+1, z-1,  x, y+1, z-1) / ChunkUtils.MAX_LIGHT_LEVEL;
        
        final float ao0 = getAO(x-1, y, z-1,  x, y-1, z-1,  x-1, y-1, z-1);
        final float ao1 = getAO(x+1, y, z-1,  x, y-1, z-1,  x+1, y-1, z-1);
        final float ao2 = getAO(x+1, y, z-1,  x, y+1, z-1,  x+1, y+1, z-1);
        final float ao3 = getAO(x-1, y, z-1,  x, y+1, z-1,  x-1, y+1, z-1);
        
        final float shadow = 0.7F;
        
        addVertex(x  , y  , z  , region.u2f(), region.v2f(), shadow * ao0 * light0);
        addVertex(x+1, y  , z  , region.u1f(), region.v2f(), shadow * ao1 * light1);
        addVertex(x+1, y+1, z  , region.u1f(), region.v1f(), shadow * ao2 * light2);
        addVertex(x+1, y+1, z  , region.u1f(), region.v1f(), shadow * ao2 * light2);
        addVertex(x  , y+1, z  , region.u2f(), region.v1f(), shadow * ao3 * light3);
        addVertex(x  , y  , z  , region.u2f(), region.v2f(), shadow * ao0 * light0);
    }

    private static void addPzFace(final int x, final int y, final int z, final Region region){
        final float light0 = getLight(x, y, z+1,  x, y-1, z+1,  x+1, y-1, z+1,  x+1, y, z+1) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light1 = getLight(x, y, z+1,  x, y-1, z+1,  x-1, y-1, z+1,  x-1, y, z+1) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light2 = getLight(x, y, z+1,  x, y+1, z+1,  x-1, y+1, z+1,  x-1, y, z+1) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light3 = getLight(x, y, z+1,  x, y+1, z+1,  x+1, y+1, z+1,  x+1, y, z+1) / ChunkUtils.MAX_LIGHT_LEVEL;
        
        final float ao0 = getAO(x+1, y, z+1,  x, y-1, z+1,  x+1, y-1, z+1);
        final float ao1 = getAO(x-1, y, z+1,  x, y-1, z+1,  x-1, y-1, z+1);
        final float ao2 = getAO(x-1, y, z+1,  x, y+1, z+1,  x-1, y+1, z+1);
        final float ao3 = getAO(x+1, y, z+1,  x, y+1, z+1,  x+1, y+1, z+1);
        
        final float shadow = 0.7F;
    
        addVertex(x+1, y  , z+1, region.u2f(), region.v2f(), shadow * ao0 * light0);
        addVertex(x  , y  , z+1, region.u1f(), region.v2f(), shadow * ao1 * light1);
        addVertex(x  , y+1, z+1, region.u1f(), region.v1f(), shadow * ao2 * light2);
        addVertex(x  , y+1, z+1, region.u1f(), region.v1f(), shadow * ao2 * light2);
        addVertex(x+1, y+1, z+1, region.u2f(), region.v1f(), shadow * ao3 * light3);
        addVertex(x+1, y  , z+1, region.u2f(), region.v2f(), shadow * ao0 * light0);
    }


    private static void addVertex(final int x, final int y, final int z, final float u, final float v,final float light){
        verticesList.add((float) x);
        verticesList.add((float) y);
        verticesList.add((float) z);
        verticesList.add(light);
        verticesList.add(light);
        verticesList.add(light);
        verticesList.add(1F);
        verticesList.add((u));
        verticesList.add((v));
       
        vertexIndex++;
    }

}
