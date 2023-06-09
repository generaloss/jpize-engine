package pize.tests.voxelgame.server.world;

import pize.tests.voxelgame.clientserver.chunk.storage.ChunkPos;
import pize.tests.voxelgame.clientserver.world.ChunkManager;
import pize.tests.voxelgame.server.chunk.ServerChunk;
import pize.tests.voxelgame.server.chunk.gen.DefaultGenerator;
import pize.tests.voxelgame.server.player.OnlinePlayer;
import pize.util.time.PerSecCounter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.getChunkPos;
import static pize.tests.voxelgame.clientserver.world.ChunkManagerUtils.*;

public class ServerChunkManager extends ChunkManager{
    
    private final ServerWorld worldOF;
    
    private final CopyOnWriteArrayList<ChunkPos> newFrontiers, frontiers;
    private final Map<ChunkPos, ServerChunk> allChunks;
    private final List<ChunkPos> loadQueue;
    
    public final PerSecCounter findTps, loadTps, checkTps;
    
    public ServerChunkManager(ServerWorld worldOF){
        this.worldOF = worldOF;
        
        frontiers = new CopyOnWriteArrayList<>();
        newFrontiers = new CopyOnWriteArrayList<>();
        allChunks = new ConcurrentHashMap<>();
        loadQueue = new CopyOnWriteArrayList<>();
        
        findTps = new PerSecCounter();
        newThread(()->{
            findTps.count();
            findChunks();
        }, "Find Chunks");
        
        loadTps = new PerSecCounter();
        newThread(()->{
            loadTps.count();
            loadChunks();
        }, "Load Chunks");
        
        checkTps = new PerSecCounter();
        newThread(()->{
            checkTps.count();
            checkChunks();
        }, "Check Chunks");
    }
    
    public ServerWorld getWorldOf(){
        return worldOF;
    }
    
    
    public void loadInitChunkForPlayer(OnlinePlayer player){
        ChunkPos chunkPos = new ChunkPos(
            getChunkPos(player.getPosition().xf()),
            getChunkPos(player.getPosition().zf())
        );
        
        ensureFrontier(chunkPos);
    }
    
    private void findChunks(){
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

    public void checkChunks(){
        for(ServerChunk chunk: allChunks.values()){
            if(isOffTheGrid(chunk.getPosition()))
                unloadChunk(chunk);
        }
    }
    
    public void loadChunk(ChunkPos chunkPos){
        ServerChunk chunk = new ServerChunk(this, chunkPos);
        allChunks.put(chunkPos, chunk);
        DefaultGenerator.getInstance().generate(chunk);
        worldOF.getLight().updateChunkSkyLight(chunk);
        
        updateNeighborChunksEdgesAndSelf(chunk, true);
    }

    public void unloadChunk(ServerChunk chunk){
        allChunks.remove(chunk.getPosition());
        updateNeighborChunksEdgesAndSelf(chunk, false);
    }
    
    
    @Override
    public ServerChunk getChunk(ChunkPos chunkPos){
        return allChunks.get(chunkPos);
    }

    @Override
    public ServerChunk getChunk(int chunkX, int chunkZ){
        return getChunk(new ChunkPos(chunkX, chunkZ));
    }

    public Collection<ServerChunk> getChunks(){
        return allChunks.values();
    }

    
    private boolean isOffTheGrid(ChunkPos chunkPos){
        if(distToChunk(chunkPos.x, chunkPos.z, worldOF.getServerOf().getDefaultWorld().getConfiguration().getSpawn()) <= worldOF.getServerOf().getConfiguration().getMaxRenderDistance())
            return false;
        
        for(OnlinePlayer player: worldOF.getPlayersIn())
            if(distToChunk(chunkPos.x, chunkPos.z, player.getPosition()) <= player.getRenderDistance())
                return false;
        
        return true;
    }

}
