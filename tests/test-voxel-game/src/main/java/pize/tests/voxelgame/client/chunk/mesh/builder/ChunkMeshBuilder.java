package pize.tests.voxelgame.client.chunk.mesh.builder;

import pize.Pize;
import pize.graphics.texture.Region;
import pize.math.Maths;
import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.Blocks;
import pize.tests.voxelgame.client.block.model.BlockCustomModel;
import pize.tests.voxelgame.client.block.model.BlockModel;
import pize.tests.voxelgame.client.block.model.BlockSolidModel;
import pize.tests.voxelgame.client.chunk.ClientChunk;
import pize.tests.voxelgame.client.chunk.mesh.ChunkMeshStack;
import pize.tests.voxelgame.main.chunk.ChunkUtils;
import pize.tests.voxelgame.main.chunk.LevelChunk;
import pize.tests.voxelgame.main.chunk.storage.Heightmap;
import pize.tests.voxelgame.main.chunk.storage.HeightmapType;
import pize.util.time.Stopwatch;

import java.util.ArrayList;
import java.util.List;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.*;

public class ChunkMeshBuilder{

    public static double buildTime;
    public static int verticesNum;
    
    private static ClientChunk[] neighborChunks;
    private static int solidVertexIndex, customVertexIndex;
    private static final List<Integer> solidVertices = new ArrayList<>();
    private static final List<Float> customVertices = new ArrayList<>();

    public static void build(ClientChunk chunk, ChunkMeshStack meshStack){
        final Stopwatch timer = new Stopwatch().start();

        // Init
        ChunkMeshBuilder.solidVertexIndex = 0;
        ChunkMeshBuilder.customVertexIndex = 0;
        ChunkMeshBuilder.neighborChunks = new ClientChunk[]{ // Rows - X, Columns - Z
            chunk.getNeighbor(-1, -1), chunk.getNeighbor(0, -1) , chunk.getNeighbor(1, -1),
            chunk.getNeighbor(-1,  0), chunk                    , chunk.getNeighbor(1,  0),
            chunk.getNeighbor(-1,  1), chunk.getNeighbor(0,  1) , chunk.getNeighbor(1,  1)
        };
        
        // Build
        final Heightmap heightmap = chunk.getHeightMap(HeightmapType.SURFACE);
        
        if(!chunk.isEmpty())
            for(int lx = 0; lx < SIZE; lx++){
                for(int lz = 0; lz < SIZE; lz++){
                    
                    final int height = heightmap.getHeight(lx, lz) + 1;
                    for(int y = 0; y < height; y++){
                        
                        final BlockProperties block = chunk.getBlockProps(lx, y, lz);
                        if(block.isEmpty())
                            continue;
                        
                        final BlockModel model = block.getModel();
                        if(model == null)
                            continue;
                        
                        if(block.isSolid()){
                            final BlockSolidModel solidModel = model.asSolid();
                            
                            if(isGenSolidFace(lx - 1, y,     lz    , block)) addSolidNxFace(lx, y, lz, solidModel.getSideNxRegion());
                            if(isGenSolidFace(lx + 1, y,     lz    , block)) addSolidPxFace(lx, y, lz, solidModel.getSidePxRegion());
                            if(isGenSolidFace(lx,     y - 1, lz    , block)) addSolidNyFace(lx, y, lz, solidModel.getSideNyRegion());
                            if(isGenSolidFace(lx,     y + 1, lz    , block)) addSolidPyFace(lx, y, lz, solidModel.getSidePyRegion());
                            if(isGenSolidFace(lx,     y,     lz - 1, block)) addSolidNzFace(lx, y, lz, solidModel.getSideNzRegion());
                            if(isGenSolidFace(lx,     y,     lz + 1, block)) addSolidPzFace(lx, y, lz, solidModel.getSidePzRegion());
                        }else{
                            final float[] add = new float[]{lx, y, lz, 0, 0, 0, 0, 0, 0};
                            final float light = chunk.getLight(lx, y, lz) / 15F;
                            final float[] mul = new float[]{1, 1, 1, light, light, light, 1, 1, 1};
                            final BlockCustomModel customModel = model.asCustom();
                            for(int i = 0; i < customModel.getVertices().size(); i++){
                                float vertex = customModel.getVertices().get(i) * mul[i % 9] + add[i % 9];
                                customVertices.add(vertex);
                            }
                            customVertexIndex += 12;
                        }
                    }
                }
            }
        
        // Solid vertices list to array
        final int[] solidVerticesArray = new int[solidVertices.size()];
        for(int i = 0; i < solidVerticesArray.length; i++)
            solidVerticesArray[i] = solidVertices.get(i);
        
        Pize.execSync(()->meshStack.setDataSolid(solidVerticesArray));
        solidVertices.clear();
        
        // Custom vertices list to array
        final float[] customVerticesArray = new float[customVertices.size()];
        for(int i = 0; i < customVerticesArray.length; i++)
            customVerticesArray[i] = customVertices.get(i);
        
        Pize.execSync(()->meshStack.setDataCustom(customVerticesArray));
        customVertices.clear();
        
        // Time / Vertices
        buildTime = timer.getMillis();
        verticesNum = solidVertexIndex + customVertexIndex;
    }
    
