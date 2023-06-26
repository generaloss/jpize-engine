package pize.tests.voxelgame.server.command.commands;

import pize.tests.voxelgame.server.command.CommandContext;
import pize.tests.voxelgame.server.command.CommandDispatcher;
import pize.tests.voxelgame.server.command.builder.Commands;
import pize.tests.voxelgame.server.command.node.CommandNodeLiteral;
import pize.tests.voxelgame.server.command.source.CommandSource;

public class CommandHelp{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("help")
            .executes( CommandHelp::sendHelp )
        );
    }
    
    private static void sendHelp(CommandContext context){
        final CommandSource source = context.getSource();
        
        for(CommandNodeLiteral command: context.getServer().getCommandDispatcher().getCommands()){
            source.sendMessage("/" + command.getLiteral());
        }
        
        // source.sendMessage("/help          - that command");
        // source.sendMessage("/spawn         - teleport to spawn");
        // source.sendMessage("/tp            - teleport");
        // source.sendMessage("/list          - players list");
        // source.sendMessage("/kick          - kick player [/kick PlayerName Reason]");
        // source.sendMessage("/tell          - tell");
        // source.sendMessage("/setworldspawn - set spawn in the world");
        // source.sendMessage("/ban           - ban player");
        // source.sendMessage("/seed          - get world seed");
        // source.sendMessage("/level         - level op [level create/goto NAME SEED GENERATOR]");
        // source.sendMessage("/push          - add velocity [/vel PlayerName X Y Z]");
    }
    
}
