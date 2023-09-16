package jpize.tests.minecraftose.client.block.air;

import jpize.tests.minecraftose.client.block.Block;
import jpize.tests.minecraftose.client.resources.GameResources;

public class VoidAir extends Block{
    
    public VoidAir(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        newState();
    }
    
}
