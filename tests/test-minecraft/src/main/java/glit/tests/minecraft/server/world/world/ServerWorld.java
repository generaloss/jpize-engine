package glit.tests.minecraft.server.world.world;

import glit.tests.minecraft.server.server.Server;
import glit.tests.minecraft.client.world.world.IChunkProvider;
import glit.tests.minecraft.client.world.world.IWorld;
import glit.tests.minecraft.server.world.chunk.ServerChunkProvider;
import glit.tests.minecraft.server.world.gen.ChunkGenerator;

public class ServerWorld extends IWorld{

    private final Server serverOf;
    private final ServerChunkProvider chunkProvider;

    public ServerWorld(Server serverOf,ChunkGenerator generator){
        this.serverOf = serverOf;
        chunkProvider = new ServerChunkProvider(this,generator);
    }


    @Override
    public IChunkProvider getChunkProvider(){
        return chunkProvider;
    }

}
