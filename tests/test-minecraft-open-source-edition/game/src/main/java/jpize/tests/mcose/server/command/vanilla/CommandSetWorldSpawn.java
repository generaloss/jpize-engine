package jpize.tests.mcose.server.command.vanilla;

import jpize.math.vecmath.vector.Vec3f;
import jpize.tests.mcose.main.text.Component;
import jpize.tests.mcose.main.text.TextColor;
import jpize.tests.mcose.main.command.CommandContext;
import jpize.tests.mcose.server.command.CommandDispatcher;
import jpize.tests.mcose.main.command.builder.Commands;
import jpize.tests.mcose.server.level.ServerLevel;
import jpize.tests.mcose.server.player.ServerPlayer;

public class CommandSetWorldSpawn{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("setworldspawn")
            .requiresPlayer()
            .executes( CommandSetWorldSpawn::setWorldSpawn)
        );
    }
    
    private static void setWorldSpawn(CommandContext context){
        // Player
        final ServerPlayer sender = context.getSource().asPlayer();
        // Spawn position
        final Vec3f position = sender.getPosition();
        final ServerLevel level = sender.getLevel();
        // Set world spawn
        level.getConfiguration().setWorldSpawn(position.x, position.z);
        context.getServer().getPlayerList().broadcastServerMessage(new Component().color(TextColor.GREEN).text("World spawn set in: " + position));
    }
    
}
