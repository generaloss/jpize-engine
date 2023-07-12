package pize.tests.voxelgame.server.command.source;

import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.main.text.Component;

public abstract class CommandSource{
    
    public abstract Vec3f getPosition();
    
    public abstract void sendMessage(Component message);
    
    
    public CommandSourcePlayer asPlayer(){
        return (CommandSourcePlayer) this;
    }
    
}
