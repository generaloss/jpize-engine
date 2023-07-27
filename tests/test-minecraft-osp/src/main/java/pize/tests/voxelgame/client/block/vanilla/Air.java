package pize.tests.voxelgame.client.block.vanilla;

import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.resources.GameResources;

public class Air extends BlockProperties{

    public Air(int id){
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