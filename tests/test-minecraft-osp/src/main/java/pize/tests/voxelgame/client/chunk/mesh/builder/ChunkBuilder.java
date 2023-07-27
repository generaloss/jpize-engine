package pize.tests.voxelgame.client.chunk.mesh.builder;

import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.Blocks;
import pize.tests.voxelgame.client.block.model.BlockModel;
import pize.tests.voxelgame.client.chunk.ClientChunk;
import pize.tests.voxelgame.client.chunk.mesh.ChunkMeshCullingOff;
import pize.tests.voxelgame.client.chunk.mesh.ChunkMeshPackedCullingOn;
import pize.tests.voxelgame.client.chunk.mesh.ChunkMeshStack;
import pize.tests.voxelgame.client.chunk.mesh.ChunkMeshTranslucentCullingOn;
import pize.tests.voxelgame.client.level.ClientChunkManager;
import pize.tests.voxelgame.main.block.BlockData;
import pize.tests.voxelgame.main.chunk.LevelChunk;
import pize.tests.voxelgame.main.chunk.storage.Heightmap;
import pize.tests.voxelgame.main.chunk.storage.HeightmapType;
import pize.util.time.Stopwatch;

import java.util.concurrent.atomic.AtomicInteger;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.*;

public class ChunkBuilder{

    private final ClientChunkManager chunkManager;

    public ChunkBuilder(ClientChunkManager chunkManager){
        this.chunkManager = chunkManager;
    }

    public ClientChunkManager getChunkManager(){
        return chunkManager;
    }


    public ClientChunk chunk;
    public ChunkMeshPackedCullingOn solidMesh;
    public ChunkMeshCullingOff customMesh;
    public ChunkMeshTranslucentCullingOn translucentMesh;

    public double buildTime;
    public int verticesNum;

    private ClientChunk[] neighborChunks;

    private volatile AtomicInteger state = new AtomicInteger();


    public int getState(){
        return state.get();
    }


    public boolean build(ClientChunk chunk){
        if(state.get() != 0)
            return false;
        state.set(1);

        final Stopwatch timer = new Stopwatch().start();

        // Init
        this.chunk = chunk;
        this.neighborChunks = new ClientChunk[]{ // Rows - X, Columns - Z
            chunk.getNeighbor(-1, -1), chunk.getNeighbor(0, -1) , chunk.getNeighbor(1, -1),
            chunk.getNeighbor(-1,  0), chunk                    , chunk.getNeighbor(1,  0),
            chunk.getNeighbor(-1,  1), chunk.getNeighbor(0,  1) , chunk.getNeighbor(1,  1)
        };

        // Get Meshes
        final ChunkMeshStack meshStack = chunk.getMeshStack();
        solidMesh = meshStack.getPacked();
        customMesh = meshStack.getCustom();
        translucentMesh = meshStack.getTranslucent();

        // Build
        final Heightmap heightmap = chunk.getHeightMap(HeightmapType.HIGHEST);

        state.incrementAndGet();

        if(!chunk.isEmpty())
            for(int lx = 0; lx < SIZE; lx++){
                state.incrementAndGet();
                for(int lz = 0; lz < SIZE; lz++){
                    
                    final int height = heightmap.getHeight(lx, lz) + 1;
                    for(int y = 0; y < height; y++){

                        final short blockData = chunk.getBlock(lx, y, lz);
                        final BlockProperties block = BlockData.getProps(blockData);
                        final byte blockState = BlockData.getState(blockData);
                        if(block.isEmpty())
                            continue;
                        
                        final BlockModel model = block.getState(blockState).getModel();
                        if(model == null)
                            continue;

                        model.build(this, block, lx, y, lz);
                        
                        // if(block.isSolid()){
                        //     final BlockModelSolid solidModel = model.asSolid();
                        //
                        //     if(isGenSolidFace(lx, y, lz, -1,  0,  0, block)) solidModel.buildNxFaces(this, lx, y, lz);
                        //     if(isGenSolidFace(lx, y, lz, +1,  0,  0, block)) solidModel.buildPxFaces(this, lx, y, lz);
                        //     if(isGenSolidFace(lx, y, lz,  0, -1,  0, block)) solidModel.buildNyFaces(this, lx, y, lz);
                        //     if(isGenSolidFace(lx, y, lz,  0, +1,  0, block)) solidModel.buildPyFaces(this, lx, y, lz);
                        //     if(isGenSolidFace(lx, y, lz,  0,  0, -1, block)) solidModel.buildNzFaces(this, lx, y, lz);
                        //     if(isGenSolidFace(lx, y, lz,  0,  0, +1, block)) solidModel.buildPzFaces(this, lx, y, lz);
                        // }else{
                        //     final float[] add = new float[]{lx, y, lz, 0, 0, 0, 0, 0, 0};
                        //     final float light = chunk.getLight(lx, y, lz) / 15F;
                        //     final float[] mul = new float[]{1, 1, 1, light, light, light, 1, 1, 1};
                        //     final BlockModelCustom customModel = model.asCustom();
                        //     for(int i = 0; i < customModel.getVertices().size(); i++){
                        //         float vertex = customModel.getVertices().get(i) * mul[i % 9] + add[i % 9];
                        //         customMesh.put(vertex);
                        //     }
                        // }
                    }
                }
            }

        state.set(54);

        // Update meshes
        verticesNum = 0;
        verticesNum += solidMesh.updateVertices();
        verticesNum += customMesh.updateVertices();
        verticesNum += translucentMesh.updateVertices();

        // Time
        buildTime = timer.getMillis();

        state.set(0);

        return true;
    }
    
