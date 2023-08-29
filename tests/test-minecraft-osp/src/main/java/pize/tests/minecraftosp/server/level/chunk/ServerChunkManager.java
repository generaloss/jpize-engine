package pize.tests.minecraftosp.server.level.chunk;

import pize.graphics.util.batch.TextureBatch;
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
                    throw new RuntimeException(e);
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
            ensureFrontier(frontierPos.getNeighbor(-1,  0));
            ensureFrontier(frontierPos.getNeighbor( 1,  0));
            ensureFrontier(frontierPos.getNeighbor( 0, -1));
            ensureFrontier(frontierPos.getNeighbor( 0,  1));
        }
    
        frontiers.removeIf(chunkPos -> isOffTheGrid(chunkPos, 2));
        newFrontiers.removeIf(chunkPos -> isOffTheGrid(chunkPos, 2));
        if(newFrontiers.isEmpty())
            return;
        
        // Load new chunks
        loadQueue.addAll(newFrontiers);
        newFrontiers.clear();
    }
    
    private void ensureFrontier(ChunkPos chunkPos){
        if(frontiers.contains(chunkPos) || isOffTheGrid(chunkPos, 2))
            return;
        
        frontiers.add(chunkPos);
        newFrontiers.add(chunkPos);
    }


    public void render(TextureBatch batch, float size){
        for(ServerChunk chunk: generatingChunks.values()){
            final ChunkPos position = chunk.getPosition();
            final float grayscale = Math.min(chunk.getLifeTimeMillis() / 500F * 0.3F, 0.3F) + 0.1F;
            batch.drawQuad(grayscale, grayscale, grayscale, 1,  position.x * size, position.z * size,  size, size);
        }
        for(ServerChunk chunk: allChunks.values()){
            final ChunkPos position = chunk.getPosition();
            batch.drawQuad(0, 0.8, 0, 1,  position.x * size, position.z * size,  size, size);
        }
    }
    
    
    private void loadChunks(){
        final ChunkGenerator generator = level.getConfiguration().getGenerator();
        if(generator == null)
            return;

        // Load
        for(ChunkPos chunkPos: loadQueue){
            loadQueue.remove(chunkPos);
            if(isOffTheGrid(chunkPos, 2))
                continue;


            // [In Future]: ServerChunk chunk = loadChunk(chunkPos); // Load
            // [In Future]: if(chunk == null)
            generateChunk(chunkPos, generator); // Start generate
        }
        // [Debug]: System.out.println("generateChunk.size() => " + generateChunks.size() + "; allChunks.size() => " + allChunks.size() + ";");
        for(ServerChunk chunk: generatingChunks.values())
            if(checkChunksAround(chunk))
                decorateChunk(chunk, generator);
    }

    private boolean checkChunksAround(ServerChunk chunk){
        for(ChunkPos neighborPosition: chunk.getNeighbors())
            if(!isChunkExists(neighborPosition))
                return false;

        return true;
    }

    private boolean isChunkExists(ChunkPos chunkPosition){
        return allChunks.containsKey(chunkPosition) || generatingChunks.containsKey(chunkPosition);
    }
    
    public void unloadChunks(){
        for(ServerChunk chunk: allChunks.values())
            if(isOffTheGrid(chunk.getPosition(), 2))
                unloadChunk(chunk);

        for(ServerChunk chunk: generatingChunks.values())
            if(isOffTheGrid(chunk.getPosition(), 2))
                generatingChunks.remove(chunk.getPosition());
    }
    
    private ServerChunk loadChunk(ChunkPos chunkPos){
        return null;
    }

    private void generateChunk(ChunkPos chunkPos, ChunkGenerator generator){
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

    private void decorateChunk(ServerChunk chunk, ChunkGenerator generator){
        // Decorate neighbors
        for(ChunkPos neighborPosition: chunk.getNeighbors()){
            ServerChunk neighbor = getGeneratingChunk(neighborPosition);

            if(neighbor == null)
                neighbor = getChunk(neighborPosition);
            if(neighbor == null)
                return;
            if(neighbor.decorated)
                continue;

            neighbor.decorated = true;
            generator.decorate(neighbor, true);
        }
        // Decorate chunk
        chunk.decorated = true;
        generator.decorate(chunk, false);

        // Update heightmap
        //chunk.getHeightMap(HeightmapType.SURFACE).update();
        chunk.getHeightMap(HeightmapType.LIGHT_SURFACE).update();

        // Update skylight
        chunk.getLevel().getSkyLight().updateSkyLight(chunk);

        // Add to list
        addToAllChunks(chunk);
    }


    public void unloadChunk(ServerChunk chunk){
        allChunks.remove(chunk.getPosition());
    }

    public ServerChunk getGeneratingChunk(ChunkPos chunkPos){
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
