package pize.tests.minecraftosp.server.command.vanilla;

import pize.tests.minecraftosp.main.text.Component;
import pize.tests.minecraftosp.main.text.TextColor;
import pize.tests.minecraftosp.main.command.CommandContext;
import pize.tests.minecraftosp.server.command.CommandDispatcher;
import pize.tests.minecraftosp.main.command.builder.Commands;
import pize.tests.minecraftosp.server.level.ServerLevel;
import pize.tests.minecraftosp.server.player.ServerPlayer;

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
        sender.sendMessage(new Component().color(TextColor.GREEN).text("World seed: " + ((ServerLevel) sender.getLevel()).getConfiguration().getSeed()));
    }
    
}
