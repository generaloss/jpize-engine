package megalul.projectvostok.world;

import pize.math.Maths;
import pize.math.vecmath.vector.Vec2f;
import pize.math.vecmath.vector.Vec3f;
import pize.util.time.FpsCounter;
import megalul.projectvostok.block.blocks.Block;
import megalul.projectvostok.chunk.Chunk;
import megalul.projectvostok.chunk.gen.DefaultGenerator;
import megalul.projectvostok.chunk.mesh.ChunkBuilder;
import megalul.projectvostok.chunk.mesh.ChunkMesh;
import megalul.projectvostok.chunk.storage.ChunkPos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static megalul.projectvostok.chunk.ChunkUtils.*;

public class ChunkProvider{
    
    private final World worldOF;
    
    public final FpsCounter findTps, loadTps, buildTps, checkTps;
    
    private final List<ChunkPos> frontiers, newFrontiers;
    private final Map<ChunkPos, Chunk> allChunks;
    private final List<ChunkPos> loadQueue;
    private final List<Chunk> toBuildQueue;
    private final Map<Chunk, float[]> built;
    private final Map<Chunk, ChunkMesh> allMeshes;
    private final List<ChunkMesh> toDispose;


    public ChunkProvider(World worldOF){
        this.worldOF = worldOF;
    
        frontiers = new ArrayList<>();
        newFrontiers = new ArrayList<>();
        allChunks = new ConcurrentHashMap<>();
        loadQueue = new CopyOnWriteArrayList<>();
        toBuildQueue = new CopyOnWriteArrayList<>();
        built = new ConcurrentHashMap<>();
        allMeshes = new ConcurrentHashMap<>();
        toDispose = new CopyOnWriteArrayList<>();
        
        findTps = new FpsCounter();
        loadTps = new FpsCounter();
        buildTps = new FpsCounter();
        checkTps = new FpsCounter();
    
        newThread(()->{
            findTps.update();
            findChunks();
        }, "Find Chunks");
        newThread(()->{
            loadTps.update();
            loadChunks();
        }, "Load Chunks");
        newThread(()->{
            buildTps.update();
            buildChunks();
        }, "Build Chunks");
        newThread(()->{
            checkTps.update();
            checkChunks();
        }, "Check Chunks");
    }
    
    public World getWorldOf(){
        return worldOF;
    }
    
    
    private void newThread(Runnable runnable, String name){
        Thread thread = new Thread(()->{
            while(!Thread.currentThread().isInterrupted()){
                runnable.run();
                Thread.yield();
            }
        }, name + " Thread");
        thread.setDaemon(true);
        thread.start();
    }
    
    
    private void findChunks(){
        Vec3f camPos = getCamPos();
        if(frontiers.size() == 0)
            ensureFrontier(new ChunkPos(
                getChunkPos(camPos.xf()),
                getChunkPos(camPos.zf())
            ));
        
        for(int i = 0; i < frontiers.size(); i++){
            ChunkPos frontierPos = frontiers.get(i);
            
            ensureFrontier(frontierPos.getNeighbor(-1, 0));
            ensureFrontier(frontierPos.getNeighbor(1, 0));
            ensureFrontier(frontierPos.getNeighbor(0, -1));
            ensureFrontier(frontierPos.getNeighbor(0, 1));
        }
    
        for(ChunkPos frontier: frontiers)
            if(!loadQueue.contains(frontier) && !allChunks.containsKey(frontier))
                newFrontiers.add(frontier);
    
        frontiers.removeIf(this::isOffTheGrid);
        newFrontiers.removeIf(this::isOffTheGrid);
        
        if(newFrontiers.size() == 0)
            return;
        
        newFrontiers.sort((pos1, pos2)->Maths.round(distToChunk(pos1.x, pos1.z, camPos) - Maths.round(distToChunk(pos2.x, pos2.z, camPos))));
        loadQueue.addAll(newFrontiers);
        newFrontiers.clear();
    }
    
