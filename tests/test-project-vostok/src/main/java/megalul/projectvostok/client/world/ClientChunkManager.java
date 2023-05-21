package megalul.projectvostok.client.world;

import megalul.projectvostok.client.chunk.ClientChunk;
import megalul.projectvostok.client.chunk.mesh.ChunkBuilder;
import megalul.projectvostok.client.chunk.mesh.ChunkMesh;
import megalul.projectvostok.clientserver.world.ChunkManager;
import megalul.projectvostok.clientserver.chunk.storage.ChunkPos;
import pize.util.time.PerSecCounter;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static megalul.projectvostok.clientserver.world.ChunkManagerUtils.*;

public class ClientChunkManager extends ChunkManager{
    
    private final ClientWorld worldOF;
    
    public final PerSecCounter buildTps, checkTps;
    
    private final Map<ChunkPos, ClientChunk> allChunks;
    private final LinkedList<ClientChunk> toBuildQueue;
    private final Map<ClientChunk, float[]> built;
    private final Map<ClientChunk, ChunkMesh> allMeshes;
    private final List<ChunkMesh> toDispose;
    
    
    public ClientChunkManager(ClientWorld worldOF){
        this.worldOF = worldOF;
        
        allChunks = new ConcurrentHashMap<>();
        toBuildQueue = new LinkedList<>();
        built = new ConcurrentHashMap<>();
        allMeshes = new ConcurrentHashMap<>();
        toDispose = new CopyOnWriteArrayList<>();
        
        buildTps = new PerSecCounter();
        newThread(()->{
            buildTps.count();
            buildChunks();
        }, "Build Chunks");
        
        checkTps = new PerSecCounter();
        newThread(()->{
            checkTps.count();
            checkChunks();
        }, "Check Chunks");
    }
    
    public ClientWorld getWorldOf(){
        return worldOF;
    }
    
    
    private void buildChunks(){
        while(!toBuildQueue.isEmpty()){
            ClientChunk chunk = toBuildQueue.poll();
            if(chunk == null)
                continue;
            
            if(isOffTheGrid(chunk.getPosition()))
                continue;
            
            float[] vertices = ChunkBuilder.build(chunk);
            built.put(chunk, vertices);
        }
    }
    
    public void checkChunks(){
        for(ClientChunk chunk: allChunks.values()){
            if(isOffTheGrid(chunk.getPosition()))
                unloadChunk(chunk);
        }
        
        for(ClientChunk chunk: allMeshes.keySet())
            if(isOffTheGrid(chunk.getPosition())){
                allMeshes.remove(chunk);
                toDispose.add(allMeshes.get(chunk));
            }
    }
    
    public void updateMeshes(){
        for(Map.Entry<ClientChunk, float[]> entry: built.entrySet()){
            updateMesh(entry.getKey(), entry.getValue());
            built.remove(entry.getKey());
        }
        
        for(ChunkMesh mesh: toDispose){
            toDispose.remove(mesh);
            if(mesh != null)
                mesh.dispose();
        }
    }
    
    
    public void unloadChunk(ClientChunk chunk){
        allChunks.remove(chunk.getPosition());
        updateNeighborChunksEdgesAndSelf(chunk, false);
    }
    
    public void updateMesh(ClientChunk chunk, float[] vertices){
        ChunkMesh mesh = allMeshes.get(chunk);
        if(mesh == null)
            mesh = new ChunkMesh();
        
        mesh.setVertices(vertices);
        allMeshes.put(chunk, mesh);
    }
    
    public void rebuildChunk(ClientChunk chunk, boolean important){
        if(!toBuildQueue.contains(chunk)){
            if(important)
                toBuildQueue.addFirst(chunk);
            else
                toBuildQueue.addLast(chunk);
        }
    }
    
    
    @Override
    public ClientChunk getChunk(ChunkPos chunkPos){
        return allChunks.get(chunkPos);
    }
    
    @Override
    public ClientChunk getChunk(int chunkX, int chunkZ){
        return getChunk(new ChunkPos(chunkX, chunkZ));
    }
    
    
    public Collection<ClientChunk> getChunks(){
        return allChunks.values();
    }
    
    public Map<ClientChunk, ChunkMesh> getMeshes(){
        return allMeshes;
    }
    
    public ChunkMesh getMesh(ClientChunk chunk){
        return allMeshes.get(chunk);
    }
    
    
    private boolean isOffTheGrid(ChunkPos chunkPos){
        return distToChunk(chunkPos.x, chunkPos.z, worldOF.getSessionOf().getClientPlayer().getPosition()) <= worldOF.getSessionOf().getOptions().getRenderDistance();
    }

}
