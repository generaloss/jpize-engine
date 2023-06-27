package pize.tests.voxelgame.server.command.commands;

import pize.tests.voxelgame.base.text.Component;
import pize.tests.voxelgame.base.text.TextColor;
import pize.tests.voxelgame.server.command.CommandContext;
import pize.tests.voxelgame.server.command.CommandDispatcher;
import pize.tests.voxelgame.server.command.builder.Commands;
import pize.tests.voxelgame.server.level.ServerLevel;
import pize.tests.voxelgame.server.player.ServerPlayer;

public class CommandSeed{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("seed")
            .requiresPlayer()
            .executes( CommandSeed::sendSeed)
        );
    }
    
    private static void sendSeed(CommandContext context){
        // Player
        final ServerPlayer sender = context.getSource().asPlayer().getPlayer();
        sender.sendMessage(new Component().color(TextColor.GREEN).text("World seed: " + ((ServerLevel) sender.getLevel()).getConfiguration().getSeed()));
    }
    
}
