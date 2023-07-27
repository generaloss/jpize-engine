package pize.tests.minecraftosp.main.command.source;

import pize.math.vecmath.vector.Vec3f;
import pize.tests.minecraftosp.server.player.ServerPlayer;
import pize.tests.minecraftosp.main.text.Component;

public abstract class CommandSource{
    
    public abstract Vec3f getPosition();
    
    public abstract void sendMessage(Component message);
    
    
    public ServerPlayer asPlayer(){
        return ((CommandSourcePlayer) this).getPlayer();
    }
    
}
