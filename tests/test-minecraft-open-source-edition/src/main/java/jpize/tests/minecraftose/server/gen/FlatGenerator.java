package jpize.tests.minecraftose.server.gen;

import jpize.tests.minecraftose.client.block.Blocks;
import jpize.tests.minecraftose.server.chunk.ServerChunk;

import static jpize.tests.minecraftose.main.chunk.ChunkUtils.SIZE;

public class FlatGenerator extends ChunkGenerator{
    
    @Override
    public void generate(ServerChunk chunk){
        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++)
                for(int y = 0; y < 5; y++){
                    if(y == 0)
                        chunk.setBlockFast(i, y, j, Blocks.STONE);
                    else if(y == 4)
                        chunk.setBlockFast(i, y, j, Blocks.GRASS_BLOCK);
                    else
                        chunk.setBlockFast(i, y, j, Blocks.DIRT);
                }
    }

    private static FlatGenerator instance;
    
    public static FlatGenerator getInstance(){
        if(instance == null)
            instance = new FlatGenerator();
        
        return instance;
    }

    @Override
    public String getID(){
        return "flat";
    }

}