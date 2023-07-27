package pize.tests.voxelgame.server.command.vanilla;

import pize.tests.voxelgame.main.text.Component;
import pize.tests.voxelgame.main.command.CommandContext;
import pize.tests.voxelgame.server.command.CommandDispatcher;
import pize.tests.voxelgame.main.command.builder.Commands;
import pize.tests.voxelgame.main.command.node.CommandNodeLiteral;
import pize.tests.voxelgame.main.command.source.CommandSource;

public class CommandHelp{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("help")
            .executes( CommandHelp::sendHelp )
        );
    }
    
    private static void sendHelp(CommandContext context){
        final CommandSource source = context.getSource();
        
        for(CommandNodeLiteral command: context.getServer().getCommandDispatcher().getCommands()){
            source.sendMessage(new Component().text("/" + command.getLiteral()));
        }
    }
    
}
