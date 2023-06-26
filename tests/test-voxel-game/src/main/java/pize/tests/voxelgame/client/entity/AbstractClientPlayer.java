package pize.tests.voxelgame.client.entity;

import pize.tests.voxelgame.client.entity.model.PlayerModel;
import pize.tests.voxelgame.base.entity.Player;
import pize.tests.voxelgame.base.level.Level;

public class AbstractClientPlayer extends Player{
    
    private PlayerModel model;
    
    public AbstractClientPlayer(Level level, String name){
        super(level, name);
    }
    
    
    public void tick(){
        super.tick();
        
        if(model == null)
            model = new PlayerModel(this);
    }
    
    public PlayerModel getModel(){
        return model;
    }
    
}
