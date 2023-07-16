package pize.tests.voxelgame.client.level;

import pize.Pize;
import pize.tests.voxelgame.client.chunk.ClientChunk;
import pize.tests.voxelgame.client.chunk.mesh.ChunkMeshStack;
import pize.tests.voxelgame.client.chunk.mesh.builder.ChunkMeshBuilder;
import pize.tests.voxelgame.client.entity.LocalPlayer;
import pize.tests.voxelgame.main.chunk.storage.ChunkPos;
import pize.tests.voxelgame.main.level.ChunkManager;
import pize.tests.voxelgame.main.level.ChunkManagerUtils;
import pize.tests.voxelgame.main.net.packet.CBPacketChunk;
import pize.tests.voxelgame.main.net.packet.SBPacketChunkRequest;
import pize.util.time.PerSecCounter;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.*;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.getChunkPos;
import static pize.tests.voxelgame.main.level.ChunkManagerUtils.distToChunk;

public class ClientChunkManager extends ChunkManager{
    
    private final ClientLevel level;
    public final PerSecCounter tps;
    
    private final ConcurrentHashMap<ChunkPos, Long> requestedChunks;
    private final CopyOnWriteArrayList<ChunkPos> frontiers;
    private final ConcurrentHashMap<ChunkPos, ClientChunk> allChunks;
    private final ConcurrentLinkedDeque<ClientChunk> toBuildQueue;
    private final ConcurrentHashMap<ClientChunk, ChunkMeshStack> allMeshes;
    
    private final ExecutorService executorService;
    
    public ClientChunkManager(ClientLevel level){
        this.level = level;
        
        requestedChunks = new ConcurrentHashMap<>();
        frontiers = new CopyOnWriteArrayList<>();
        allChunks = new ConcurrentHashMap<>();
        toBuildQueue = new ConcurrentLinkedDeque<>();
        allMeshes = new ConcurrentHashMap<>();
        
        tps = new PerSecCounter();
        
        executorService = Executors.newSingleThreadExecutor(runnable->{
            final Thread thread = new Thread(runnable, "ClientChunkManager-Thread");
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.setDaemon(true);
            return thread;
        });
    }
    
    public ClientLevel getLevel(){
        return level;
    }
    
    
    public void startLoadChunks(){
        executorService.submit(()->{
            while(!Thread.currentThread().isInterrupted()){
                tps.count();
                findChunks();
                buildChunks();
                checkChunks();
                
                Thread.yield();
            }
        });
    }
    
    public void dispose(){
        executorService.shutdownNow();
        
        for(ChunkMeshStack meshStack: allMeshes.values())
            meshStack.dispose();
        
        requestedChunks.clear();
        frontiers      .clear();
        allChunks      .clear();
        toBuildQueue   .clear();
        allMeshes      .clear();
    }
    
    
    private void findChunks(){
        if(frontiers.size() == 0){
            final LocalPlayer player = level.getSession().getGame().getPlayer();
            ensureFrontier(new ChunkPos(
                getChunkPos(player.getPosition().xf()),
                getChunkPos(player.getPosition().zf())
            ));
        }
        
        for(final ChunkPos frontierPos: frontiers){
            ensureFrontier(frontierPos.getNeighbor(-1,  0));
            ensureFrontier(frontierPos.getNeighbor( 1,  0));
            ensureFrontier(frontierPos.getNeighbor( 0, -1));
            ensureFrontier(frontierPos.getNeighbor( 0,  1));
            
            if(!allChunks.containsKey(frontierPos) && !requestedChunks.containsKey(frontierPos) && toBuildQueue.stream().noneMatch(chunk -> chunk.getPosition().equals(frontierPos))){
                getLevel().getSession().getGame().sendPacket(new SBPacketChunkRequest(frontierPos.x, frontierPos.z));
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
            final ClientChunk chunk = toBuildQueue.poll();
            if(chunk == null)
                continue;
            
            if(isOffTheGrid(chunk.getPosition()))
                continue;
            
            ChunkMeshStack meshStack = allMeshes.get(chunk);
            if(meshStack == null)
                meshStack = new ChunkMeshStack();
            
            ChunkMeshBuilder.build(chunk, meshStack);
            allMeshes.put(chunk, meshStack);
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
                
                final ChunkMeshStack meshStack = allMeshes.get(chunk);
                if(meshStack != null)
                    Pize.execSync(meshStack::dispose);
            }
        
        for(Map.Entry<ChunkPos, Long> entry: requestedChunks.entrySet())
            if(System.currentTimeMillis() - entry.getValue() > 1000)
                requestedChunks.remove(entry.getKey());
    }
    
    
    public void receivedChunk(CBPacketChunk packet){
        final ClientChunk chunk = packet.getChunk(level);
        chunk.rebuild(false);
        allChunks.put(chunk.getPosition(), chunk);
        
        ChunkManagerUtils.rebuildNeighborChunks(chunk);
        
        requestedChunks.remove(chunk.getPosition());
    }
    
    
    public void unloadChunk(ClientChunk chunk){
        allChunks.remove(chunk.getPosition());
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
    
    public Map<ClientChunk, ChunkMeshStack> getMeshes(){
        return allMeshes;
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
