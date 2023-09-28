package jpize.tests.mcose.server.command.vanilla;

import jpize.tests.mcose.main.command.CommandContext;
import jpize.tests.mcose.main.command.builder.Commands;
import jpize.tests.mcose.main.text.Component;
import jpize.tests.mcose.main.text.TextColor;
import jpize.tests.mcose.server.command.CommandDispatcher;
import jpize.tests.mcose.server.player.ServerPlayer;

public class CommandSeed{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("seed")
            .requiresPlayer()
            .executes( CommandSeed::sendSeed)
        );
    }
    
    private static void sendSeed(CommandContext context){
        // Player
        final ServerPlayer sender = context.getSource().asPlayer();
        sender.sendMessage(new Component().color(TextColor.GREEN).text("World seed: " + sender.getLevel().getConfiguration().getSeed()));
    }
    
}
