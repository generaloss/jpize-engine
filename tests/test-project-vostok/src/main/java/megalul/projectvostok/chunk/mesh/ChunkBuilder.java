package megalul.projectvostok.chunk.mesh;

import glit.graphics.util.color.Color;
import glit.util.time.Stopwatch;
import megalul.projectvostok.Main;
import megalul.projectvostok.block.BlockState;
import megalul.projectvostok.block.blocks.Block;
import megalul.projectvostok.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;

import static megalul.projectvostok.chunk.ChunkUtils.SIZE;

public class ChunkBuilder{

    public static final float AO_BRIGHTNESS = 0.75F;
    
    
    private static Chunk chunk;
    private static int vertexIndex;
    private static final List<Float> verticesList = new ArrayList<>();
    private static final Color v_color = new Color(1, 1, 1, 1F);

    public static float[] build(Chunk chunk){
        Stopwatch timer = new Stopwatch().start();
        
        ChunkBuilder.chunk = chunk;
        vertexIndex = 0;

        for(int x = 0; x < SIZE; x++)
            for(int z = 0; z < SIZE; z++){
                final int maxHeight = chunk.getHeight(x, z) + 1;
                final int minHeight = Main.UPDATE_DEPTH_MAP ? chunk.getDepth(x, z) : 0;
                
                for(int y = minHeight; y < maxHeight; y++){
                    final BlockState block = chunk.getBlock(x, y, z);
                    if(block.getProp().isEmpty())
                        continue;

                    if(block.getProp().isSolid()){
                        if(chunk.getBlock(x - 1, y, z).getProp().isEmpty()) addNxFace(x, y, z);
                        if(chunk.getBlock(x + 1, y, z).getProp().isEmpty()) addPxFace(x, y, z);
                        if(chunk.getBlock(x, y - 1, z).getProp().isEmpty()) addNyFace(x, y, z);
                        if(chunk.getBlock(x, y + 1, z).getProp().isEmpty()) addPyFace(x, y, z);
                        if(chunk.getBlock(x, y, z - 1).getProp().isEmpty()) addNzFace(x, y, z);
                        if(chunk.getBlock(x, y, z + 1).getProp().isEmpty()) addPzFace(x, y, z);
                    }
                }
            }
    
        // addVertex(0 , 200, 0 , 0, 0);
        // addVertex(16, 200, 0 , 1, 0);
        // addVertex(16, 200, 16, 1, 1);
        // addVertex(16, 200, 16, 1, 1);
        // addVertex(0 , 200, 16, 0, 1);
        // addVertex(0 , 200, 0 , 0, 0);
        
        float[] array = new float[verticesList.size()];
        for(int i = 0; i < array.length; i++)
            array[i] = verticesList.get(i);

        verticesList.clear();
    
        // System.out.println("Build: " + timer.getMillis() + " (" + vertexIndex + " vertices)");
        
        return array;
    }
    
    
    public static float getAO(int x1, int y1, int z1, int x2, int y2, int z2, int x3, int y3, int z3){
        return
            chunk.fastGetBlockID(x1, y1, z1) != Block.AIR.id ||
            chunk.fastGetBlockID(x2, y2, z2) != Block.AIR.id ||
            chunk.fastGetBlockID(x3, y3, z3) != Block.AIR.id
            ? AO_BRIGHTNESS : 1;
    }
    

    private static void addNxFace(int x, int y, int z){ // No errors
        float ao0 = getAO(x-1, y+1, z,  x-1, y, z+1,  x-1, y+1, z+1);
        float ao1 = getAO(x-1, y-1, z,  x-1, y, z+1,  x-1, y-1, z+1);
        float ao2 = getAO(x-1, y-1, z,  x-1, y, z-1,  x-1, y-1, z-1);
        float ao3 = getAO(x-1, y+1, z,  x-1, y, z-1,  x-1, y+1, z-1);
        
        addVertex(x  , y+1, z+1, 1, 1, ao0 * 0.75F);
        addVertex(x  , y  , z+1, 0, 1, ao1 * 0.75F);
        addVertex(x  , y  , z  , 0, 0, ao2 * 0.75F);
        addVertex(x  , y  , z  , 0, 0, ao2 * 0.75F);
        addVertex(x  , y+1, z  , 1, 0, ao3 * 0.75F);
        addVertex(x  , y+1, z+1, 1, 1, ao0 * 0.75F);
    }

    private static void addPxFace(int x, int y, int z){ // No errors
        float ao0 = getAO(x+1, y+1, z,  x+1, y, z-1,  x+1, y+1, z-1);
        float ao1 = getAO(x+1, y-1, z,  x+1, y, z-1,  x+1, y-1, z-1);
        float ao2 = getAO(x+1, y-1, z,  x+1, y, z+1,  x+1, y-1, z+1);
        float ao3 = getAO(x+1, y+1, z,  x+1, y, z+1,  x+1, y+1, z+1);
    
        addVertex(x+1, y+1, z  , 1, 1, ao0 * 0.75F);
        addVertex(x+1, y  , z  , 0, 1, ao1 * 0.75F);
        addVertex(x+1, y  , z+1, 0, 0, ao2 * 0.75F);
        addVertex(x+1, y  , z+1, 0, 0, ao2 * 0.75F);
        addVertex(x+1, y+1, z+1, 1, 0, ao3 * 0.75F);
        addVertex(x+1, y+1, z  , 1, 1, ao0 * 0.75F);
    }

