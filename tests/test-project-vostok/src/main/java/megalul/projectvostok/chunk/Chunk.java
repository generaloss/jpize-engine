package megalul.projectvostok.chunk;

import megalul.projectvostok.block.BlockState;
import megalul.projectvostok.chunk.data.ChunkField;
import megalul.projectvostok.chunk.data.ChunkPos;
import megalul.projectvostok.world.ChunkProvider;

public class Chunk{

    private final ChunkProvider providerOf;
    
    private final ChunkPos position;
    private final ChunkField field;

    public Chunk(ChunkProvider providerOf, ChunkPos position){
        this.providerOf = providerOf;

        this.position = position;
        field = new ChunkField(this);
    }


    public ChunkProvider getProvider(){
        return providerOf;
    }

    public ChunkPos getPos(){
        return position;
    }

    public int getHeight(int x, int z){
        return field.getHeightDepthMap().getHeight(x, z);
    }

    public int getDepth(int x, int z){
        return field.getHeightDepthMap().getDepth(x, z);
    }

    public BlockState getBlock(int x, int y, int z){
        return field.get(x, y, z);
    }

    public void setBlock(int x, int y, int z, BlockState block){
        field.set(x, y, z, block);
    }
    
    public int fastGetBlockID(int x, int y, int z){
        return field.fastGetID(x, y, z);
    }
    
    public void fastSetBlock(int x, int y, int z, BlockState block){
        field.fastSet(x, y, z, block);
    }
    
    public boolean isDirty(){
        return field.isDirty();
    }

    public void onMeshUpdate(){
        field.onMeshUpdate();
    }

    public int getMaxY(){
        return field.getHeightDepthMap().getMax();
    }

    public int getMinY(){
        return field.getHeightDepthMap().getMin();
    }
    
    public ChunkField getField(){
        return field;
    }


    @Override
    public boolean equals(Object object){
        if(object == this)
            return true;
        if(object == null || object.getClass() != getClass())
            return false;
        Chunk chunk = (Chunk) object;
        return position.equals(chunk.position);
    }

    @Override
    public int hashCode(){
        return position.hashCode();
    }
    
}
