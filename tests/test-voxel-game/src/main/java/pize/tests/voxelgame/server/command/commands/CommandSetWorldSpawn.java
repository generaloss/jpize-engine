package pize.tests.voxelgame.server.command.commands;

import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.server.command.CommandContext;
import pize.tests.voxelgame.server.command.CommandDispatcher;
import pize.tests.voxelgame.server.command.builder.Commands;
import pize.tests.voxelgame.server.level.ServerLevel;
import pize.tests.voxelgame.server.player.ServerPlayer;

public class CommandSetWorldSpawn{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("setworldspawn")
            .requiresPlayer()
            .executes( CommandSetWorldSpawn::setWorldSpawn)
        );
    }
    
    private static void setWorldSpawn(CommandContext context){
        // Player
        final ServerPlayer sender = context.getSource().asPlayer().getPlayer();
        // Spawn position
        final Vec3f position = sender.getPosition();
        final ServerLevel level = (ServerLevel) sender.getLevel();
        // Set world spawn
        level.getConfiguration().setWorldSpawn(position.x, position.z);
        context.getServer().getPlayerList().broadcastMessage("World spawn set in: " + position);
    }
    
}