    public boolean isGenSolidFace(int lx, int y, int lz, int normalX, int normalY, int normalZ, BlockProperties block){
        if(isOutOfBounds(y))
            return true;

        final short neighborData = getBlock(lx + normalX, y + normalY, lz + normalZ);
        final byte neighborState = BlockData.getState(neighborData);
        final BlockProperties neighbor = BlockData.getProps(neighborData);

        if(neighbor.getID() == Blocks.VOID_AIR.getID())
            return false;
        
        return (neighbor.isSolid() && neighbor.getState(neighborState).getModel().isFaceTransparentForNeighbors(-normalX, -normalY, -normalZ)) || neighbor.isEmpty() || (neighbor.isLightTranslucent() && !block.isLightTranslucent());
    }
    
    
    public float getAO(int x1, int y1, int z1, int x2, int y2, int z2, int x3, int y3, int z3, int x4, int y4, int z4){
        final BlockProperties block1 = getBlockProps(x1, y1, z1);
        final BlockProperties block2 = getBlockProps(x2, y2, z2);
        final BlockProperties block3 = getBlockProps(x3, y3, z3);

        final short block4Data = getBlock(x4, y4, z4);
        final byte block4State = BlockData.getState(block4Data);
        final BlockProperties block4 = BlockData.getProps(block4Data);

        float result = 0;
        if(!(block1.isEmpty() || block1.isLightTranslucent())) result++;
        if(!(block2.isEmpty() || block2.isLightTranslucent())) result++;
        if(!(block3.isEmpty() || block3.isLightTranslucent())) result++;

        final int normalX = (x1 + x2 + x3 + x4) / 2;
        final int normalY = (y1 + y2 + y3 + y4) / 2;
        final int normalZ = (z1 + z2 + z3 + z4) / 2;

        if(!(block4.isEmpty() || block4.isLightTranslucent()) || (block4.isSolid() && block4.getState(block4State).getModel().isFaceTransparentForNeighbors(normalX, normalY, normalZ))) result++;

        return 1 - result / 5;
    }
    
    public float getLight(int x1, int y1, int z1, int x2, int y2, int z2, int x3, int y3, int z3, int x4, int y4, int z4){
        float result = 0;
        byte n = 0;
        
        if(getBlockProps(x1, y1, z1).isLightTranslucent()){
            result += getLight(x1, y1, z1);
            n++;
        }
        
        if(getBlockProps(x2, y2, z2).isLightTranslucent()){
            result += getLight(x2, y2, z2);
            n++;
        }
        
        if(getBlockProps(x3, y3, z3).isLightTranslucent()){
            result += getLight(x3, y3, z3);
            n++;
        }
        
        if(getBlockProps(x4, y4, z4).isLightTranslucent()){
            result += getLight(x4, y4, z4);
            n++;
        }
        
        if(n == 0)
            return 0;
        
        return result / n;
    }


    public BlockProperties getBlockProps(int lx, int y, int lz){
        return BlockData.getProps(getBlock(lx, y, lz));
    }

    public short getBlock(int lx, int y, int lz){
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
            return Blocks.VOID_AIR.getDefaultData();
        
        // Возвращаем блок
        return chunk.getBlock(lx, y, lz);
    }
    
    public byte getLight(int lx, int y, int lz){
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
        
        final ClientChunk chunk = neighborChunks[(signZ + 1) * 3 + (signX + 1)];
        if(chunk == null)
            return 15;
        
        // Возвращаем уровень света
        return chunk.getLight(lx, y, lz);
    }

}
