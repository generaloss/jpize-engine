package jpize.tests.minecraftosp.main.command.source;

import jpize.math.vecmath.vector.Vec3f;
import jpize.tests.minecraftosp.server.player.ServerPlayer;
import jpize.tests.minecraftosp.main.text.Component;

public abstract class CommandSource{
    
    public abstract Vec3f getPosition();
    
    public abstract void sendMessage(Component message);
    
    
    public ServerPlayer asPlayer(){
        return ((CommandSourcePlayer) this).getPlayer();
    }
    
}
