package pize.tests.minecraft.client.game;

import pize.tests.minecraft.client.net.ClientPlayerNetHandler;
import pize.tests.minecraft.client.world.world.ClientWorld;
import pize.tests.minecraft.server.entity.PlayerEntity;

public class ClientGame{

    private final Session session;

    private ClientWorld world;
    private final ClientPlayerNetHandler netHandler;
    private PlayerEntity playerEntity;
    private boolean inGame;

    public ClientGame(Session sessions){
        this.session = sessions;

        netHandler = new ClientPlayerNetHandler(this);
    }


    public void createWorldObject(){
        inGame = true;

        playerEntity = new PlayerEntity();
        //player.pos().set(world.);

        world = new ClientWorld();
        //world.addEntity(player);
        session.getGameRenderer().getWorldRenderer().setRenderWorld(world);
    }


    public ClientWorld getWorld(){
        return world;
    }

    public PlayerEntity getPlayerEntity(){
        return playerEntity;
    }

    public ClientPlayerNetHandler getNetHandler(){
        return netHandler;
    }

    public boolean isInGame(){
        return inGame;
    }

}
