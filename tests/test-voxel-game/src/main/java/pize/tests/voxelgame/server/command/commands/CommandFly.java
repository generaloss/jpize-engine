package pize.tests.voxelgame.server.command.commands;

import pize.tests.voxelgame.server.command.CommandContext;
import pize.tests.voxelgame.server.command.CommandDispatcher;
import pize.tests.voxelgame.server.command.builder.Commands;
import pize.tests.voxelgame.server.player.ServerPlayer;

public class CommandFly{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("fly")
            .requiresPlayer()
            .executes( CommandFly::toggleFly )
        );
    }
    
    private static void toggleFly(CommandContext context){
        // Player
        final ServerPlayer sender = context.getSource().asPlayer().getPlayer();
        // Fly
        if(sender.isFlyEnabled()){
            sender.setFlyEnabled(false);
            sender.sendMessage("Fly disabled");
        }else{
            sender.setFlyEnabled(true);
            sender.sendMessage("Fly enabled");
        }
    }
    
}
