package pize.tests.voxelgame.clientserver.chunk.storage;

import pize.tests.voxelgame.client.block.BlockState;
import pize.tests.voxelgame.clientserver.chunk.Chunk;
import pize.tests.voxelgame.clientserver.net.packet.PacketChunk;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.*;

public class ChunkStorage{
    
    private final Chunk chunkOF;
    
    protected final short[] blocks;
    protected final short[] heightMap;
    protected short maxY;
    protected short blockCount;
    
    public ChunkStorage(Chunk chunkOF){
        this.chunkOF = chunkOF;
        
        blocks = new short[C_VOLUME];
        heightMap = new short[AREA];
        
    }
    
    public ChunkStorage(Chunk chunkOF, PacketChunk packet){
        this.chunkOF = chunkOF;
        
        blocks = packet.blocks;
        heightMap = packet.heightMap;
        maxY = packet.maxY;
        blockCount = packet.blockCount;
    }
    
    public Chunk getChunkOf(){
        return chunkOF;
    }
    
    
    /**
     * @param x локальная координата по оси X
     * @param z локальная координата по оси Z
     * @return максимальную высоту на данных координитах
     */
    public int getHeight(int x, int z){
        return heightMap[getIndex(x, z)];
    }
    
    /**
     * Устанавливает максимальную высоту на заданных координатах
     * @param x локальная координата по оси X
     * @param z локальная координата по оси Z
     */
    public void setHeight(int x, int z, int height){
        heightMap[getIndex(x, z)] = (short) height;
    }
    
    /**
     * @return максимальную высоту в чанке
     */
    public int getMaxY(){
        return maxY;
    }
    
    /**
     * @param x локальная координата по оси X
     * @param y локальная координата по оси Y
     * @param z локальная координата по оси Z
     * @return класс BlockState в помощь
     */
    public short getBlock(int x, int y, int z){
        return blocks[getIndexC(x, y, z)];
    }
    
    /**
     * Устанавливает блок
     * @param x локальная координата по оси X
     * @param y локальная координата по оси Y
     * @param z локальная координата по оси Z
     * @param state класс BlockState в помощь
     * @return айди блока, который был до установки
     */
    public byte setBlock(int x, int y, int z, short state){
        final byte currentID = BlockState.getID(getBlock(x, y, z));
        final byte targetID = BlockState.getID(state);
        
        if(currentID != targetID){
            blocks[getIndexC(x, y, z)] = state;
            
            if(targetID == 0)
                blockCount--;
            else if(currentID == 0)
                blockCount++;
        }
        
        return currentID;
    }
    
    
    public short getBlockCount(){
        return blockCount;
    }
    
    public boolean isEmpty(){
        return blockCount == 0;
    }
    
    
    public PacketChunk getPacket(){
        return new PacketChunk(
            chunkOF.getPosition().x,
            chunkOF.getPosition().z,
            blocks,
            heightMap,
            maxY,
            blockCount
        );
    }
    
}
