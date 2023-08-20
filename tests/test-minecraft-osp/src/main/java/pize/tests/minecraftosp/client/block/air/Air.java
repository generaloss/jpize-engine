package pize.tests.minecraftosp.client.block.air;

import pize.tests.minecraftosp.client.block.Block;
import pize.tests.minecraftosp.client.resources.GameResources;

public class Air extends Block{

    public Air(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        newState();
    }
    
}