    private static void addNyFace(int x, int y, int z){ // No errors
        float ao0 = getAO(x+1, y-1, z,  x, y-1, z-1,  x+1, y-1, z-1);
        float ao1 = getAO(x-1, y-1, z,  x, y-1, z-1,  x-1, y-1, z-1);
        float ao2 = getAO(x-1, y-1, z,  x, y-1, z+1,  x-1, y-1, z+1);
        float ao3 = getAO(x+1, y-1, z,  x, y-1, z+1,  x+1, y-1, z+1);
    
        addVertex(x+1, y  , z  , 1, 1, ao0 * 0.7F);
        addVertex(x  , y  , z  , 0, 1, ao1 * 0.7F);
        addVertex(x  , y  , z+1, 0, 0, ao2 * 0.7F);
        addVertex(x  , y  , z+1, 0, 0, ao2 * 0.7F);
        addVertex(x+1, y  , z+1, 1, 0, ao3 * 0.7F);
        addVertex(x+1, y  , z  , 1, 1, ao0 * 0.7F);
    }

    private static void addPyFace(int x, int y, int z){ // No errors
        float ao0 = getAO(x-1, y+1, z,  x, y+1, z-1,  x-1, y+1, z-1);
        float ao1 = getAO(x+1, y+1, z,  x, y+1, z-1,  x+1, y+1, z-1);
        float ao2 = getAO(x+1, y+1, z,  x, y+1, z+1,  x+1, y+1, z+1);
        float ao3 = getAO(x-1, y+1, z,  x, y+1, z+1,  x-1, y+1, z+1);
    
        addVertex(x  , y+1, z  , 0, 0, ao0);
        addVertex(x+1, y+1, z  , 1, 0, ao1);
        addVertex(x+1, y+1, z+1, 1, 1, ao2);
        addVertex(x+1, y+1, z+1, 1, 1, ao2);
        addVertex(x  , y+1, z+1, 0, 1, ao3);
        addVertex(x  , y+1, z  , 0, 0, ao0);
    }

    private static void addNzFace(int x, int y, int z){
        float ao0 = getAO(x-1, y, z-1,  x, y-1, z-1,  x-1, y-1, z-1);
        float ao1 = getAO(x+1, y, z-1,  x, y-1, z-1,  x+1, y-1, z-1);
        float ao2 = getAO(x+1, y, z-1,  x, y+1, z-1,  x+1, y+1, z-1);
        float ao3 = getAO(x-1, y, z-1,  x, y+1, z-1,  x-1, y+1, z-1);
    
        addVertex(x  , y   ,z  , 0, 0, ao0 * 0.9F);
        addVertex(x+1, y   ,z  , 1, 0, ao1 * 0.9F);
        addVertex(x+1, y+1 ,z  , 1, 1, ao2 * 0.9F);
        addVertex(x+1, y+1 ,z  , 1, 1, ao2 * 0.9F);
        addVertex(x  , y+1 ,z  , 0, 1, ao3 * 0.9F);
        addVertex(x  , y   ,z  , 0, 0, ao0 * 0.9F);
    }

    private static void addPzFace(int x, int y, int z){
        float ao0 = getAO(x+1, y, z+1,  x, y-1, z+1,  x+1, y-1, z+1);
        float ao1 = getAO(x-1, y, z+1,  x, y-1, z+1,  x-1, y-1, z+1);
        float ao2 = getAO(x-1, y, z+1,  x, y+1, z+1,  x-1, y+1, z+1);
        float ao3 = getAO(x+1, y, z+1,  x, y+1, z+1,  x+1, y+1, z+1);
    
        addVertex(x+1, y  , z+1, 0, 0, ao0 * 0.9F);
        addVertex(x  , y  , z+1, 1, 0, ao1 * 0.9F);
        addVertex(x  , y+1, z+1, 1, 1, ao2 * 0.9F);
        addVertex(x  , y+1, z+1, 1, 1, ao2 * 0.9F);
        addVertex(x+1, y+1, z+1, 0, 1, ao3 * 0.9F);
        addVertex(x+1, y  , z+1, 0, 0, ao0 * 0.9F);
    }


    private static void addVertex(float x, float y, float z, float u, float v, float ao){
        verticesList.add(x);
        verticesList.add(y);
        verticesList.add(z);
        verticesList.add(v_color.r() * ao);
        verticesList.add(v_color.g() * ao);
        verticesList.add(v_color.b() * ao);
        verticesList.add(v_color.a());
        verticesList.add((u));// + 1) / 3);
        verticesList.add((v));// + 1) / 3);

        vertexIndex++;
    }

}
