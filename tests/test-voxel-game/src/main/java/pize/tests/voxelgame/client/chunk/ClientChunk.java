package pize.tests.voxelgame.client.chunk;

import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.BlockState;
import pize.tests.voxelgame.client.world.ClientChunkManager;
import pize.tests.voxelgame.clientserver.chunk.Chunk;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkBlockUtils;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkHeightUtils;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkPos;
import pize.tests.voxelgame.clientserver.net.packet.PacketChunk;
import pize.tests.voxelgame.clientserver.net.packet.PacketPlayerBlockSet;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.SIZE;
import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.isOutOfBounds;

public class ClientChunk extends Chunk{
    
    private final ClientChunkManager chunkManagerOf;
    
    public ClientChunk(ClientChunkManager chunkManagerOf, ChunkPos position){
        super(chunkManagerOf, position);
        
        this.chunkManagerOf = chunkManagerOf;
    }
    
    public ClientChunk(ClientChunkManager chunkManagerOf, PacketChunk packet){
        super(chunkManagerOf, packet);
        
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
        this.setBlock(x, y, z, state, false);
    }
    
    public void setBlock(int x, int y, int z, short state, boolean net){
        if(isOutOfBounds(x, z)){
            storage.setBlock(x, y, z, state);
            return;
        }
        
        BlockProperties previousBlock = BlockState.getProps(storage.setBlock(x, y, z, state));
        BlockProperties targetBlock = BlockState.getProps(state);
        if(previousBlock.equals(targetBlock))
            return;
        
        if(!net)
            chunkManagerOf.getWorldOf().getSessionOf().getNet().sendPacket(
                new PacketPlayerBlockSet(chunkManagerOf.getWorldOf().getSessionOf().getProfile().getName(), position.x * SIZE + x, y, position.z * SIZE + z, state)
            );
        
        ChunkBlockUtils.updateNeighborChunksEdges(this, x, y, z, state);
        ChunkHeightUtils.updateHeight(storage, x, y, z, !targetBlock.isEmpty());
        
        rebuild(true);
    }
    
    public void setBlockFast(int x, int y, int z, short state){
        if(storage.setBlock(x, y, z, state) != BlockState.getID(state) && !isOutOfBounds(x, z))
            ChunkBlockUtils.updateNeighborChunksEdges(this, x, y, z, state);
    }
    
    
    public void setSkyLight(int x, int y, int z, int level){
        storage.setSkyLight(x, y, z, level);
    }
    
    public void setBlockLight(int x, int y, int z, int level){
        storage.setBlockLight(x, y, z, level);
    }
    
}
