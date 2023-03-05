package glit.tests.minecraft.client.world.chunk;

import glit.tests.minecraft.client.world.world.IChunkProvider;
import glit.tests.minecraft.client.world.chunk.mesh.ChunkMeshCollection;
import glit.tests.minecraft.client.world.chunk.utils.ChunkPos;
import glit.tests.minecraft.client.world.chunk.utils.IChunk;

import java.util.HashMap;
import java.util.Map;

public class ClientChunkProvider extends IChunkProvider{

    private final ChunkManager manager;
    private final Map<ChunkPos, ChunkMeshCollection> meshes;

    public ClientChunkProvider(){
        manager = new ChunkManager();
        meshes = new HashMap<>();
    }


    @Override
    public IChunk getChunk(ChunkPos pos, boolean load){
        IChunk chunk = manager.get(pos);
        if(chunk == ChunkManager.EMPTY_CHUNK){
            chunk = new Chunk(pos);
            manager.put(chunk);
        }
        return chunk;
    }

}
