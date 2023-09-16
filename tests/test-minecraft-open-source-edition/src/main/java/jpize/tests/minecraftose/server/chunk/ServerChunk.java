package jpize.tests.minecraftose.server.chunk;

import jpize.tests.minecraftose.client.block.Block;
import jpize.tests.minecraftose.client.block.Blocks;
import jpize.tests.minecraftose.main.block.BlockData;
import jpize.tests.minecraftose.main.chunk.LevelChunk;
import jpize.tests.minecraftose.main.chunk.storage.ChunkPos;
import jpize.tests.minecraftose.main.chunk.storage.Heightmap;
import jpize.tests.minecraftose.server.level.ServerLevel;

public class ServerChunk extends LevelChunk{

    public boolean decorated;


    public ServerChunk(ServerLevel level, ChunkPos position){
        super(level, position);
    }
    
    public ServerLevel getLevel(){
        return (ServerLevel) level;
    }

    @Override
    public boolean setBlockData(int lx, int y, int lz, short blockData){
        final boolean result = super.setBlockData(lx, y, lz, blockData);
        if(result){
            final boolean blockPlaced = BlockData.getID(blockData) != Blocks.AIR.getID();
            for(Heightmap heightmap: heightmaps.values())
                heightmap.update(lx, y, lz, blockPlaced);
            return true;
        }

        return false;
    }


    public void setBlockFast(int lx, int y, int lz, Block block){
        super.setBlock(lx, y, lz, block);
    }

    public void setBlockDataFast(int lx, int y, int lz, short data){
        super.setBlockData(lx, y, lz, data);
    }


    @Override
    public boolean setBlock(int lx, int y, int lz, Block block){
        final boolean result = super.setBlock(lx, y, lz, block);
        if(result){
            //+ final boolean blockPlaced = block != Blocks.AIR;
            //+ for(Heightmap heightmap: heightmaps.values())
            //+     heightmap.update(lx, y, lz, blockPlaced);
            return true;
        }

        return false;
    }

}