    private void ensureFrontier(ChunkPos chunkPos){
        if(frontiers.contains(chunkPos) || isOffTheGrid(chunkPos)) // || !worldOF.getSessionOf().getCamera().isChunkSeen(chunkPos)
            return;
        
        frontiers.add(chunkPos);
    }
    

    private void loadChunks(){
        for(ChunkPos chunkPos: loadQueue){
            loadQueue.remove(chunkPos);
            if(isOffTheGrid(chunkPos))
                continue;

            loadChunk(chunkPos);
        }
    }

    private void buildChunks(){
        for(Chunk chunk: toBuildQueue){
            toBuildQueue.remove(chunk);
            if(isOffTheGrid(chunk.getPos()))
                continue;
            
            float[] vertices = ChunkBuilder.build(chunk);
            built.put(chunk, vertices);
        }
    }

    public void checkChunks(){
        for(Chunk chunk: allChunks.values()){
            if(isOffTheGrid(chunk.getPos()))
                unloadChunk(chunk);
        }
    
        for(Chunk chunk: allMeshes.keySet())
            if(isOffTheGrid(chunk.getPos())){
                allMeshes.remove(chunk);
                toDispose.add(allMeshes.get(chunk));
            }
    }
    
    public void updateMeshes(){
        for(Map.Entry<Chunk, float[]> entry: built.entrySet()){
            updateMesh(entry.getKey(), entry.getValue());
            built.remove(entry.getKey());
        }
        
        for(ChunkMesh mesh: toDispose){
            toDispose.remove(mesh);
            if(mesh != null)
                mesh.dispose();
        }
    }


    public void loadChunk(ChunkPos chunkPos){
        Chunk chunk = new Chunk(this, chunkPos);
        allChunks.put(chunkPos, chunk);
        DefaultGenerator.getInstance().generate(chunk);
        worldOF.getLight().updateChunkSkyLight(chunk);

        updateNeighborChunksEdgesAndSelf(chunk, true);
        toBuildQueue.add(chunk);
    }

    public void unloadChunk(Chunk chunk){
        allChunks.remove(chunk.getPos());
        updateNeighborChunksEdgesAndSelf(chunk, false);
    }

    public void updateMesh(Chunk chunk, float[] vertices){
        ChunkMesh mesh = allMeshes.get(chunk);
        if(mesh == null)
            mesh = new ChunkMesh();

        mesh.setVertices(vertices);
        allMeshes.put(chunk, mesh);
    }

    public void rebuildChunk(Chunk chunk){
        if(!toBuildQueue.contains(chunk))
            toBuildQueue.add(chunk);
    }
    

