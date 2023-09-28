package jpize.tests.mcose.main.command.source;

import jpize.math.vecmath.vector.Vec3f;
import jpize.tests.mcose.server.level.ServerLevel;
import jpize.tests.mcose.server.player.ServerPlayer;
import jpize.tests.mcose.main.text.Component;

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
