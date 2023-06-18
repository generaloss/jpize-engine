package pize.tests.voxelgame.server.level;

import pize.math.vecmath.vector.Vec2f;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkPos;
import pize.tests.voxelgame.clientserver.entity.Entity;
import pize.tests.voxelgame.clientserver.level.ChunkManager;
import pize.tests.voxelgame.server.chunk.ServerChunk;
import pize.tests.voxelgame.server.chunk.gen.DefaultGenerator;
import pize.tests.voxelgame.server.player.ServerPlayer;
import pize.util.time.PerSecCounter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.getChunkPos;
import static pize.tests.voxelgame.clientserver.level.ChunkManagerUtils.*;

public class ServerChunkManager extends ChunkManager{
    
    private final ServerLevel level;
    
    private final Map<ChunkPos, ServerPlayer> requestedChunks;
    private final CopyOnWriteArrayList<ChunkPos> newFrontiers, frontiers;
    private final Map<ChunkPos, ServerChunk> allChunks;
    private final List<ChunkPos> loadQueue;
    
    public final PerSecCounter findTps, loadTps, checkTps;
    
    public ServerChunkManager(ServerLevel level){
        this.level = level;
        
        requestedChunks = new HashMap<>();
        frontiers = new CopyOnWriteArrayList<>();
        newFrontiers = new CopyOnWriteArrayList<>();
        allChunks = new ConcurrentHashMap<>();
        loadQueue = new CopyOnWriteArrayList<>();
        
        findTps = new PerSecCounter();
        loadTps = new PerSecCounter();
        checkTps = new PerSecCounter();
    }
    
    public void start(){
        newThread(()->{
            findTps.count();
            findChunks();
        }, "Find Chunks");
        
        newThread(()->{
            loadTps.count();
            loadChunks();
        }, "Load Chunks");
        
        newThread(()->{
            checkTps.count();
            checkChunks();
        }, "Check Chunks");
    }
    
    public ServerLevel getLevel(){
        return level;
    }
    
    
    public void loadInitChunkForPlayer(ServerPlayer player){
        final ChunkPos chunkPos = new ChunkPos(
            getChunkPos(player.getPosition().xf()),
            getChunkPos(player.getPosition().zf())
        );
        
        ensureFrontier(chunkPos);
    }
    
    private void findChunks(){
        if(frontiers.size() == 0){
            final Vec2f spawn = level.getConfiguration().getSpawn();
            ensureFrontier(new ChunkPos(
                getChunkPos(spawn.xf()),
                getChunkPos(spawn.yf())
            ));
        }
        
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
        if(frontiers.contains(chunkPos) || isOffTheGrid(chunkPos))
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
        for(ServerChunk chunk: allChunks.values())
            if(isOffTheGrid(chunk.getPosition()))
                unloadChunk(chunk);
    }
    
    public void loadChunk(ChunkPos chunkPos){
        final ServerChunk chunk = new ServerChunk(this, chunkPos);
        DefaultGenerator.getInstance().generate(chunk);
        updateNeighborChunksEdgesAndSelf(chunk, true);
        allChunks.put(chunkPos, chunk);
        
        if(requestedChunks.containsKey(chunkPos)){
            requestedChunks.get(chunkPos).sendPacket(chunk.getStorage().getPacket());
            requestedChunks.remove(chunkPos);
        }
    }

    public void unloadChunk(ServerChunk chunk){
        allChunks.remove(chunk.getPosition());
        updateNeighborChunksEdgesAndSelf(chunk, false);
    }
    
    
    public void requestedChunk(ServerPlayer player, ChunkPos chunkPos){
        final ServerChunk chunk = getChunk(chunkPos);
        if(chunk != null)
            player.sendPacket(chunk.getStorage().getPacket());
        else
            requestedChunks.put(chunkPos, player);
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
        final Vec2f spawn = level.getServer().getLevelManager().getDefaultLevel().getConfiguration().getSpawn();
        if(distToChunk(chunkPos.x, chunkPos.z, spawn) <= level.getServer().getConfiguration().getMaxRenderDistance())
            return false;
        
        for(Entity entity: level.getEntities())
            if(entity instanceof ServerPlayer player)
                if(distToChunk(chunkPos.x, chunkPos.z, player.getPosition()) <= player.getRenderDistance())
                    return false;
        
        return true;
    }

}
