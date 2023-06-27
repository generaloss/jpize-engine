package pize.tests.voxelgame.server.command.source;

import pize.math.vecmath.tuple.Tuple3f;
import pize.tests.voxelgame.base.text.Component;

public abstract class CommandSource{
    
    public abstract Tuple3f getPosition();
    
    public abstract void sendMessage(Component message);
    
    
    public CommandSourcePlayer asPlayer(){
        return (CommandSourcePlayer) this;
    }
    
}
