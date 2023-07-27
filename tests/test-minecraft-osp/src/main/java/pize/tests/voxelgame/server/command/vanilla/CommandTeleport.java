package pize.tests.voxelgame.server.command.vanilla;

import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.main.text.Component;
import pize.tests.voxelgame.main.command.CommandContext;
import pize.tests.voxelgame.server.command.CommandDispatcher;
import pize.tests.voxelgame.main.command.argument.CommandArg;
import pize.tests.voxelgame.main.command.argument.CommandArgPlayer;
import pize.tests.voxelgame.main.command.argument.CommandArgPosition;
import pize.tests.voxelgame.main.command.builder.Commands;
import pize.tests.voxelgame.server.player.ServerPlayer;

public class CommandTeleport{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("tp")
            .then(Commands.argument("position", CommandArg.position())
                .requiresPlayer()
                .executes( CommandTeleport::teleportToPosition )
            )
            
            .then(Commands.argument("player", CommandArg.player())
                .requiresPlayer()
                .executes( CommandTeleport::teleportToPlayer )
                
                .then(Commands.argument("targetPlayer", CommandArg.player())
                    .executes( CommandTeleport::teleportPlayerToPlayer )
                )
                .then(Commands.argument("position", CommandArg.position())
                    .executes( CommandTeleport::teleportPlayerToPosition )
                )
            )
            
        );
    }
    
    private static void teleportToPosition(CommandContext context){
        // Arguments
        final CommandArgPosition argPosition = context.getArg(0).asPosition();
        
        // Sender, Position
        final ServerPlayer sender = context.getSource().asPlayer();
        final Vec3f position = argPosition.getPosition();
        // Teleport
        sender.teleport(position);
        sender.sendMessage(new Component().text("You teleported to " + position));
    }
    
    private static void teleportToPlayer(CommandContext context){
        // Arguments
        final CommandArgPlayer argPlayer = context.getArg(0).asPlayer();
        
        // Players
        final ServerPlayer sender = context.getSource().asPlayer();
        final ServerPlayer targetPlayer = argPlayer.getPlayer();
        // Teleport
        sender.teleport(targetPlayer);
        sender.sendMessage(new Component().text("You teleported to " + targetPlayer.getName()));
        targetPlayer.sendMessage(new Component().text("Player " + sender.getName() + " teleported to you"));
    }
    
    private static void teleportPlayerToPlayer(CommandContext context){
        // Arguments
        final CommandArgPlayer argPlayer = context.getArg(0).asPlayer();
        final CommandArgPlayer argTargetPlayer = context.getArg(1).asPlayer();
        
        // Players
        final ServerPlayer player = argPlayer.getPlayer();
        final ServerPlayer targetPlayer = argTargetPlayer.getPlayer();
        
        // Teleport
        player.teleport(targetPlayer);
        player.sendMessage(new Component().text("You teleported to " + targetPlayer.getName()));
        targetPlayer.sendMessage(new Component().text("Player " + player.getName() + " teleported to you"));
    }
    
    private static void teleportPlayerToPosition(CommandContext context){
        // Arguments
        final CommandArgPlayer argPlayer = context.getArg(0).asPlayer();
        final CommandArgPosition argPosition = context.getArg(1).asPosition();
        
        // Player, Position
        final ServerPlayer player = argPlayer.getPlayer();
        final Vec3f position = argPosition.getPosition();
        // Teleport
        player.teleport(position);
        player.sendMessage(new Component().text("You teleported to " + position));
    }
    
}
