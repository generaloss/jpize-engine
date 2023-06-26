package pize.tests.voxelgame.server.command.source;

import pize.math.vecmath.tuple.Tuple3f;

public abstract class CommandSource{
    
    public abstract Tuple3f getPosition();
    
    public abstract void sendMessage(String message);
    
    
    public CommandSourcePlayer asPlayer(){
        return (CommandSourcePlayer) this;
    }
    
}
