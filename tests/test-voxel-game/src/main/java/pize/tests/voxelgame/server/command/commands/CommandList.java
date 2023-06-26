package pize.tests.voxelgame.server.command.commands;

import pize.tests.voxelgame.server.command.CommandContext;
import pize.tests.voxelgame.server.command.CommandDispatcher;
import pize.tests.voxelgame.server.command.builder.Commands;
import pize.tests.voxelgame.server.command.source.CommandSource;
import pize.tests.voxelgame.server.player.ServerPlayer;

import java.util.Collection;
import java.util.StringJoiner;

public class CommandList{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("list")
            .executes( CommandList::sendList)
        );
    }
    
    private static void sendList(CommandContext context){
        final CommandSource source = context.getSource();
        
        final StringJoiner joiner = new StringJoiner(", ");
        
        final Collection<ServerPlayer> players = context.getServer().getPlayerList().getPlayers();
        for(ServerPlayer player: players)
            joiner.add(player.getName());
        
        source.sendMessage("Players: " + joiner);
    }
    
}
