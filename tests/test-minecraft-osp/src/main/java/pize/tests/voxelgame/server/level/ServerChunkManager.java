package pize.tests.voxelgame.server.level;

import pize.math.vecmath.vector.Vec2f;
import pize.tests.voxelgame.main.chunk.storage.ChunkPos;
import pize.tests.voxelgame.main.chunk.storage.HeightmapType;
import pize.tests.voxelgame.main.entity.Entity;
import pize.tests.voxelgame.main.level.ChunkManager;
import pize.tests.voxelgame.main.net.packet.CBPacketChunk;
import pize.tests.voxelgame.server.chunk.ServerChunk;
import pize.tests.voxelgame.server.chunk.gen.ChunkGenerator;
import pize.tests.voxelgame.server.player.ServerPlayer;
import pize.util.time.PerSecCounter;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.getChunkPos;
import static pize.tests.voxelgame.main.level.ChunkManagerUtils.distToChunk;

public class ServerChunkManager extends ChunkManager{
    
    private final ServerLevel level;
    
    private final Map<ChunkPos, ServerPlayer> requestedChunks;
    private final CopyOnWriteArrayList<ChunkPos> newFrontiers, frontiers;
    private final Map<ChunkPos, ServerChunk> processChunks;
    private final Map<ChunkPos, ServerChunk> allChunks;
    private final Queue<ChunkPos> loadQueue;
    
    public final PerSecCounter tps;
    
    private final ExecutorService executorService;
    
    public ServerChunkManager(ServerLevel level){
        this.level = level;
        
        requestedChunks = new HashMap<>();
        frontiers = new CopyOnWriteArrayList<>();
        newFrontiers = new CopyOnWriteArrayList<>();
        loadQueue = new LinkedBlockingQueue<>();
        processChunks = new ConcurrentHashMap<>();
        allChunks = new ConcurrentHashMap<>();
        
        tps = new PerSecCounter();
   
        executorService = Executors.newSingleThreadExecutor(runnable->{
            final Thread thread = new Thread(runnable, "ServerChunkManager-Thread");
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.setDaemon(true);
            return thread;
        });
    }
    
    public void start(){
        executorService.submit(()->{
            while(!Thread.interrupted()){
                tps.count();

                try{
                    findChunks();
                    loadChunks();
                    unloadChunks();
                }catch(Exception e){
                    e.printStackTrace();
                }
                
                Thread.yield();
            }
        });
    }
    
    public ServerLevel getLevel(){
        return level;
    }


    public void addToAllChunks(ServerChunk chunk){
        allChunks.put(chunk.getPosition(), chunk);
        sendChunkIsRequired(chunk);
    }

    public void sendChunkIsRequired(ServerChunk chunk){
        if(requestedChunks.containsKey(chunk.getPosition())){
            requestedChunks.get(chunk.getPosition()).sendPacket(new CBPacketChunk(chunk));
            requestedChunks.remove(chunk.getPosition());
        }
    }

    public void requestedChunk(ServerPlayer player, ChunkPos chunkPos){
        final ServerChunk chunk = allChunks.get(chunkPos);

        if(chunk != null)
            player.sendPacket(new CBPacketChunk(chunk));
        else
            requestedChunks.put(chunkPos, player);
    }

    
    public void loadInitChunkForPlayer(ServerPlayer player){
        final ChunkPos chunkPos = new ChunkPos(
            getChunkPos(player.getPosition().xf()),
            getChunkPos(player.getPosition().zf())
        );
        
        ensureFrontier(chunkPos);
    }
    
