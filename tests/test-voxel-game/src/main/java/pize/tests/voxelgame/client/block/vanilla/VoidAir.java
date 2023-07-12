package pize.tests.voxelgame.client.block.vanilla;

import pize.tests.voxelgame.client.block.BlockProperties;

public class VoidAir extends BlockProperties{
    
    public VoidAir(int id){
        super(id);
        
        solid = false;
        lightLevel = 0;
        opacity = 0;
        collideShape = null;
        cursorShape = null;
        model = null;
    }
    
}
