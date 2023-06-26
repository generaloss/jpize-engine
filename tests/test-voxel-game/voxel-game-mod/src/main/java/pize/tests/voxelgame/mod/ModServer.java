package pize.tests.voxelgame.mod;

import pize.tests.voxelgame.base.modification.api.DedicatedServerModInitializer;

public class ModServer implements DedicatedServerModInitializer{
    
    @Override
    public void onInitializeServer(){
        System.out.println("[Voxel Game Mod]: server point initialized");
    }
    
}
