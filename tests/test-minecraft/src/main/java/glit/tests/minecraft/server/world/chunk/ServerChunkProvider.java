package glit.tests.minecraft.server.world.chunk;

import glit.tests.minecraft.client.world.chunk.Chunk;
import glit.tests.minecraft.client.world.chunk.ChunkManager;
import glit.tests.minecraft.client.world.chunk.utils.ChunkPos;
import glit.tests.minecraft.client.world.chunk.utils.IChunk;
import glit.tests.minecraft.server.world.gen.ChunkGenerator;
import glit.tests.minecraft.server.world.world.ServerWorld;
import glit.tests.minecraft.client.world.world.IChunkProvider;

public class ServerChunkProvider extends IChunkProvider{

    private final ServerWorld worldOf;
    private final ChunkManager manager;
    private final ChunkLoader loader;
    private final ChunkGenerator generator;

    public ServerChunkProvider(ServerWorld worldOf,ChunkGenerator generator){
        this.worldOf = worldOf;
        manager = new ChunkManager();
        loader = new ChunkLoader();
        this.generator = generator;
    }


    @Override
    public IChunk getChunk(ChunkPos pos,boolean load){
        IChunk chunk = manager.get(pos);
        if(chunk == ChunkManager.EMPTY_CHUNK){
            chunk = new Chunk(pos);

            if( !loader.load(chunk) )
                generator.generate(chunk);

            manager.put(chunk);
        }
        return chunk;
    }

    public void unloadChunk(ChunkPos pos){
        manager.remove(pos);
    }

}
