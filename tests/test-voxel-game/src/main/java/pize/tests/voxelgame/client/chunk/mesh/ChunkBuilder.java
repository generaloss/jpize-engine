package pize.tests.voxelgame.client.chunk.mesh;

import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.model.BlockTextureRegion;
import pize.tests.voxelgame.clientserver.chunk.Chunk;
import pize.tests.voxelgame.clientserver.chunk.ChunkUtils;
import pize.graphics.texture.Region;
import pize.util.time.Stopwatch;

import java.util.ArrayList;
import java.util.List;

public class ChunkBuilder{

    public static final float AO_BRIGHTNESS = 0.7F;
    
    public static double buildTime;
    public static int vertexCount;
    
    private static Chunk chunk;
    private static int vertexIndex;
    private static final List<Float> verticesList = new ArrayList<>();

    public static float[] build(Chunk chunk){
        Stopwatch timer = new Stopwatch().start();
        
        ChunkBuilder.chunk = chunk;
        vertexIndex = 0;
        
        if(!chunk.getStorage().isEmpty())
            for(int x = 0; x < ChunkUtils.SIZE; x++)
                for(int z = 0; z < ChunkUtils.SIZE; z++){
                    final int maxHeight = chunk.getStorage().getHeight(x, z) + 1;
                    
                    for(int y = 0; y < maxHeight; y++){
                        final BlockProperties block = chunk.getBlockProps(x, y, z);
                        if(block.isEmpty())
                            continue;
    
                        if(block.isSolid()){
                            final BlockTextureRegion region = block.getTextureRegion();
                            
                            if(isGenFace(x - 1, y, z, block)) addNxFace(x, y, z, region.getNx());
                            if(isGenFace(x + 1, y, z, block)) addPxFace(x, y, z, region.getPx());
                            if(isGenFace(x, y - 1, z, block)) addNyFace(x, y, z, region.getNy());
                            if(isGenFace(x, y + 1, z, block)) addPyFace(x, y, z, region.getPy());
                            if(isGenFace(x, y, z - 1, block)) addNzFace(x, y, z, region.getNz());
                            if(isGenFace(x, y, z + 1, block)) addPzFace(x, y, z, region.getPz());
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
    
    private static boolean isGenFace(int x, int y, int z, BlockProperties block){
        BlockProperties neighbor = chunk.getBlockProps(x, y, z);
        
        return
            neighbor.isEmpty() ||
            (neighbor.isTransparent() && !block.isTransparent());
    }
    
    
    public static float getAO(int x1, int y1, int z1, int x2, int y2, int z2, int x3, int y3, int z3){
        BlockProperties block1 = chunk.getBlockProps(x1, y1, z1);
        BlockProperties block2 = chunk.getBlockProps(x2, y2, z2);
        BlockProperties block3 = chunk.getBlockProps(x3, y3, z3);
        
        return
            !(block1.isEmpty() || block1.isTransparent()) ||
            !(block2.isEmpty() || block2.isTransparent()) ||
            !(block3.isEmpty() || block3.isTransparent())
            ? AO_BRIGHTNESS : 1;
    }
    
    /* public static float getLight(int x1, int y1, int z1, int x2, int y2, int z2, int x3, int y3, int z3, int x4, int y4, int z4){
        float result = 0;
        byte n = 0;
        
        if(chunk.getBlockProps(x1, y1, z1).isTransparent()){
            result += chunk.getLight(x1, y1, z1);
            n++;
        }
        
        if(chunk.getBlockProps(x2, y2, z2).isTransparent()){
            result += chunk.getLight(x2, y2, z2);
            n++;
        }
        
        if(chunk.getBlockProps(x3, y3, z3).isTransparent()){
            result += chunk.getLight(x3, y3, z3);
            n++;
        }
        
        if(chunk.getBlockProps(x4, y4, z4).isTransparent()){
            result += chunk.getLight(x4, y4, z4);
            n++;
        }
        
        if(n == 0)
            return 0;
        
        return result / n;
    } */
    

    private static void addNxFace(final int x, final int y, final int z, final Region region){
        // final float light0 = getLight(x-1, y  , z,  x-1, y, z+1,  x-1, y+1, z+1,  x-1, y+1, z  ) / ChunkUtils.MAX_LIGHT_LEVEL;
        // final float light1 = getLight(x-1, y  , z,  x-1, y, z+1,  x-1, y-1, z+1,  x-1, y-1, z  ) / ChunkUtils.MAX_LIGHT_LEVEL;
        // final float light2 = getLight(x-1, y  , z,  x-1, y, z-1,  x-1, y-1, z-1,  x-1, y-1, z  ) / ChunkUtils.MAX_LIGHT_LEVEL;
        // final float light3 = getLight(x-1, y  , z,  x-1, y, z-1,  x-1, y+1, z-1,  x-1, y+1, z  ) / ChunkUtils.MAX_LIGHT_LEVEL;
        
        final float ao0 = getAO(x-1, y+1, z,  x-1, y, z+1,  x-1, y+1, z+1);
        final float ao1 = getAO(x-1, y-1, z,  x-1, y, z+1,  x-1, y-1, z+1);
        final float ao2 = getAO(x-1, y-1, z,  x-1, y, z-1,  x-1, y-1, z-1);
        final float ao3 = getAO(x-1, y+1, z,  x-1, y, z-1,  x-1, y+1, z-1);
        
        final float shadow = 0.8F;
        
        addVertex(x  , y+1, z+1, region.u2f(), region.v1f(), shadow * ao0); // * light0);
        addVertex(x  , y  , z+1, region.u2f(), region.v2f(), shadow * ao1); // * light1);
        addVertex(x  , y  , z  , region.u1f(), region.v2f(), shadow * ao2); // * light2);
        addVertex(x  , y  , z  , region.u1f(), region.v2f(), shadow * ao2); // * light2);
        addVertex(x  , y+1, z  , region.u1f(), region.v1f(), shadow * ao3); // * light3);
        addVertex(x  , y+1, z+1, region.u2f(), region.v1f(), shadow * ao0); // * light0);
    }

    private static void addPxFace(final int x, final int y, final int z, final Region region){
        // final float light0 = getLight(x+1, y, z,  x+1, y, z-1,  x+1, y+1, z-1,  x+1, y+1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        // final float light1 = getLight(x+1, y, z,  x+1, y, z-1,  x+1, y-1, z-1,  x+1, y-1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        // final float light2 = getLight(x+1, y, z,  x+1, y, z+1,  x+1, y-1, z+1,  x+1, y-1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        // final float light3 = getLight(x+1, y, z,  x+1, y, z+1,  x+1, y+1, z+1,  x+1, y+1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        
        final float ao0 = getAO(x+1, y+1, z,  x+1, y, z-1,  x+1, y+1, z-1);
        final float ao1 = getAO(x+1, y-1, z,  x+1, y, z-1,  x+1, y-1, z-1);
        final float ao2 = getAO(x+1, y-1, z,  x+1, y, z+1,  x+1, y-1, z+1);
        final float ao3 = getAO(x+1, y+1, z,  x+1, y, z+1,  x+1, y+1, z+1);
        
        final float shadow = 0.8F;
        
        addVertex(x+1, y+1, z  , region.u2f(), region.v1f(), shadow * ao0); // * light0);
        addVertex(x+1, y  , z  , region.u2f(), region.v2f(), shadow * ao1); // * light1);
        addVertex(x+1, y  , z+1, region.u1f(), region.v2f(), shadow * ao2); // * light2);
        addVertex(x+1, y  , z+1, region.u1f(), region.v2f(), shadow * ao2); // * light2);
        addVertex(x+1, y+1, z+1, region.u1f(), region.v1f(), shadow * ao3); // * light3);
        addVertex(x+1, y+1, z  , region.u2f(), region.v1f(), shadow * ao0); // * light0);
    }

    private static void addNyFace(final int x, final int y, final int z, final Region region){
        // final float light0 = getLight(x, y-1, z,  x, y-1, z-1,  x+1, y-1, z-1,  x+1, y-1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        // final float light1 = getLight(x, y-1, z,  x, y-1, z-1,  x-1, y-1, z-1,  x-1, y-1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        // final float light2 = getLight(x, y-1, z,  x, y-1, z+1,  x-1, y-1, z+1,  x-1, y-1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        // final float light3 = getLight(x, y-1, z,  x, y-1, z+1,  x+1, y-1, z+1,  x+1, y-1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        
        final float ao0 = getAO(x+1, y-1, z,  x, y-1, z-1,  x+1, y-1, z-1);
        final float ao1 = getAO(x-1, y-1, z,  x, y-1, z-1,  x-1, y-1, z-1);
        final float ao2 = getAO(x-1, y-1, z,  x, y-1, z+1,  x-1, y-1, z+1);
        final float ao3 = getAO(x+1, y-1, z,  x, y-1, z+1,  x+1, y-1, z+1);
        
        final float shadow = 0.6F;
        
        addVertex(x+1, y  , z  , region.u2f(), region.v2f(), shadow * ao0); // * light0);
        addVertex(x  , y  , z  , region.u1f(), region.v2f(), shadow * ao1); // * light1);
        addVertex(x  , y  , z+1, region.u1f(), region.v1f(), shadow * ao2); // * light2);
        addVertex(x  , y  , z+1, region.u1f(), region.v1f(), shadow * ao2); // * light2);
        addVertex(x+1, y  , z+1, region.u2f(), region.v1f(), shadow * ao3); // * light3);
        addVertex(x+1, y  , z  , region.u2f(), region.v2f(), shadow * ao0); // * light0);
    }

    private static void addPyFace(final int x, final int y, final int z, final Region region){
        // final float light0 = getLight(x, y+1, z,  x, y+1, z-1,  x-1, y+1, z-1,  x-1, y+1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        // final float light1 = getLight(x, y+1, z,  x, y+1, z-1,  x+1, y+1, z-1,  x+1, y+1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        // final float light2 = getLight(x, y+1, z,  x, y+1, z+1,  x+1, y+1, z+1,  x+1, y+1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        // final float light3 = getLight(x, y+1, z,  x, y+1, z+1,  x-1, y+1, z+1,  x-1, y+1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        
        final float ao0 = getAO(x-1, y+1, z,  x, y+1, z-1,  x-1, y+1, z-1);
        final float ao1 = getAO(x+1, y+1, z,  x, y+1, z-1,  x+1, y+1, z-1);
        final float ao2 = getAO(x+1, y+1, z,  x, y+1, z+1,  x+1, y+1, z+1);
        final float ao3 = getAO(x-1, y+1, z,  x, y+1, z+1,  x-1, y+1, z+1);
        
        final float shadow = 1;
        
        addVertex(x  , y+1, z  , region.u1f(), region.v1f(), shadow * ao0); // * light0);
        addVertex(x+1, y+1, z  , region.u2f(), region.v1f(), shadow * ao1); // * light1);
        addVertex(x+1, y+1, z+1, region.u2f(), region.v2f(), shadow * ao2); // * light2);
        addVertex(x+1, y+1, z+1, region.u2f(), region.v2f(), shadow * ao2); // * light2);
        addVertex(x  , y+1, z+1, region.u1f(), region.v2f(), shadow * ao3); // * light3);
        addVertex(x  , y+1, z  , region.u1f(), region.v1f(), shadow * ao0); // * light0);
    }

    private static void addNzFace(final int x, final int y, final int z, final Region region){
        // final float light0 = getLight(x, y, z-1,  x, y-1, z-1,  x-1, y-1, z-1,  x, y-1, z-1) / ChunkUtils.MAX_LIGHT_LEVEL;
        // final float light1 = getLight(x, y, z-1,  x, y-1, z-1,  x+1, y-1, z-1,  x, y-1, z-1) / ChunkUtils.MAX_LIGHT_LEVEL;
        // final float light2 = getLight(x, y, z-1,  x, y+1, z-1,  x+1, y+1, z-1,  x, y+1, z-1) / ChunkUtils.MAX_LIGHT_LEVEL;
        // final float light3 = getLight(x, y, z-1,  x, y+1, z-1,  x-1, y+1, z-1,  x, y+1, z-1) / ChunkUtils.MAX_LIGHT_LEVEL;
        
        final float ao0 = getAO(x-1, y, z-1,  x, y-1, z-1,  x-1, y-1, z-1);
        final float ao1 = getAO(x+1, y, z-1,  x, y-1, z-1,  x+1, y-1, z-1);
        final float ao2 = getAO(x+1, y, z-1,  x, y+1, z-1,  x+1, y+1, z-1);
        final float ao3 = getAO(x-1, y, z-1,  x, y+1, z-1,  x-1, y+1, z-1);
        
        final float shadow = 0.7F;
        
        addVertex(x  , y  , z  , region.u2f(), region.v2f(), shadow * ao0); // * light0);
        addVertex(x+1, y  , z  , region.u1f(), region.v2f(), shadow * ao1); // * light1);
        addVertex(x+1, y+1, z  , region.u1f(), region.v1f(), shadow * ao2); // * light2);
        addVertex(x+1, y+1, z  , region.u1f(), region.v1f(), shadow * ao2); // * light2);
        addVertex(x  , y+1, z  , region.u2f(), region.v1f(), shadow * ao3); // * light3);
        addVertex(x  , y  , z  , region.u2f(), region.v2f(), shadow * ao0); // * light0);
    }

    private static void addPzFace(final int x, final int y, final int z, final Region region){
        // final float light0 = getLight(x, y, z+1,  x, y-1, z+1,  x+1, y-1, z+1,  x+1, y, z+1) / ChunkUtils.MAX_LIGHT_LEVEL;
        // final float light1 = getLight(x, y, z+1,  x, y-1, z+1,  x-1, y-1, z+1,  x-1, y, z+1) / ChunkUtils.MAX_LIGHT_LEVEL;
        // final float light2 = getLight(x, y, z+1,  x, y+1, z+1,  x-1, y+1, z+1,  x-1, y, z+1) / ChunkUtils.MAX_LIGHT_LEVEL;
        // final float light3 = getLight(x, y, z+1,  x, y+1, z+1,  x+1, y+1, z+1,  x+1, y, z+1) / ChunkUtils.MAX_LIGHT_LEVEL;
        
        final float ao0 = getAO(x+1, y, z+1,  x, y-1, z+1,  x+1, y-1, z+1);
        final float ao1 = getAO(x-1, y, z+1,  x, y-1, z+1,  x-1, y-1, z+1);
        final float ao2 = getAO(x-1, y, z+1,  x, y+1, z+1,  x-1, y+1, z+1);
        final float ao3 = getAO(x+1, y, z+1,  x, y+1, z+1,  x+1, y+1, z+1);
        
        final float shadow = 0.7F;
    
        addVertex(x+1, y  , z+1, region.u2f(), region.v2f(), shadow * ao0); // * light0);
        addVertex(x  , y  , z+1, region.u1f(), region.v2f(), shadow * ao1); // * light1);
        addVertex(x  , y+1, z+1, region.u1f(), region.v1f(), shadow * ao2); // * light2);
        addVertex(x  , y+1, z+1, region.u1f(), region.v1f(), shadow * ao2); // * light2);
        addVertex(x+1, y+1, z+1, region.u2f(), region.v1f(), shadow * ao3); // * light3);
        addVertex(x+1, y  , z+1, region.u2f(), region.v2f(), shadow * ao0); // * light0);
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
