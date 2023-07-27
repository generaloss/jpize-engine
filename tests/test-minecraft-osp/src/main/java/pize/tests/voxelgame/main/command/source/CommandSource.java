package pize.tests.voxelgame.main.command.source;

import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.main.text.Component;
import pize.tests.voxelgame.server.player.ServerPlayer;

public abstract class CommandSource{
    
    public abstract Vec3f getPosition();
    
    public abstract void sendMessage(Component message);
    
    
    public ServerPlayer asPlayer(){
        return ((CommandSourcePlayer) this).getPlayer();
    }
    
}
