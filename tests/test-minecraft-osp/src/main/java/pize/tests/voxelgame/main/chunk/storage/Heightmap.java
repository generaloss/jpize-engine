package pize.tests.voxelgame.main.chunk.storage;

import pize.tests.voxelgame.main.chunk.LevelChunk;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.*;

public class Heightmap{
    
    private final LevelChunk chunk;
    private final HeightmapType type;
    private final short[] values;
    
    public Heightmap(LevelChunk chunk, HeightmapType type){
        this.chunk = chunk;
        this.type = type;
        this.values = new short[AREA];
    }
    
    public Heightmap(LevelChunk chunk, HeightmapType type, short[] values){
        this.chunk = chunk;
        this.type = type;
        this.values = values;
    }
    
    public LevelChunk getChunk(){
        return chunk;
    }
    
    public HeightmapType getType(){
        return type;
    }
    
    
    public short[] getValues(){
        return values;
    }
    
    public int getHeight(int lx, int lz){
        return values[getIndex(lx, lz)];
    }
    
    public void setHeight(int lx, int lz, int height){
        values[getIndex(lx, lz)] = (short) height;
    }
    
    
    public void update(int lx, int y, int lz, boolean blockPlaced){
        int height = getHeight(lx, lz);
        
        if(y == height && !blockPlaced)
            for(height--; type.isOpaque.test(chunk.getBlockID(lx, height, lz)) && height >= 0; height--);
        else if(y > height && blockPlaced)
            height = y;
        
        setHeight(lx, lz, height);
    }
    
    public void update(int lx, int lz){
        int height = HEIGHT;
        for(height--; type.isOpaque.test(chunk.getBlockID(lx, height, lz)) && height >= 0; height--);
        setHeight(lx, lz, height);
    }
    
    public void update(){
        for(int lx = 0; lx < SIZE; lx++)
            for(int lz = 0; lz < SIZE; lz++)
                update(lx, lz);
    }

    public void updateFrom(Heightmap heightmap){
        System.arraycopy(heightmap.values, 0, values, 0, heightmap.values.length);
    }
    
}
