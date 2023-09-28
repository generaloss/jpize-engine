package jpize.tests.mcose.server.command.vanilla;

import jpize.tests.mcose.main.text.Component;
import jpize.tests.mcose.main.command.CommandContext;
import jpize.tests.mcose.server.command.CommandDispatcher;
import jpize.tests.mcose.main.command.builder.Commands;
import jpize.tests.mcose.main.command.node.CommandNodeLiteral;
import jpize.tests.mcose.main.command.source.CommandSource;

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
