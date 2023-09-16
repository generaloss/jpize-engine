package jpize.tests.minecraftose.client.block.air;

import jpize.tests.minecraftose.client.block.Block;
import jpize.tests.minecraftose.client.resources.GameResources;

public class Air extends Block{

    public Air(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        newState();
    }
    
}