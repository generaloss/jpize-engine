package jpize.tests.minecraftosp.server.command.vanilla;

import jpize.tests.minecraftosp.main.text.Component;
import jpize.tests.minecraftosp.main.command.CommandContext;
import jpize.tests.minecraftosp.server.command.CommandDispatcher;
import jpize.tests.minecraftosp.main.command.builder.Commands;
import jpize.tests.minecraftosp.main.command.node.CommandNodeLiteral;
import jpize.tests.minecraftosp.main.command.source.CommandSource;

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
