package jpize.tests.mcose.server.command.vanilla;

import jpize.tests.mcose.main.text.Component;
import jpize.tests.mcose.main.command.CommandContext;
import jpize.tests.mcose.server.command.CommandDispatcher;
import jpize.tests.mcose.main.command.builder.Commands;
import jpize.tests.mcose.server.player.ServerPlayer;

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