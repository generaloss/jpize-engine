package jpize.tests.minecraftosp.server.command.vanilla;

import jpize.tests.minecraftosp.main.command.CommandContext;
import jpize.tests.minecraftosp.main.command.builder.Commands;
import jpize.tests.minecraftosp.main.text.Component;
import jpize.tests.minecraftosp.main.text.TextColor;
import jpize.tests.minecraftosp.server.command.CommandDispatcher;
import jpize.tests.minecraftosp.server.player.ServerPlayer;

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