    private static boolean isGenSolidFace(int lx, int y, int lz, BlockProperties block){
        if(isOutOfBounds(y))
            return true;
        
        final BlockProperties neighbor = getBlockProps(lx, y, lz);
        if(neighbor.getID() == Blocks.VOID_AIR.getID())
            return false;
        
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
            return Blocks.VOID_AIR;
        
        // Возвращаем блок
        return chunk.getBlockProps(lx, y, lz);
    }
    
    private static byte getLight(int lx, int y, int lz){
        // Находим соседний чанк в массиве 3x3 (neighborChunks)
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
            return 15;
        
        // Возвращаем уровень света
        return chunk.getLight(lx, y, lz);
    }
    

    private static void addSolidNxFace(final int x, final int y, final int z, final Region region){
        final float light0 = getLight(x-1, y  , z,  x-1, y, z+1,  x-1, y+1, z+1,  x-1, y+1, z  ) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light1 = getLight(x-1, y  , z,  x-1, y, z+1,  x-1, y-1, z+1,  x-1, y-1, z  ) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light2 = getLight(x-1, y  , z,  x-1, y, z-1,  x-1, y-1, z-1,  x-1, y-1, z  ) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light3 = getLight(x-1, y  , z,  x-1, y, z-1,  x-1, y+1, z-1,  x-1, y+1, z  ) / ChunkUtils.MAX_LIGHT_LEVEL;
        
        final float ao0 = getAO(x-1, y+1, z,  x-1, y, z+1,  x-1, y+1, z+1);
        final float ao1 = getAO(x-1, y-1, z,  x-1, y, z+1,  x-1, y-1, z+1);
        final float ao2 = getAO(x-1, y-1, z,  x-1, y, z-1,  x-1, y-1, z-1);
        final float ao3 = getAO(x-1, y+1, z,  x-1, y, z-1,  x-1, y+1, z-1);
        
        final float shadow = 0.8F;
        
        final float brightness0 = shadow * ao0 * light0;
        final float brightness1 = shadow * ao1 * light1;
        final float brightness2 = shadow * ao2 * light2;
        final float brightness3 = shadow * ao3 * light3;
        
        if(brightness1 + brightness3 > brightness0 + brightness2){ // Flip (change vertex order)
            addSolidVertex(x  , y+1, z  , region.u1(), region.v1(), brightness3); // 3
            addSolidVertex(x  , y+1, z+1, region.u2(), region.v1(), brightness0); // 0
            addSolidVertex(x  , y  , z+1, region.u2(), region.v2(), brightness1); // 1
            addSolidVertex(x  , y  , z+1, region.u2(), region.v2(), brightness1); // 1
            addSolidVertex(x  , y  , z  , region.u1(), region.v2(), brightness2); // 2
            addSolidVertex(x  , y+1, z  , region.u1(), region.v1(), brightness3); // 3
        }else{
            addSolidVertex(x  , y+1, z+1, region.u2(), region.v1(), brightness0); // 0
            addSolidVertex(x  , y  , z+1, region.u2(), region.v2(), brightness1); // 1
            addSolidVertex(x  , y  , z  , region.u1(), region.v2(), brightness2); // 2
            addSolidVertex(x  , y  , z  , region.u1(), region.v2(), brightness2); // 2
            addSolidVertex(x  , y+1, z  , region.u1(), region.v1(), brightness3); // 3
            addSolidVertex(x  , y+1, z+1, region.u2(), region.v1(), brightness0); // 0
        }
    }

