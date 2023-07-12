package pize.tests.voxelgame.client.block.model;

public abstract class BlockModel{
    
    public BlockSolidModel asSolid(){
        return (BlockSolidModel) this;
    }
    
    public BlockCustomModel asCustom(){
        return (BlockCustomModel) this;
    }
    
}
