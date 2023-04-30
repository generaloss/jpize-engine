package megalul.projectvostok.world;

import megalul.projectvostok.block.blocks.Block;
import megalul.projectvostok.chunk.Chunk;
import megalul.projectvostok.chunk.Priority;
import megalul.projectvostok.chunk.gen.DefaultGenerator;
import megalul.projectvostok.chunk.mesh.ChunkBuilder;
import megalul.projectvostok.chunk.mesh.ChunkMesh;
import megalul.projectvostok.chunk.storage.ChunkPos;
import pize.math.vecmath.vector.Vec2f;
import pize.math.vecmath.vector.Vec3f;
import pize.util.time.FpsCounter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static megalul.projectvostok.chunk.ChunkUtils.*;

public class ChunkProvider{
    
    public static boolean doLoadChunks = true;
    
    
    private final World worldOF;
    
    public final FpsCounter findTps, loadTps, buildTps, checkTps;
    
    private final List<ChunkPos> frontiers, newFrontiers;
    private final Map<ChunkPos, Chunk> allChunks;
    private final List<ChunkPos> loadQueue;
    private final Queue<Chunk> toBuildQueue;
    private final Map<Chunk, float[]> built;
    private final Map<Chunk, ChunkMesh> allMeshes;
    private final List<ChunkMesh> toDispose;
    

    public ChunkProvider(World worldOF){
        this.worldOF = worldOF;
        
        frontiers = new ArrayList<>();
        newFrontiers = new ArrayList<>();
        allChunks = new ConcurrentHashMap<>();
        loadQueue = new CopyOnWriteArrayList<>();
        toBuildQueue = new ArrayDeque<>();
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
        if(!doLoadChunks)
            return;
        
        final Vec3f camPos = getCamPos();
        
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
    
        frontiers.removeIf(this::isOffTheGrid);
        newFrontiers.removeIf(this::isOffTheGrid);
        
        if(newFrontiers.size() == 0)
            return;
        
        loadQueue.addAll(newFrontiers);
        newFrontiers.clear();
    }
    
    private void ensureFrontier(ChunkPos chunkPos){
        if(frontiers.contains(chunkPos) || isOffTheGrid(chunkPos)) // || !worldOF.getSessionOf().getCamera().isChunkSeen(chunkPos)
            return;
        
        frontiers.add(chunkPos);
        newFrontiers.add(chunkPos);
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
        while(!toBuildQueue.isEmpty()){
            Chunk chunk = toBuildQueue.poll();
            
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

    public void rebuildChunk(Chunk chunk, Priority priority){
        if(!toBuildQueue.contains(chunk))
            toBuildQueue.add(chunk);
    }
    

    private void updateNeighborChunksEdgesAndSelf(Chunk chunk, boolean loaded){
        // neighbors
        Chunk neighbor = getNeighborChunk(chunk, -1, 0);
        if(neighbor != null)
            for(int i = 0; i < SIZE; i++)
                for(int y = 0; y < HEIGHT; y++)
                    updateEdge(y, loaded, chunk, neighbor, SIZE, i, 0, i, -1, i, SIZE_IDX, i);
        
        neighbor = getNeighborChunk(chunk, 1, 0);
        if(neighbor != null)
            for(int i = 0; i < SIZE; i++)
                for(int y = 0; y < HEIGHT; y++)
                    updateEdge(y, loaded, chunk, neighbor, -1, i, SIZE_IDX, i, SIZE, i, 0, i);
        
        neighbor = getNeighborChunk(chunk, 0, -1);
        if(neighbor != null)
            for(int i = 0; i < SIZE; i++)
                for(int y = 0; y < HEIGHT; y++)
                    updateEdge(y, loaded, chunk, neighbor, i, SIZE, i, 0, i, -1, i, SIZE_IDX);
        
        neighbor = getNeighborChunk(chunk, 0, 1);
        if(neighbor != null)
            for(int i = 0; i < SIZE; i++)
                for(int y = 0; y < HEIGHT; y++)
                    updateEdge(y, loaded, chunk, neighbor, i, -1, i, SIZE_IDX, i, SIZE, i, 0);
    
        // corners
        neighbor = getNeighborChunk(chunk, -1, 1);
        if(neighbor != null)
            for(int y = 0; y < HEIGHT; y++)
                updateEdge(y, loaded, chunk, neighbor, SIZE, -1, 0, SIZE_IDX, -1, SIZE, SIZE_IDX, 0);
        
        neighbor = getNeighborChunk(chunk, 1, 1);
        if(neighbor != null)
            for(int y = 0; y < HEIGHT; y++)
                updateEdge(y, loaded, chunk, neighbor, -1, -1, SIZE_IDX, SIZE_IDX, SIZE, SIZE, 0, 0);
        
        neighbor = getNeighborChunk(chunk, -1, -1);
        if(neighbor != null)
            for(int y = 0; y < HEIGHT; y++)
                updateEdge(y, loaded, chunk, neighbor, SIZE, SIZE, 0, 0, -1, -1, SIZE_IDX, SIZE_IDX);
        
        neighbor = getNeighborChunk(chunk, -1, 1);
        if(neighbor != null)
            for(int y = 0; y < HEIGHT; y++)
                updateEdge(y, loaded, chunk, neighbor, SIZE, -1, 0, SIZE_IDX, -1, SIZE, SIZE_IDX, 0);
    }
    
    
    private void updateEdge(int y, boolean loaded, Chunk chunk1, Chunk chunk2, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4){
        chunk2.setBlock(x1, y, y1, loaded ? chunk1.getBlock(x2, y, y2) : Block.VOID_AIR.getState());
        chunk2.setSkyLight(x1, y, y1, loaded ? chunk1.getSkyLight(x2, y, y2) : MAX_LIGHT_LEVEL);
        chunk2.setBlockLight(x1, y, y1, loaded ? chunk1.getBlockLight(x2, y, y2) : MAX_LIGHT_LEVEL);
        
        if(loaded){
            chunk1.setBlock(x3, y, y3, chunk2.getBlock(x4, y, y4));
            chunk1.setSkyLight(x3, y, y3, chunk2.getSkyLight(x4, y, y4));
            chunk1.setBlockLight(x3, y, y3, chunk2.getBlockLight(x4, y, y4));
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
