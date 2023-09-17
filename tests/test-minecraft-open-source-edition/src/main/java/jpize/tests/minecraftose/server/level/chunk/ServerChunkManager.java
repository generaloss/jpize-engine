package jpize.tests.minecraftose.server.level.chunk;

import jpize.graphics.util.batch.TextureBatch;
import jpize.math.vecmath.vector.Vec2f;
import jpize.tests.minecraftose.main.chunk.storage.ChunkPos;
import jpize.tests.minecraftose.main.chunk.storage.HeightmapType;
import jpize.tests.minecraftose.main.entity.Entity;
import jpize.tests.minecraftose.main.level.ChunkManager;
import jpize.tests.minecraftose.main.net.packet.clientbound.CBPacketChunk;
import jpize.tests.minecraftose.server.chunk.ServerChunk;
import jpize.tests.minecraftose.server.gen.ChunkGenerator;
import jpize.tests.minecraftose.server.level.ServerLevel;
import jpize.tests.minecraftose.server.player.ServerPlayer;
import jpize.util.time.FpsCounter;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

import static jpize.tests.minecraftose.main.chunk.ChunkUtils.getChunkPos;
import static jpize.tests.minecraftose.main.level.ChunkManagerUtils.distToChunk;

public class ServerChunkManager extends ChunkManager{
    
    private final ServerLevel level;
    
    private final Map<ChunkPos, ServerPlayer> requestedChunks;
    private final CopyOnWriteArrayList<ChunkPos> newFrontiers, frontiers;
    private final Map<ChunkPos, ServerChunk> allChunks, decoratingChunks, generatingChunks;
    private final Queue<ChunkPos> loadQueue;

    public final FpsCounter tps;
    
    private final ExecutorService executorService;
    
    public ServerChunkManager(ServerLevel level){
        this.level = level;
        
        this.requestedChunks = new HashMap<>();
        this.frontiers = new CopyOnWriteArrayList<>();
        this.newFrontiers = new CopyOnWriteArrayList<>();
        this.loadQueue = new LinkedBlockingQueue<>();
        this.allChunks = new ConcurrentHashMap<>();
        this.generatingChunks = new ConcurrentHashMap<>();
        this.decoratingChunks = new ConcurrentHashMap<>();

        this.tps = new FpsCounter();

        this.executorService = Executors.newSingleThreadExecutor(runnable->{
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
            getChunkPos(player.getPosition().xFloor()),
            getChunkPos(player.getPosition().zFloor())
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
                getChunkPos(player.getPosition().xFloor()),
                getChunkPos(player.getPosition().zFloor())
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
            final float alpha = Math.min(chunk.getLifeTimeMillis() / 500F, 1);
            batch.drawQuad(0.3, 0.3, 0.3, alpha,  position.x * size, position.z * size,  size, size);
        }
        for(ServerChunk chunk: decoratingChunks.values()){
            final ChunkPos position = chunk.getPosition();
            batch.drawQuad(0.6, 0.6, 0, 1,  position.x * size, position.z * size,  size, size);
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
            if(checkChunksAround1(chunk))
                decorateChunk(chunk, generator);

        for(ServerChunk chunk: decoratingChunks.values())
            if(checkChunksAround2(chunk))
                lightChunk(chunk);
    }

    private boolean checkChunksAround1(ServerChunk chunk){
        for(ChunkPos neighborPosition: chunk.getNeighbors1())
            if(!isChunkExists(neighborPosition))
                return false;

        return true;
    }

    private boolean checkChunksAround2(ServerChunk chunk){
        for(ChunkPos neighborPosition: chunk.getNeighbors2())
            if(!isChunkExists(neighborPosition))
                return false;

        return true;
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

        // Move
        generatingChunks.put(chunkPos, chunk);
    }

    private void decorateChunk(ServerChunk chunk, ChunkGenerator generator){
        // Decorate neighbors
        for(ChunkPos neighborPos: chunk.getNeighbors1()){
            ServerChunk neighbor = getGeneratingChunk(neighborPos);

            if(neighbor == null)
                neighbor = getChunk(neighborPos);
            if(neighbor == null)
                return;
            if(neighbor.decorated)
                continue;

            neighbor.decorated = true;
            generator.decorate(neighbor);
        }
        // Decorate chunk
        chunk.decorated = true;
        generator.decorate(chunk);
        level.getBlockPool().loadBlocksFor(chunk);

        // Move
        decoratingChunks.put(chunk.getPosition(), chunk);
        generatingChunks.remove(chunk.getPosition());
    }

    private void lightChunk(ServerChunk chunk){
        // Update heightmap
        chunk.getHeightMap(HeightmapType.LIGHT_SURFACE).update();
        chunk.getHeightMap(HeightmapType.SURFACE).update();

        // Update skylight
        chunk.getLevel().getSkyLight().updateSkyLight(chunk);

        // Move
        allChunks.put(chunk.getPosition(), chunk);
        decoratingChunks.remove(chunk.getPosition());
        // Send
        sendChunkIsRequired(chunk);
    }


    private boolean isChunkExists(ChunkPos chunkPosition){
        return allChunks.containsKey(chunkPosition) || decoratingChunks.containsKey(chunkPosition) || generatingChunks.containsKey(chunkPosition);
    }

    public void unloadChunks(){
        for(ServerChunk chunk: allChunks.values())
            if(isOffTheGrid(chunk.getPosition(), 2))
                unloadChunk(chunk);

        for(ServerChunk chunk: decoratingChunks.values())
            if(isOffTheGrid(chunk.getPosition(), 2))
                decoratingChunks.remove(chunk.getPosition());

        for(ServerChunk chunk: generatingChunks.values())
            if(isOffTheGrid(chunk.getPosition(), 2))
                generatingChunks.remove(chunk.getPosition());
    }

    private ServerChunk loadChunk(ChunkPos chunkPos){
        return null;
    }


    public void unloadChunk(ServerChunk chunk){
        allChunks.remove(chunk.getPosition());
    }

    public ServerChunk getGeneratingChunk(ChunkPos chunkPos){
        return generatingChunks.get(chunkPos);
    }

    public ServerChunk getDecoratingChunk(ChunkPos chunkPos){
        return decoratingChunks.get(chunkPos);
    }

    
    @Override
    public ServerChunk getChunk(ChunkPos chunkPos){
        ServerChunk chunk = allChunks.get(chunkPos);
        if(chunk == null)
            chunk = decoratingChunks.get(chunkPos);
        if(chunk == null)
            chunk = generatingChunks.get(chunkPos);
        return chunk;
    }

    @Override
    public ServerChunk getChunk(int chunkX, int chunkZ){
        ServerChunk chunk = getChunk(new ChunkPos(chunkX, chunkZ));
        if(chunk == null)
            chunk = getDecoratingChunk(new ChunkPos(chunkX, chunkZ));
        if(chunk == null)
            chunk = getGeneratingChunk(new ChunkPos(chunkX, chunkZ));
        return chunk;
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
                if(distToChunk(chunkPos.x, chunkPos.z, player.getPosition()) <= player.getRenderDistance() + renderDistanceIncrease)
                    return false;
        
        return true;
    }

}
