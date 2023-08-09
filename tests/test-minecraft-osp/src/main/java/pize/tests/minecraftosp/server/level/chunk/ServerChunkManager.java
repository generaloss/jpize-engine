package pize.tests.minecraftosp.server.level.chunk;

import pize.math.Mathc;
import pize.math.vecmath.vector.Vec2f;
import pize.tests.minecraftosp.main.chunk.storage.ChunkPos;
import pize.tests.minecraftosp.main.chunk.storage.HeightmapType;
import pize.tests.minecraftosp.main.entity.Entity;
import pize.tests.minecraftosp.main.level.ChunkManager;
import pize.tests.minecraftosp.main.net.packet.CBPacketChunk;
import pize.tests.minecraftosp.server.chunk.ServerChunk;
import pize.tests.minecraftosp.server.gen.ChunkGenerator;
import pize.tests.minecraftosp.server.level.ServerLevel;
import pize.tests.minecraftosp.server.player.ServerPlayer;
import pize.util.time.FpsCounter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

import static pize.tests.minecraftosp.main.chunk.ChunkUtils.getChunkPos;
import static pize.tests.minecraftosp.main.level.ChunkManagerUtils.distToChunk;

public class ServerChunkManager extends ChunkManager{
    
    private final ServerLevel level;
    
    private final Map<ChunkPos, ServerPlayer> requestedChunks;
    private final CopyOnWriteArrayList<ChunkPos> newFrontiers, frontiers;
    private final Map<ChunkPos, ServerChunk> allChunks;
    private final Map<ChunkPos, ServerChunk> generatingChunks;
    private final Queue<ChunkPos> loadQueue;

    private final BlockPool blockPool;

    public final FpsCounter tps;
    
    private final ExecutorService executorService;
    
    public ServerChunkManager(ServerLevel level){
        this.level = level;
        
        requestedChunks = new HashMap<>();
        frontiers = new CopyOnWriteArrayList<>();
        newFrontiers = new CopyOnWriteArrayList<>();
        loadQueue = new LinkedBlockingQueue<>();
        allChunks = new ConcurrentHashMap<>();
        generatingChunks = new ConcurrentHashMap<>();

        blockPool = new BlockPool(this);
        
        tps = new FpsCounter();
   
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
        generatingChunks.remove(chunk.getPosition());
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
        if(frontiers.isEmpty()){
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
        if(newFrontiers.isEmpty())
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
            if(isOffTheGrid(chunkPos))
                continue;

            // Load / Generate
            // ServerChunk chunk = loadChunk(chunkPos);
            // if(chunk == null)
            generateChunk(chunkPos); // Start generate
        }
        // [Debug]: System.out.println("generateChunk.size() => " + generateChunks.size() + "; allChunks.size() => " + allChunks.size() + ";");
        for(ServerChunk chunk: generatingChunks.values()){
            if(!checkChunkDist(chunk))
                continue;

            blockPool.loadBlocksFor(chunk);
            // Update skylight
            chunk.getHeightMap(HeightmapType.OPAQUE).update();
            chunk.getLevel().getLight().updateSkyLight(chunk);

            // Add to list
            addToAllChunks(chunk);
        }
    }

    private final ArrayList<ChunkPos> neighborsList = new ArrayList<>();

    private boolean checkChunkDist(ServerChunk chunk){
        final ChunkPos chunkPos = chunk.getPosition();

        neighborsList.clear();

        neighborsList.add(chunkPos.getNeighbor( 0, -1));
        neighborsList.add(chunkPos.getNeighbor(-1, -1));
        neighborsList.add(chunkPos.getNeighbor( 1, -1));

        neighborsList.add(chunkPos.getNeighbor( 0,  0));
        neighborsList.add(chunkPos.getNeighbor(-1,  0));
        neighborsList.add(chunkPos.getNeighbor( 1,  0));

        neighborsList.add(chunkPos.getNeighbor( 0,  1));
        neighborsList.add(chunkPos.getNeighbor(-1,  1));
        neighborsList.add(chunkPos.getNeighbor( 1,  1));

        for(ChunkPos neighborPosition: neighborsList)
            if(!isChunkExists(neighborPosition))
                return false;

        return true;
    }

    private boolean isChunkExists(ChunkPos chunkPosition){
        return allChunks.containsKey(chunkPosition) || generatingChunks.containsKey(chunkPosition);
    }
    
    public void unloadChunks(){
        for(ServerChunk chunk: allChunks.values())
            if(isOffTheGrid(chunk.getPosition()))
                unloadChunk(chunk);

        for(ServerChunk chunk: generatingChunks.values())
            if(isOffTheGrid(chunk.getPosition(), Mathc.sqrt(1F * 2)))
                generatingChunks.remove(chunk.getPosition());
    }
    
    public ServerChunk loadChunk(ChunkPos chunkPos){
        return null;
    }

    public void generateChunk(ChunkPos chunkPos){
        final ChunkGenerator generator = level.getConfiguration().getGenerator();
        if(generator == null)
            return;

        // If Generated
        if(generatingChunks.containsKey(chunkPos))
            return;
        if(allChunks.containsKey(chunkPos))
            return;

        // Generate Base
        final ServerChunk chunk = new ServerChunk(level, chunkPos);
        generator.generate(chunk);
        generatingChunks.put(chunkPos, chunk);
    }


    public void unloadChunk(ServerChunk chunk){
        allChunks.remove(chunk.getPosition());
    }


    public BlockPool getBlockPool(){
        return blockPool;
    }

    public ServerChunk getProcessChunk(ChunkPos chunkPos){
        return generatingChunks.get(chunkPos);
    }

    
    @Override
    public ServerChunk getChunk(ChunkPos chunkPos){
        return allChunks.get(chunkPos);
    }

    @Override
    public ServerChunk getChunk(int chunkX, int chunkZ){
        return getChunk(new ChunkPos(chunkX, chunkZ));
    }
    
    
    private boolean isOffTheGrid(ChunkPos chunkPos){
        return isOffTheGrid(chunkPos, 0);
    }
    
    private boolean isOffTheGrid(ChunkPos chunkPos, float renderDistanceIncrease){
        final Vec2f spawn = level.getServer().getLevelManager().getDefaultLevel().getConfiguration().getWorldSpawn();
        if(distToChunk(chunkPos.x, chunkPos.z, spawn) <= level.getServer().getConfiguration().getMaxRenderDistance() + renderDistanceIncrease)
            return false;
        
        for(Entity entity: level.getEntities())
            if(entity instanceof ServerPlayer player)
                if(distToChunk(chunkPos.x, chunkPos.z, player.getPosition()) <= player.getRenderDistance())
                    return false;
        
        return true;
    }

}
