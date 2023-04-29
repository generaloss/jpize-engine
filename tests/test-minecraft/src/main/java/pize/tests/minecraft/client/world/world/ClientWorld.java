package pize.tests.minecraft.client.world.world;

import pize.tests.minecraft.client.world.chunk.ClientChunkProvider;

public class ClientWorld extends IWorld{

    private final ClientChunkProvider chunkProvider;

    public ClientWorld(){
        chunkProvider = new ClientChunkProvider();
    }


    @Override
    public IChunkProvider getChunkProvider(){
        return chunkProvider;
    }

}
