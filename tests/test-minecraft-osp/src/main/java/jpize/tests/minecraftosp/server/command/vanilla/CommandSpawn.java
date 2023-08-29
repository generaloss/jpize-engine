package jpize.tests.minecraftosp.server.command.vanilla;

import jpize.math.vecmath.vector.Vec3f;
import jpize.tests.minecraftosp.main.text.Component;
import jpize.tests.minecraftosp.main.command.CommandContext;
import jpize.tests.minecraftosp.server.command.CommandDispatcher;
import jpize.tests.minecraftosp.main.command.builder.Commands;
import jpize.tests.minecraftosp.server.level.ServerLevel;
import jpize.tests.minecraftosp.server.player.ServerPlayer;

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
