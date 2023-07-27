package pize.tests.voxelgame.client.block.vanilla;

import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.resources.GameResources;

public class VoidAir extends BlockProperties{
    
    public VoidAir(int id){
        super(id);
    }

    @Override
    protected void load(GameResources resources){
        solid = false;
        lightLevel = 0;
        opacity = 0;
        translucent = false;
        soundPack = null;
    }
    
}
