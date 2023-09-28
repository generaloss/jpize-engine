package jpize.tests.mcose.client.block.air;

import jpize.tests.mcose.client.block.Block;
import jpize.tests.mcose.client.resources.GameResources;

public class Air extends Block{

    public Air(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        newState();
    }
    
}