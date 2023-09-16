package jpize.tests.minecraftose.server.command.vanilla;

import jpize.tests.minecraftose.main.text.Component;
import jpize.tests.minecraftose.main.text.TextColor;
import jpize.tests.minecraftose.main.command.CommandContext;
import jpize.tests.minecraftose.server.command.CommandDispatcher;
import jpize.tests.minecraftose.main.command.builder.Commands;
import jpize.tests.minecraftose.main.command.source.CommandSource;
import jpize.tests.minecraftose.server.player.ServerPlayer;

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
