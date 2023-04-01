package megalul.projectvostok.world;

import glit.math.Maths;
import glit.math.vecmath.vector.Vec2f;
import glit.math.vecmath.vector.Vec3f;
import glit.util.time.FpsCounter;
import megalul.projectvostok.Main;
import megalul.projectvostok.block.BlockState;
import megalul.projectvostok.chunk.Chunk;
import megalul.projectvostok.chunk.data.ChunkPos;
import megalul.projectvostok.chunk.gen.DefaultGenerator;
import megalul.projectvostok.chunk.mesh.ChunkBuilder;
import megalul.projectvostok.chunk.mesh.ChunkMesh;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static megalul.projectvostok.chunk.ChunkUtils.*;

public class ChunkProvider{
    
    private static final int MAX_LOAD_TASKS = 128;

    private final Main session;
    
    public final FpsCounter findTps, loadTps, buildTps, checkTps;
    
    private final List<ChunkPos> frontiers, newFrontiers;
    private final Map<ChunkPos, Chunk> allChunks;
    private final List<ChunkPos> loadQueue;
    private final List<Chunk> toBuildQueue;
    private final Map<Chunk, float[]> built;
    private final Map<Chunk, ChunkMesh> allMeshes;
    private final List<ChunkMesh> toDispose;


    public ChunkProvider(Main session){
        this.session = session;
    
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
        if(frontiers.contains(chunkPos) || !session.getCamera().isChunkSeen(chunkPos) || isOffTheGrid(chunkPos))
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
            else if(chunk.isDirty())
                rebuildChunk(chunk);
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
        chunk.onMeshUpdate();
    }

    public void rebuildChunk(Chunk chunk){
        if(!toBuildQueue.contains(chunk) && !built.containsKey(chunk))
            toBuildQueue.add(chunk);
    }
    

    private void updateNeighborChunksEdgesAndSelf(Chunk chunk, boolean loaded){
        Chunk neighbor = allChunks.get(chunk.getPos().getNeighbor(-1, 0));
        if(neighbor != null)
            for(int i = 0; i < SIZE; i++)
                for(int y = 0; y < HEIGHT; y++){
                    neighbor.setBlock(SIZE, y, i, loaded ? chunk.getBlock(0, y, i) : BlockState.AIR);
                    if(loaded)
                        chunk.setBlock(-1, y, i, neighbor.getBlock(SIZE_IDX, y, i));
                }
        neighbor = allChunks.get(chunk.getPos().getNeighbor(1, 0));
        if(neighbor != null)
            for(int i = 0; i < SIZE; i++)
                for(int y = 0; y < HEIGHT; y++){
                    neighbor.setBlock(-1, y, i, loaded ? chunk.getBlock(SIZE_IDX, y, i) : BlockState.AIR);
                    if(loaded)
                        chunk.setBlock(SIZE, y, i, neighbor.getBlock(0, y, i));
                }
        neighbor = allChunks.get(chunk.getPos().getNeighbor(0, -1));
        if(neighbor != null)
            for(int i = 0; i < SIZE; i++)
                for(int y = 0; y < HEIGHT; y++){
                    neighbor.setBlock(i, y, SIZE, loaded ? chunk.getBlock(i, y, 0) : BlockState.AIR);
                    if(loaded)
                        chunk.setBlock(i, y, -1, neighbor.getBlock(i, y, SIZE_IDX));
                }
        neighbor = allChunks.get(chunk.getPos().getNeighbor(0, 1));
        if(neighbor != null)
            for(int i = 0; i < SIZE; i++)
                for(int y = 0; y < HEIGHT; y++){
                    neighbor.setBlock(i, y, -1, loaded ? chunk.getBlock(i, y, SIZE_IDX) : BlockState.AIR);
                    if(loaded)
                        chunk.setBlock(i, y, SIZE, neighbor.getBlock(i, y, 0));
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
        return distToChunk(x, z, getCamPos()) > session.getOptions().getRenderDistance();
    }

    private boolean isOffTheGrid(ChunkPos chunkPos){
        return isOffTheGrid(chunkPos.x, chunkPos.z);
    }


    private float distToChunk(int x, int z, Vec3f camPos){
        return Vec2f.len(x - camPos.x + 0.5F, z - camPos.z + 0.5F);
    }

    private Vec3f getCamPos(){
        return session.getCamera().getPos().clone().div(SIZE);
    }

}
