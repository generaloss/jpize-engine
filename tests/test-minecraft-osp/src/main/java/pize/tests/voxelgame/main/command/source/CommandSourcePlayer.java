package pize.tests.voxelgame.main.command.source;

import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.main.text.Component;
import pize.tests.voxelgame.server.player.ServerPlayer;

public class CommandSourcePlayer extends CommandSource{
    
    private final ServerPlayer player;
    
    public CommandSourcePlayer(ServerPlayer player){
        this.player = player;
    }
    
    public ServerPlayer getPlayer(){
        return player;
    }
    
    
    @Override
    public Vec3f getPosition(){
        return player.getPosition();
    }
    
    @Override
    public void sendMessage(Component message){
        player.sendMessage(message);
    }
    
}
