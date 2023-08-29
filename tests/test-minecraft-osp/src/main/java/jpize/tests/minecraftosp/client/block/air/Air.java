package jpize.tests.minecraftosp.client.block.air;

import jpize.tests.minecraftosp.client.block.Block;
import jpize.tests.minecraftosp.client.resources.GameResources;

public class Air extends Block{

    public Air(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        newState();
    }
    
}