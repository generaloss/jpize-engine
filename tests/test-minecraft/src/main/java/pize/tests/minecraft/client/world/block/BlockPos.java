package pize.tests.minecraft.client.world.block;

import pize.math.vecmath.tuple.Tuple3i;

public class BlockPos extends Tuple3i implements Cloneable{

    public BlockPos(){ }

    public BlockPos(int x,int y,int z){
        set(x,y,z);
    }

    public BlockPos(Tuple3i blockPos){
        set(blockPos);
    }


    @Override
    public BlockPos clone(){
        return new BlockPos(this);
    }

}
