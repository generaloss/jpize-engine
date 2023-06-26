package pize.tests.voxelgame.server.command.source;

import pize.math.vecmath.tuple.Tuple3f;
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
    public Tuple3f getPosition(){
        return player.getPosition();
    }
    
    @Override
    public void sendMessage(String message){
        player.sendMessage(message);
    }
    
}