    private static void addSolidPxFace(final int x, final int y, final int z, final Region region){
        final float light0 = getLight(x+1, y, z,  x+1, y, z-1,  x+1, y+1, z-1,  x+1, y+1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light1 = getLight(x+1, y, z,  x+1, y, z-1,  x+1, y-1, z-1,  x+1, y-1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light2 = getLight(x+1, y, z,  x+1, y, z+1,  x+1, y-1, z+1,  x+1, y-1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light3 = getLight(x+1, y, z,  x+1, y, z+1,  x+1, y+1, z+1,  x+1, y+1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        
        final float ao0 = getAO(x+1, y+1, z,  x+1, y, z-1,  x+1, y+1, z-1);
        final float ao1 = getAO(x+1, y-1, z,  x+1, y, z-1,  x+1, y-1, z-1);
        final float ao2 = getAO(x+1, y-1, z,  x+1, y, z+1,  x+1, y-1, z+1);
        final float ao3 = getAO(x+1, y+1, z,  x+1, y, z+1,  x+1, y+1, z+1);
        
        final float shadow = 0.8F;
        
        final float brightness0 = shadow * ao0 * light0;
        final float brightness1 = shadow * ao1 * light1;
        final float brightness2 = shadow * ao2 * light2;
        final float brightness3 = shadow * ao3 * light3;
        
        if(brightness1 + brightness3 > brightness0 + brightness2){ // Flip (change vertex order)
            addSolidVertex(x+1, y+1, z+1, region.u1(), region.v1(), brightness3); // 3
            addSolidVertex(x+1, y+1, z  , region.u2(), region.v1(), brightness0); // 0
            addSolidVertex(x+1, y  , z  , region.u2(), region.v2(), brightness1); // 1
            addSolidVertex(x+1, y  , z  , region.u2(), region.v2(), brightness1); // 1
            addSolidVertex(x+1, y  , z+1, region.u1(), region.v2(), brightness2); // 2
            addSolidVertex(x+1, y+1, z+1, region.u1(), region.v1(), brightness3); // 3
        }else{
            addSolidVertex(x+1, y+1, z  , region.u2(), region.v1(), brightness0); // 0
            addSolidVertex(x+1, y  , z  , region.u2(), region.v2(), brightness1); // 1
            addSolidVertex(x+1, y  , z+1, region.u1(), region.v2(), brightness2); // 2
            addSolidVertex(x+1, y  , z+1, region.u1(), region.v2(), brightness2); // 2
            addSolidVertex(x+1, y+1, z+1, region.u1(), region.v1(), brightness3); // 3
            addSolidVertex(x+1, y+1, z  , region.u2(), region.v1(), brightness0); // 0
        }
    }

    private static void addSolidNyFace(final int x, final int y, final int z, final Region region){
        final float light0 = getLight(x, y-1, z,  x, y-1, z-1,  x+1, y-1, z-1,  x+1, y-1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light1 = getLight(x, y-1, z,  x, y-1, z-1,  x-1, y-1, z-1,  x-1, y-1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light2 = getLight(x, y-1, z,  x, y-1, z+1,  x-1, y-1, z+1,  x-1, y-1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light3 = getLight(x, y-1, z,  x, y-1, z+1,  x+1, y-1, z+1,  x+1, y-1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        
        final float ao0 = getAO(x+1, y-1, z,  x, y-1, z-1,  x+1, y-1, z-1);
        final float ao1 = getAO(x-1, y-1, z,  x, y-1, z-1,  x-1, y-1, z-1);
        final float ao2 = getAO(x-1, y-1, z,  x, y-1, z+1,  x-1, y-1, z+1);
        final float ao3 = getAO(x+1, y-1, z,  x, y-1, z+1,  x+1, y-1, z+1);
        
        final float shadow = 0.6F;
        
        final float brightness0 = shadow * ao0 * light0;
        final float brightness1 = shadow * ao1 * light1;
        final float brightness2 = shadow * ao2 * light2;
        final float brightness3 = shadow * ao3 * light3;
        
        if(brightness1 + brightness3 > brightness0 + brightness2){ // Flip (change vertex order)
            addSolidVertex(x+1, y  , z+1, region.u2(), region.v1(), brightness3); // 3
            addSolidVertex(x+1, y  , z  , region.u2(), region.v2(), brightness0); // 0
            addSolidVertex(x  , y  , z  , region.u1(), region.v2(), brightness1); // 1
            addSolidVertex(x  , y  , z  , region.u1(), region.v2(), brightness1); // 1
            addSolidVertex(x  , y  , z+1, region.u1(), region.v1(), brightness2); // 2
            addSolidVertex(x+1, y  , z+1, region.u2(), region.v1(), brightness3); // 3
        }else{
            addSolidVertex(x+1, y  , z  , region.u2(), region.v2(), brightness0); // 0
            addSolidVertex(x  , y  , z  , region.u1(), region.v2(), brightness1); // 1
            addSolidVertex(x  , y  , z+1, region.u1(), region.v1(), brightness2); // 2
            addSolidVertex(x  , y  , z+1, region.u1(), region.v1(), brightness2); // 2
            addSolidVertex(x+1, y  , z+1, region.u2(), region.v1(), brightness3); // 3
            addSolidVertex(x+1, y  , z  , region.u2(), region.v2(), brightness0); // 0
        }
    }

    private static void addSolidPyFace(final int x, final int y, final int z, final Region region){
        final float light0 = getLight(x, y+1, z,  x, y+1, z-1,  x-1, y+1, z-1,  x-1, y+1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light1 = getLight(x, y+1, z,  x, y+1, z-1,  x+1, y+1, z-1,  x+1, y+1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light2 = getLight(x, y+1, z,  x, y+1, z+1,  x+1, y+1, z+1,  x+1, y+1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light3 = getLight(x, y+1, z,  x, y+1, z+1,  x-1, y+1, z+1,  x-1, y+1, z) / ChunkUtils.MAX_LIGHT_LEVEL;
        
        final float ao0 = getAO(x-1, y+1, z,  x, y+1, z-1,  x-1, y+1, z-1);
        final float ao1 = getAO(x+1, y+1, z,  x, y+1, z-1,  x+1, y+1, z-1);
        final float ao2 = getAO(x+1, y+1, z,  x, y+1, z+1,  x+1, y+1, z+1);
        final float ao3 = getAO(x-1, y+1, z,  x, y+1, z+1,  x-1, y+1, z+1);
        
        final float shadow = 1;
        
        final float brightness0 = shadow * ao0 * light0;
        final float brightness1 = shadow * ao1 * light1;
        final float brightness2 = shadow * ao2 * light2;
        final float brightness3 = shadow * ao3 * light3;
        
        if(brightness1 + brightness3 > brightness0 + brightness2){ // Flip (change vertex order)
            addSolidVertex(x  , y+1, z+1, region.u1(), region.v2(), brightness3); // 3
            addSolidVertex(x  , y+1, z  , region.u1(), region.v1(), brightness0); // 0
            addSolidVertex(x+1, y+1, z  , region.u2(), region.v1(), brightness1); // 1
            addSolidVertex(x+1, y+1, z  , region.u2(), region.v1(), brightness1); // 1
            addSolidVertex(x+1, y+1, z+1, region.u2(), region.v2(), brightness2); // 2
            addSolidVertex(x  , y+1, z+1, region.u1(), region.v2(), brightness3); // 3
        }else{
            addSolidVertex(x  , y+1, z  , region.u1(), region.v1(), brightness0); // 0
            addSolidVertex(x+1, y+1, z  , region.u2(), region.v1(), brightness1); // 1
            addSolidVertex(x+1, y+1, z+1, region.u2(), region.v2(), brightness2); // 2
            addSolidVertex(x+1, y+1, z+1, region.u2(), region.v2(), brightness2); // 2
            addSolidVertex(x  , y+1, z+1, region.u1(), region.v2(), brightness3); // 3
            addSolidVertex(x  , y+1, z  , region.u1(), region.v1(), brightness0); // 0
        }
    }

    private static void addSolidNzFace(final int x, final int y, final int z, final Region region){
        final float light0 = getLight(x, y, z-1,  x, y-1, z-1,  x-1, y-1, z-1,  x, y-1, z-1) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light1 = getLight(x, y, z-1,  x, y-1, z-1,  x+1, y-1, z-1,  x, y-1, z-1) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light2 = getLight(x, y, z-1,  x, y+1, z-1,  x+1, y+1, z-1,  x, y+1, z-1) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light3 = getLight(x, y, z-1,  x, y+1, z-1,  x-1, y+1, z-1,  x, y+1, z-1) / ChunkUtils.MAX_LIGHT_LEVEL;
        
        final float ao0 = getAO(x-1, y, z-1,  x, y-1, z-1,  x-1, y-1, z-1);
        final float ao1 = getAO(x+1, y, z-1,  x, y-1, z-1,  x+1, y-1, z-1);
        final float ao2 = getAO(x+1, y, z-1,  x, y+1, z-1,  x+1, y+1, z-1);
        final float ao3 = getAO(x-1, y, z-1,  x, y+1, z-1,  x-1, y+1, z-1);
        
        final float shadow = 0.7F;
        
        final float brightness0 = shadow * ao0 * light0;
        final float brightness1 = shadow * ao1 * light1;
        final float brightness2 = shadow * ao2 * light2;
        final float brightness3 = shadow * ao3 * light3;
        
        if(brightness1 + brightness3 > brightness0 + brightness2){ // Flip (change vertex order)
            addSolidVertex(x  , y+1, z  , region.u2(), region.v1(), brightness3); // 3
            addSolidVertex(x  , y  , z  , region.u2(), region.v2(), brightness0); // 0
            addSolidVertex(x+1, y  , z  , region.u1(), region.v2(), brightness1); // 1
            addSolidVertex(x+1, y  , z  , region.u1(), region.v2(), brightness1); // 1
            addSolidVertex(x+1, y+1, z  , region.u1(), region.v1(), brightness2); // 2
            addSolidVertex(x  , y+1, z  , region.u2(), region.v1(), brightness3); // 3
        }else{
            addSolidVertex(x  , y  , z  , region.u2(), region.v2(), brightness0); // 0
            addSolidVertex(x+1, y  , z  , region.u1(), region.v2(), brightness1); // 1
            addSolidVertex(x+1, y+1, z  , region.u1(), region.v1(), brightness2); // 2
            addSolidVertex(x+1, y+1, z  , region.u1(), region.v1(), brightness2); // 2
            addSolidVertex(x  , y+1, z  , region.u2(), region.v1(), brightness3); // 3
            addSolidVertex(x  , y  , z  , region.u2(), region.v2(), brightness0); // 0
        }
    }

    private static void addSolidPzFace(final int x, final int y, final int z, final Region region){
        final float light0 = getLight(x, y, z+1,  x, y-1, z+1,  x+1, y-1, z+1,  x+1, y, z+1) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light1 = getLight(x, y, z+1,  x, y-1, z+1,  x-1, y-1, z+1,  x-1, y, z+1) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light2 = getLight(x, y, z+1,  x, y+1, z+1,  x-1, y+1, z+1,  x-1, y, z+1) / ChunkUtils.MAX_LIGHT_LEVEL;
        final float light3 = getLight(x, y, z+1,  x, y+1, z+1,  x+1, y+1, z+1,  x+1, y, z+1) / ChunkUtils.MAX_LIGHT_LEVEL;
        
        final float ao0 = getAO(x+1, y, z+1,  x, y-1, z+1,  x+1, y-1, z+1);
        final float ao1 = getAO(x-1, y, z+1,  x, y-1, z+1,  x-1, y-1, z+1);
        final float ao2 = getAO(x-1, y, z+1,  x, y+1, z+1,  x-1, y+1, z+1);
        final float ao3 = getAO(x+1, y, z+1,  x, y+1, z+1,  x+1, y+1, z+1);
        
        final float shadow = 0.7F;
        
        final float brightness0 = shadow * ao0 * light0;
        final float brightness1 = shadow * ao1 * light1;
        final float brightness2 = shadow * ao2 * light2;
        final float brightness3 = shadow * ao3 * light3;
        
        if(brightness1 + brightness3 > brightness0 + brightness2){ // Flip (change vertex order)
            addSolidVertex(x+1, y+1, z+1, region.u2(), region.v1(), brightness3); // 3
            addSolidVertex(x+1, y  , z+1, region.u2(), region.v2(), brightness0); // 0
            addSolidVertex(x  , y  , z+1, region.u1(), region.v2(), brightness1); // 1
            addSolidVertex(x  , y  , z+1, region.u1(), region.v2(), brightness1); // 1
            addSolidVertex(x  , y+1, z+1, region.u1(), region.v1(), brightness2); // 2
            addSolidVertex(x+1, y+1, z+1, region.u2(), region.v1(), brightness3); // 3
        }else{
            addSolidVertex(x+1, y  , z+1, region.u2(), region.v2(), brightness0); // 0
            addSolidVertex(x  , y  , z+1, region.u1(), region.v2(), brightness1); // 1
            addSolidVertex(x  , y+1, z+1, region.u1(), region.v1(), brightness2); // 2
            addSolidVertex(x  , y+1, z+1, region.u1(), region.v1(), brightness2); // 2
            addSolidVertex(x+1, y+1, z+1, region.u2(), region.v1(), brightness3); // 3
            addSolidVertex(x+1, y  , z+1, region.u2(), region.v2(), brightness0); // 0
        }
    }
    

    private static void addSolidVertex(final int x, final int y, final int z, final float u, final float v, final float light){
        solidVertexIndex++;
        
        // Packed position
        final int atlasTilesX = 8;
        final int atlasTilesY = 8;
        final int positionPacked = (
            (x      ) | // 5 bit
            (y << 5 ) | // 9 bit
            (z << 14) | // 5 bit
        
            (Maths.round(u * atlasTilesX) << 19) | // 4 bit
            (Maths.round(v * atlasTilesY) << 23)   // 4 bit
        );
        solidVertices.add(positionPacked); // x, y, z, u, v
        
        // Packed color
        final int colorPacked = (
            Maths.round(light * 255) // 8 bit
        );
        solidVertices.add(colorPacked); // r, g, b, a
    }
    
    private static void addCustomVertex(final int x, final int y, final int z, final float u, final float v, final float light){
        customVertexIndex++;
        
        customVertices.add((float) x);
        customVertices.add((float) y);
        customVertices.add((float) z);
        customVertices.add(light);
        customVertices.add(light);
        customVertices.add(light);
        customVertices.add(1F);
        customVertices.add(u);
        customVertices.add(v);
    }

}
