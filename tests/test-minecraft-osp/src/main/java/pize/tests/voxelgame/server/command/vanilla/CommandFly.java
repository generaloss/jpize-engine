package pize.tests.voxelgame.server.command.vanilla;

import pize.tests.voxelgame.main.text.Component;
import pize.tests.voxelgame.main.command.CommandContext;
import pize.tests.voxelgame.server.command.CommandDispatcher;
import pize.tests.voxelgame.main.command.builder.Commands;
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
        final ServerPlayer sender = context.getSource().asPlayer();
        // Fly
        if(sender.isFlyEnabled()){
            sender.setFlyEnabled(false);
            sender.sendMessage(new Component().text("Fly disabled"));
        }else{
            sender.setFlyEnabled(true);
            sender.sendMessage(new Component().text("Fly enabled"));
        }
    }
    
}
