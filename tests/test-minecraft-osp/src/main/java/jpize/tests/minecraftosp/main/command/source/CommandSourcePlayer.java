package jpize.tests.minecraftosp.main.command.source;

import jpize.math.vecmath.vector.Vec3f;
import jpize.tests.minecraftosp.server.level.ServerLevel;
import jpize.tests.minecraftosp.server.player.ServerPlayer;
import jpize.tests.minecraftosp.main.text.Component;

public class CommandSourcePlayer extends CommandSource{
    
    private final ServerPlayer player;
    
    public CommandSourcePlayer(ServerPlayer player){
        this.player = player;
    }
    
    public ServerPlayer getPlayer(){
        return player;
    }

    public ServerLevel getLevel(){
        return player.getLevel();
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
