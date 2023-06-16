package pize.tests.voxelgame.client.entity;

import pize.physic.BoundingBox;
import pize.tests.voxelgame.client.entity.model.PlayerModel;
import pize.tests.voxelgame.clientserver.entity.Entity;
import pize.tests.voxelgame.clientserver.world.World;

public class RemotePlayer extends Entity{

    public static final float PLAYER_HEIGHT = 1.8F;
    public static final float PLAYER_WIDTH = 0.6F;
    public static final float PLAYER_EYES_HEIGHT = 1.62F;
    public static final float PLAYER_JUMP_HEIGHT = 1.25F;


    private final String name;
    private PlayerModel model;

    public RemotePlayer(World worldOF, String name){
        super(new BoundingBox(
                -PLAYER_WIDTH / 2, 0            , -PLAYER_WIDTH / 2,
                 PLAYER_WIDTH / 2, PLAYER_HEIGHT,  PLAYER_WIDTH / 2
        ), worldOF);

        this.name = name;
    }


    public String getName(){
        return name;
    }

    public PlayerModel getModel(){
        return model;
    }


    @Override
    public void onLivingUpdate(){
        if(model == null)
            model = new PlayerModel(this);
    }

    @Override
    public float getEyes(){
        return PLAYER_EYES_HEIGHT;
    }

}
