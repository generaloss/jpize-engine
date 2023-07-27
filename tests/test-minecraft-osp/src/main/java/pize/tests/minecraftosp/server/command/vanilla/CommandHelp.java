package pize.tests.minecraftosp.server.command.vanilla;

import pize.tests.minecraftosp.main.text.Component;
import pize.tests.minecraftosp.main.command.CommandContext;
import pize.tests.minecraftosp.server.command.CommandDispatcher;
import pize.tests.minecraftosp.main.command.builder.Commands;
import pize.tests.minecraftosp.main.command.node.CommandNodeLiteral;
import pize.tests.minecraftosp.main.command.source.CommandSource;

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