    private void findChunks(){
        // Load spawn chunks
        if(frontiers.size() == 0){
            final Vec2f spawn = level.getConfiguration().getWorldSpawn();
            ensureFrontier(new ChunkPos(
                getChunkPos(spawn.xf()),
                getChunkPos(spawn.yf())
            ));
        }
        
        // Load players chunks
        for(ServerPlayer player: level.getServer().getPlayerList().getPlayers()){
            ensureFrontier(new ChunkPos(
                getChunkPos(player.getPosition().xf()),
                getChunkPos(player.getPosition().zf())
            ));
        }
        
        // Fast flood fill
        for(final ChunkPos frontierPos: frontiers){
            ensureFrontier(frontierPos.getNeighbor(-1, 0));
            ensureFrontier(frontierPos.getNeighbor(1, 0));
            ensureFrontier(frontierPos.getNeighbor(0, -1));
            ensureFrontier(frontierPos.getNeighbor(0, 1));
        }
    
        frontiers.removeIf(this::isOffTheGrid);
        newFrontiers.removeIf(this::isOffTheGrid);
        if(newFrontiers.size() == 0)
            return;
        
        // Load new chunks
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
        // Load
        for(ChunkPos chunkPos: loadQueue){
            loadQueue.remove(chunkPos);
            if(isOffTheGrid(chunkPos, 2))
                continue;

            // Load / Generate
            // ServerChunk chunk = loadChunk(chunkPos);
            // if(chunk == null)
            final ServerChunk chunk = generateChunk(chunkPos); // Generate

            if(chunk == null)
                continue;

            // Update skylight
            chunk.getHeightMap(HeightmapType.OPAQUE).update();
            chunk.getLevel().getLightEngine().updateSkyLight(chunk);

            // Add to list
            addToAllChunks(chunk);
        }
    }
    
    public void unloadChunks(){
        for(ServerChunk chunk: processChunks.values())
            if(isOffTheGrid(chunk.getPosition(), 2))
                unloadChunk(chunk);
    }
    
    public ServerChunk loadChunk(ChunkPos chunkPos){
        return null;
    }

    public ServerChunk generateChunk(ChunkPos chunkPos){
        final ChunkGenerator generator = level.getConfiguration().getGenerator();
        if(generator == null)
            return null;

        // Generate
        final ServerChunk chunk = generateChunkBase(chunkPos);

        // Base-Generate Neighbor Chunks
        generateChunkBase(chunkPos.getNeighbor(+1, +0));
        generateChunkBase(chunkPos.getNeighbor(+1, +1));
        generateChunkBase(chunkPos.getNeighbor(+1, -1));
        generateChunkBase(chunkPos.getNeighbor(-1, +0));
        generateChunkBase(chunkPos.getNeighbor(-1, +1));
        generateChunkBase(chunkPos.getNeighbor(-1, -1));
        generateChunkBase(chunkPos.getNeighbor(+0, +1));
        generateChunkBase(chunkPos.getNeighbor(+0, -1));

        // Generate Decorations
        generator.generateDecorations(chunk);

        return chunk;
    }

    public ServerChunk generateChunkBase(ChunkPos chunkPos){
        // If Generated
        if(processChunks.containsKey(chunkPos))
            return processChunks.get(chunkPos);

        if(allChunks.containsKey(chunkPos))
            return allChunks.get(chunkPos);

        // Generate Base
        final ServerChunk chunk = new ServerChunk(level, chunkPos);
        processChunks.put(chunk.getPosition(), chunk);

        final ChunkGenerator generator = level.getConfiguration().getGenerator();
        generator.generate(chunk);
        return chunk;
    }

    public void unloadChunk(ServerChunk chunk){
        processChunks.remove(chunk.getPosition());
    }
    
    
    @Override
    public ServerChunk getChunk(ChunkPos chunkPos){
        return allChunks.getOrDefault(chunkPos, processChunks.get(chunkPos));
    }

    @Override
    public ServerChunk getChunk(int chunkX, int chunkZ){
        return getChunk(new ChunkPos(chunkX, chunkZ));
    }
    
    
    private boolean isOffTheGrid(ChunkPos chunkPos){
        return isOffTheGrid(chunkPos, 0);
    }
    
    private boolean isOffTheGrid(ChunkPos chunkPos, int renderDistanceIncrease){
        final Vec2f spawn = level.getServer().getLevelManager().getDefaultLevel().getConfiguration().getWorldSpawn();
        if(distToChunk(chunkPos.x, chunkPos.z, spawn) <= level.getServer().getConfiguration().getMaxRenderDistance() + renderDistanceIncrease)
            return false;
        
        for(Entity entity: level.getEntities())
            if(entity instanceof ServerPlayer player)
                if(distToChunk(chunkPos.x, chunkPos.z, player.getPosition()) <= player.getRenderDistance() + renderDistanceIncrease)
                    return false;
        
        return true;
    }

}
