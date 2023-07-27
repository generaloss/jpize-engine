package pize.tests.minecraftosp.server.command.vanilla;

import pize.math.vecmath.vector.Vec3f;
import pize.tests.minecraftosp.main.text.Component;
import pize.tests.minecraftosp.main.command.CommandContext;
import pize.tests.minecraftosp.server.command.CommandDispatcher;
import pize.tests.minecraftosp.main.command.builder.Commands;
import pize.tests.minecraftosp.server.level.ServerLevel;
import pize.tests.minecraftosp.server.player.ServerPlayer;

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
