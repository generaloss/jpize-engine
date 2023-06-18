package pize.tests.voxelgame.client.chunk;

import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.BlockState;
import pize.tests.voxelgame.client.level.ClientChunkManager;
import pize.tests.voxelgame.clientserver.chunk.Chunk;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkBlockUtils;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkHeightUtils;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkPos;
import pize.tests.voxelgame.clientserver.net.packet.CBPacketChunk;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.isOutOfBounds;

public class ClientChunk extends Chunk{
    
    private final ClientChunkManager chunkManagerOf;
    
    public ClientChunk(ClientChunkManager chunkManagerOf, ChunkPos position){
        super(position);
        this.chunkManagerOf = chunkManagerOf;
    }
    
    public ClientChunk(ClientChunkManager chunkManagerOf, CBPacketChunk packet){
        super(packet);
        this.chunkManagerOf = chunkManagerOf;
    }
    
    public ClientChunkManager getManagerOf(){
        return chunkManagerOf;
    }
    
    
    public void rebuild(boolean important){
        chunkManagerOf.rebuildChunk(this, important);
    }
    
    public void rebuild(){
        rebuild(false);
    }
    
    
    public void setBlock(int x, int y, int z, short state){
        if(isOutOfBounds(x, z)){
            storage.setBlock(x, y, z, state);
            return;
        }
        
        final BlockProperties previousBlock = BlockState.getProps(storage.setBlock(x, y, z, state));
        final BlockProperties targetBlock = BlockState.getProps(state);
        if(previousBlock.equals(targetBlock))
            return;
        
        ChunkBlockUtils.updateNeighborChunksEdges(this, x, y, z, state);
        ChunkHeightUtils.updateHeight(storage, x, y, z, !targetBlock.isEmpty());
        
        rebuild(true);
    }
    
    public void setBlockFast(int x, int y, int z, short state){
        if(storage.setBlock(x, y, z, state) != BlockState.getID(state) && !isOutOfBounds(x, z))
            ChunkBlockUtils.updateNeighborChunksEdges(this, x, y, z, state);
    }
    
    
}
