package pize.tests.minecraftosp.main.command.source;

import pize.math.vecmath.vector.Vec3f;
import pize.tests.minecraftosp.server.player.ServerPlayer;
import pize.tests.minecraftosp.main.text.Component;

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
