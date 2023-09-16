package jpize.tests.minecraftose.main.command.source;

import jpize.math.vecmath.vector.Vec3f;
import jpize.tests.minecraftose.main.text.Component;
import jpize.tests.minecraftose.server.level.ServerLevel;
import jpize.tests.minecraftose.server.player.ServerPlayer;

public abstract class CommandSource{
    
    public abstract Vec3f getPosition();

    public abstract ServerLevel getLevel();

    public abstract void sendMessage(Component message);
    
    
    public ServerPlayer asPlayer(){
        return ((CommandSourcePlayer) this).getPlayer();
    }
    
}