    private void updateNeighborChunksEdgesAndSelf(Chunk chunk, boolean loaded){
        Chunk neighbor = allChunks.get(chunk.getPos().getNeighbor(-1, 0));
        if(neighbor != null)
            for(int i = 0; i < SIZE; i++)
                for(int y = 0; y < HEIGHT; y++){
                    neighbor.setBlock(SIZE, y, i, loaded ? chunk.getBlock(0, y, i) : Block.AIR.getState());
                    neighbor.setSkyLight(SIZE, y, i, loaded ? chunk.getSkyLight(0, y, i) : MAX_LIGHT_LEVEL);
                    neighbor.setBlockLight(SIZE, y, i, loaded ? chunk.getBlockLight(0, y, i) : MAX_LIGHT_LEVEL);
                    if(loaded){
                        chunk.setBlock(-1, y, i, neighbor.getBlock(SIZE_IDX, y, i));
                        chunk.setSkyLight(-1, y, i, neighbor.getSkyLight(SIZE_IDX, y, i));
                        chunk.setBlockLight(-1, y, i, neighbor.getBlockLight(SIZE_IDX, y, i));
                    }
                }
        neighbor = allChunks.get(chunk.getPos().getNeighbor(1, 0));
        if(neighbor != null)
            for(int i = 0; i < SIZE; i++)
                for(int y = 0; y < HEIGHT; y++){
                    neighbor.setBlock(-1, y, i, loaded ? chunk.getBlock(SIZE_IDX, y, i) : Block.AIR.getState());
                    neighbor.setSkyLight(-1, y, i, loaded ? chunk.getSkyLight(SIZE_IDX, y, i) : MAX_LIGHT_LEVEL);
                    neighbor.setBlockLight(-1, y, i, loaded ? chunk.getBlockLight(SIZE_IDX, y, i) : MAX_LIGHT_LEVEL);
                    if(loaded){
                        chunk.setBlock(SIZE, y, i, neighbor.getBlock(0, y, i));
                        chunk.setSkyLight(SIZE, y, i, neighbor.getSkyLight(0, y, i));
                        chunk.setBlockLight(SIZE, y, i, neighbor.getBlockLight(0, y, i));
                    }
                }
        neighbor = allChunks.get(chunk.getPos().getNeighbor(0, -1));
        if(neighbor != null)
            for(int i = 0; i < SIZE; i++)
                for(int y = 0; y < HEIGHT; y++){
                    neighbor.setBlock(i, y, SIZE, loaded ? chunk.getBlock(i, y, 0) : Block.AIR.getState());
                    neighbor.setSkyLight(i, y, SIZE, loaded ? chunk.getSkyLight(i, y, 0) : MAX_LIGHT_LEVEL);
                    neighbor.setBlockLight(i, y, SIZE, loaded ? chunk.getBlockLight(i, y, 0) : MAX_LIGHT_LEVEL);
                    if(loaded){
                        chunk.setBlock(i, y, -1, neighbor.getBlock(i, y, SIZE_IDX));
                        chunk.setSkyLight(i, y, -1, neighbor.getSkyLight(i, y, SIZE_IDX));
                        chunk.setBlockLight(i, y, -1, neighbor.getBlockLight(i, y, SIZE_IDX));
                    }
                }
        neighbor = allChunks.get(chunk.getPos().getNeighbor(0, 1));
        if(neighbor != null)
            for(int i = 0; i < SIZE; i++)
                for(int y = 0; y < HEIGHT; y++){
                    neighbor.setBlock(i, y, -1, loaded ? chunk.getBlock(i, y, SIZE_IDX) : Block.AIR.getState());
                    neighbor.setSkyLight(i, y, -1, loaded ? chunk.getSkyLight(i, y, SIZE_IDX) : MAX_LIGHT_LEVEL);
                    neighbor.setBlockLight(i, y, -1, loaded ? chunk.getBlockLight(i, y, SIZE_IDX) : MAX_LIGHT_LEVEL);
                    if(loaded){
                        chunk.setBlock(i, y, SIZE, neighbor.getBlock(i, y, 0));
                        chunk.setSkyLight(i, y, SIZE, neighbor.getSkyLight(i, y, 0));
                        chunk.setBlockLight(i, y, SIZE, neighbor.getBlockLight(i, y, 0));
                    }
                }
    
        // corners
        neighbor = allChunks.get(chunk.getPos().getNeighbor(-1, 1));
        if(neighbor != null)
            for(int y = 0; y < HEIGHT; y++){
                neighbor.setBlock(SIZE, y, -1, loaded ? chunk.getBlock(0, y, SIZE_IDX) : Block.AIR.getState());
                neighbor.setSkyLight(SIZE, y, -1, loaded ? chunk.getSkyLight(0, y, SIZE_IDX) : MAX_LIGHT_LEVEL);
                neighbor.setBlockLight(SIZE, y, -1, loaded ? chunk.getBlockLight(0, y, SIZE_IDX) : MAX_LIGHT_LEVEL);
                if(loaded){
                    chunk.setBlock(-1, y, SIZE, neighbor.getBlock(SIZE_IDX, y, 0));
                    chunk.setSkyLight(-1, y, SIZE, neighbor.getSkyLight(SIZE_IDX, y, 0));
                    chunk.setBlockLight(-1, y, SIZE, neighbor.getBlockLight(SIZE_IDX, y, 0));
                }
            }
        neighbor = allChunks.get(chunk.getPos().getNeighbor(1, 1));
        if(neighbor != null)
            for(int y = 0; y < HEIGHT; y++){
                neighbor.setBlock(-1, y, -1, loaded ? chunk.getBlock(SIZE_IDX, y, SIZE_IDX) : Block.AIR.getState());
                neighbor.setSkyLight(-1, y, -1, loaded ? chunk.getSkyLight(SIZE_IDX, y, SIZE_IDX) : MAX_LIGHT_LEVEL);
                neighbor.setBlockLight(-1, y, -1, loaded ? chunk.getBlockLight(SIZE_IDX, y, SIZE_IDX) : MAX_LIGHT_LEVEL);
                if(loaded){
                    chunk.setBlock(SIZE, y, SIZE, neighbor.getBlock(0, y, 0));
                    chunk.setSkyLight(SIZE, y, SIZE, neighbor.getSkyLight(0, y, 0));
                    chunk.setBlockLight(SIZE, y, SIZE, neighbor.getBlockLight(0, y, 0));
                }
            }
        neighbor = allChunks.get(chunk.getPos().getNeighbor(-1, -1));
        if(neighbor != null)
            for(int y = 0; y < HEIGHT; y++){
                neighbor.setBlock(SIZE, y, SIZE, loaded ? chunk.getBlock(0, y, 0) : Block.AIR.getState());
                neighbor.setSkyLight(SIZE, y, SIZE, loaded ? chunk.getSkyLight(0, y, 0) : MAX_LIGHT_LEVEL);
                neighbor.setBlockLight(SIZE, y, SIZE, loaded ? chunk.getBlockLight(0, y, 0) : MAX_LIGHT_LEVEL);
                if(loaded){
                    chunk.setBlock(-1, y, -1, neighbor.getBlock(SIZE_IDX, y, SIZE_IDX));
                    chunk.setSkyLight(-1, y, -1, neighbor.getSkyLight(SIZE_IDX, y, SIZE_IDX));
                    chunk.setBlockLight(-1, y, -1, neighbor.getBlockLight(SIZE_IDX, y, SIZE_IDX));
                }
            }
        neighbor = allChunks.get(chunk.getPos().getNeighbor(-1, 1));
        if(neighbor != null)
            for(int y = 0; y < HEIGHT; y++){
                neighbor.setBlock(SIZE, y, -1, loaded ? chunk.getBlock(0, y, SIZE_IDX) : Block.AIR.getState());
                neighbor.setSkyLight(SIZE, y, -1, loaded ? chunk.getSkyLight(0, y, SIZE_IDX) : MAX_LIGHT_LEVEL);
                neighbor.setBlockLight(SIZE, y, -1, loaded ? chunk.getBlockLight(0, y, SIZE_IDX) : MAX_LIGHT_LEVEL);
                if(loaded){
                    chunk.setBlock(-1, y, SIZE, neighbor.getBlock(SIZE_IDX, y, 0));
                    chunk.setSkyLight(-1, y, SIZE, neighbor.getSkyLight(SIZE_IDX, y, 0));
                    chunk.setBlockLight(-1, y, SIZE, neighbor.getBlockLight(SIZE_IDX, y, 0));
                }
            }
    }


    public Chunk getChunk(ChunkPos chunkPos){
        return allChunks.get(chunkPos);
    }

    public Chunk getChunk(int chunkX, int chunkZ){
        return getChunk(new ChunkPos(chunkX, chunkZ));
    }

    public Collection<Chunk> getChunks(){
        return allChunks.values();
    }

    public Map<Chunk, ChunkMesh> getMeshes(){
        return allMeshes;
    }
    
    public ChunkMesh getMesh(Chunk chunk){
        return allMeshes.get(chunk);
    }


    private boolean isOffTheGrid(int x, int z){
        return distToChunk(x, z, getCamPos()) > worldOF.getSessionOf().getOptions().getRenderDistance();
    }

    private boolean isOffTheGrid(ChunkPos chunkPos){
        return isOffTheGrid(chunkPos.x, chunkPos.z);
    }


    private float distToChunk(int x, int z, Vec3f camPos){
        return Vec2f.len(x - camPos.x + 0.5F, z - camPos.z + 0.5F);
    }

    private Vec3f getCamPos(){
        return worldOF.getSessionOf().getCamera().getPos().clone().div(SIZE);
    }

}
