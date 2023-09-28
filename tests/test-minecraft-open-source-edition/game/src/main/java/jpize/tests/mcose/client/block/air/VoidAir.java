package jpize.tests.mcose.client.block.air;

import jpize.tests.mcose.client.block.Block;
import jpize.tests.mcose.client.resources.GameResources;

public class VoidAir extends Block{
    
    public VoidAir(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        newState();
    }
    
}
