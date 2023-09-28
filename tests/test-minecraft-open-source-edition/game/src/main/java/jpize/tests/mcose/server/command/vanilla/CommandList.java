package jpize.tests.mcose.server.command.vanilla;

import jpize.tests.mcose.main.text.Component;
import jpize.tests.mcose.main.text.TextColor;
import jpize.tests.mcose.main.command.CommandContext;
import jpize.tests.mcose.server.command.CommandDispatcher;
import jpize.tests.mcose.main.command.builder.Commands;
import jpize.tests.mcose.main.command.source.CommandSource;
import jpize.tests.mcose.server.player.ServerPlayer;

import java.util.Collection;
import java.util.StringJoiner;

public class CommandList{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("list")
            .executes(CommandList::sendList)
        );
    }
    
    private static void sendList(CommandContext context){
        final CommandSource source = context.getSource();
        
        final StringJoiner joiner = new StringJoiner(", ");
        
        final Collection<ServerPlayer> players = context.getServer().getPlayerList().getPlayers();
        for(ServerPlayer player: players)
            joiner.add(player.getName());
        
        source.sendMessage(new Component().color(TextColor.YELLOW).text("Players: ").reset().text(joiner.toString()));
    }
    
}
