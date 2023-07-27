package pize.tests.voxelgame.server.command.vanilla;

import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.main.text.Component;
import pize.tests.voxelgame.main.command.CommandContext;
import pize.tests.voxelgame.server.command.CommandDispatcher;
import pize.tests.voxelgame.main.command.builder.Commands;
import pize.tests.voxelgame.server.level.ServerLevel;
import pize.tests.voxelgame.server.player.ServerPlayer;

public class CommandSpawn{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("spawn")
            .requiresPlayer()
            .executes( CommandSpawn::teleportToSpawn )
        );
    }
    
    private static void teleportToSpawn(CommandContext context){
        // Player
        final ServerPlayer sender = context.getSource().asPlayer();
        // Spawn position
        final ServerLevel level = (ServerLevel) sender.getLevel();
        final Vec3f spawnPosition = level.getSpawnPosition();
        // Teleport
        sender.teleport(spawnPosition);
        sender.sendMessage(new Component().text("You teleported to spawn"));
    }
    
}
