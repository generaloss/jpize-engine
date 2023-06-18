package pize.tests.voxelgame.client.level;

import pize.tests.voxelgame.client.chunk.ClientChunk;
import pize.tests.voxelgame.client.chunk.mesh.ChunkBuilder;
import pize.tests.voxelgame.client.chunk.mesh.ChunkMesh;
import pize.tests.voxelgame.client.entity.LocalPlayer;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkPos;
import pize.tests.voxelgame.clientserver.net.packet.CBPacketChunk;
import pize.tests.voxelgame.clientserver.net.packet.SBPacketChunkRequest;
import pize.tests.voxelgame.clientserver.level.ChunkManager;
import pize.util.time.PerSecCounter;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.getChunkPos;
import static pize.tests.voxelgame.clientserver.level.ChunkManagerUtils.*;

public class ClientChunkManager extends ChunkManager{
    
    private final ClientLevel level;
    
    public final PerSecCounter findTps, buildTps, checkTps;
    
    private final ConcurrentHashMap<ChunkPos, Long> requestedChunks;
    private final CopyOnWriteArrayList<ChunkPos> frontiers;
    private final ConcurrentHashMap<ChunkPos, ClientChunk> allChunks;
    private final LinkedList<ClientChunk> toBuildQueue;
    private final ConcurrentHashMap<ClientChunk, float[]> built;
    private final ConcurrentHashMap<ClientChunk, ChunkMesh> allMeshes;
    private final CopyOnWriteArrayList<ChunkMesh> toDispose;
    
    public ClientChunkManager(ClientLevel level){
        this.level = level;
        
        requestedChunks = new ConcurrentHashMap<>();
        frontiers = new CopyOnWriteArrayList<>();
        allChunks = new ConcurrentHashMap<>();
        toBuildQueue = new LinkedList<>();
        built = new ConcurrentHashMap<>();
        allMeshes = new ConcurrentHashMap<>();
        toDispose = new CopyOnWriteArrayList<>();
        
        findTps = new PerSecCounter();
        buildTps = new PerSecCounter();
        checkTps = new PerSecCounter();
    }
    
    public ClientLevel getLevel(){
        return level;
    }
    
    
    public void start(){
        newThread(()->{
            findTps.count();
            findChunks();
        }, "Find Chunks");
        
        newThread(()->{
            buildTps.count();
            buildChunks();
        }, "Build Chunks");
        
        newThread(()->{
            checkTps.count();
            checkChunks();
        }, "Check Chunks");
    }
    
    
    private void findChunks(){
        if(frontiers.size() == 0){
            final LocalPlayer player = level.getSession().getGame().getPlayer();
            ensureFrontier(new ChunkPos(
                getChunkPos(player.getPosition().xf()),
                getChunkPos(player.getPosition().zf())
            ));
        }
        
        for(int i = 0; i < frontiers.size(); i++){
            final ChunkPos frontierPos = frontiers.get(i);
            
            ensureFrontier(frontierPos.getNeighbor(-1, 0));
            ensureFrontier(frontierPos.getNeighbor(1, 0));
            ensureFrontier(frontierPos.getNeighbor(0, -1));
            ensureFrontier(frontierPos.getNeighbor(0, 1));
            
            if(!allChunks.containsKey(frontierPos) && !requestedChunks.containsKey(frontierPos)){
                getLevel().getSession().getGame().sendPacket(
                    new SBPacketChunkRequest(frontierPos.x, frontierPos.z)
                );
                requestedChunks.put(frontierPos, System.currentTimeMillis());
            }
        }
        
        frontiers.removeIf(this::isOffTheGrid);
    }
    
    private void ensureFrontier(ChunkPos chunkPos){
        if(frontiers.contains(chunkPos) || isOffTheGrid(chunkPos))
            return;
        
        frontiers.add(chunkPos);
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
        
        for(Map.Entry<ChunkPos, Long> entry: requestedChunks.entrySet())
            if(System.currentTimeMillis() - entry.getValue() > 1000)
                requestedChunks.remove(entry.getKey());
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
    
    public void updateMesh(ClientChunk chunk, float[] vertices){
        ChunkMesh mesh = allMeshes.get(chunk);
        if(mesh == null)
            mesh = new ChunkMesh();
        
        mesh.setVertices(vertices);
        
        allMeshes.put(chunk, mesh);
    }
    
    
    public void receivedChunk(CBPacketChunk packet){
        final ClientChunk chunk = new ClientChunk(this, packet);
        allChunks.put(chunk.getPosition(), chunk);
        toBuildQueue.add(chunk);
        
        requestedChunks.remove(chunk.getPosition());
    }
    
    
    public void unloadChunk(ClientChunk chunk){
        allChunks.remove(chunk.getPosition());
        updateNeighborChunksEdgesAndSelf(chunk, false);
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
        final LocalPlayer player = level.getSession().getGame().getPlayer();
        if(player == null)
            return true;
        
        return
            distToChunk(chunkPos.x, chunkPos.z, player.getPosition())
            > level.getSession().getOptions().getRenderDistance();
    }

}
