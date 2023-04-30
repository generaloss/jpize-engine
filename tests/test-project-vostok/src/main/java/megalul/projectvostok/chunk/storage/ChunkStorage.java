package megalul.projectvostok.chunk.storage;

import megalul.projectvostok.block.BlockState;

import java.util.Arrays;

import static megalul.projectvostok.chunk.ChunkUtils.*;

public class ChunkStorage{
    
    protected final short[] blocks;
    protected final short[] heightMap;
    protected final byte[] skyLight;
    protected final byte[] blockLight;
    protected short maxY;
    protected int blockCount;
    
    public ChunkStorage(){
        blocks = new short[C_VOLUME];
        heightMap = new short[AREA];
        skyLight = new byte[C_VOLUME];
        blockLight = new byte[C_VOLUME];
        
        Arrays.fill(skyLight, (byte) 0);
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
     * @param x локальная координата по оси X
     * @param y локальная координата по оси Y
     * @param z локальная координата по оси Z
     * @return айди блока
     */
    public byte getBlockID(int x, int y, int z){
        return BlockState.getID(getBlock(x, y, z));
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
        final byte currentID = getBlockID(x, y, z);
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
    
    
    public void setSkyLight(int x, int y, int z, int level){
        skyLight[getIndexC(x, y, z)] = (byte) level;
    }
    
    public byte getSkyLight(int x, int y, int z){
        return skyLight[getIndexC(x, y, z)];
    }
    
    
    public void setBlockLight(int x, int y, int z, int level){
        blockLight[getIndexC(x, y, z)] = (byte) level;
    }
    
    public byte getBlockLight(int x, int y, int z){
        return blockLight[getIndexC(x, y, z)];
    }
    
    
    public boolean isEmpty(){
        return blockCount == 0;
    }
    
}
