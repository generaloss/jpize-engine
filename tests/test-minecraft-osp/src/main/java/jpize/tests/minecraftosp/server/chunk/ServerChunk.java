package jpize.tests.minecraftosp.server.chunk;

import jpize.tests.minecraftosp.client.block.Block;
import jpize.tests.minecraftosp.client.block.Blocks;
import jpize.tests.minecraftosp.main.block.BlockData;
import jpize.tests.minecraftosp.main.chunk.LevelChunk;
import jpize.tests.minecraftosp.main.chunk.storage.ChunkPos;
import jpize.tests.minecraftosp.main.chunk.storage.Heightmap;
import jpize.tests.minecraftosp.server.level.ServerLevel;

public class ServerChunk extends LevelChunk{

    public boolean decorated;


    public ServerChunk(ServerLevel level, ChunkPos position){
        super(level, position);
    }
    
    public ServerLevel getLevel(){
        return (ServerLevel) level;
    }

    @Override
    public boolean setBlockState(int lx, int y, int lz, short blockData){
        final boolean result = super.setBlockState(lx, y, lz, blockData);
        if(result){
            final boolean blockPlaced = BlockData.getID(blockData) != Blocks.AIR.getID();
            for(Heightmap heightmap: heightmaps.values())
                heightmap.update(lx, y, lz, blockPlaced);
            return true;
        }

        return false;
    }
    
    public void setBlockDec(ServerChunk from, boolean others, int lx, int y, int lz, Block block){
        if(this != from || others)
            super.setBlock(lx, y, lz, block);
    }

    public void setBlockDec(int lx, int y, int lz, Block block){
        super.setBlock(lx, y, lz, block);
    }

    public void setBlockStateFast(int lx, int y, int lz, short data){
        super.setBlockState(lx, y, lz, data);
    }


    @Override
    public boolean setBlock(int lx, int y, int lz, Block block){
        final boolean result = super.setBlock(lx, y, lz, block);
        if(result){
            final boolean blockPlaced = block != Blocks.AIR;
            for(Heightmap heightmap: heightmaps.values())
                heightmap.update(lx, y, lz, blockPlaced);
            return true;
        }

        return false;
    }

}
