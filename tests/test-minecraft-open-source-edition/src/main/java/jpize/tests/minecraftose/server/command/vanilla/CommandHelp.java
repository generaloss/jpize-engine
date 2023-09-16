package jpize.tests.minecraftose.server.command.vanilla;

import jpize.tests.minecraftose.main.text.Component;
import jpize.tests.minecraftose.main.command.CommandContext;
import jpize.tests.minecraftose.server.command.CommandDispatcher;
import jpize.tests.minecraftose.main.command.builder.Commands;
import jpize.tests.minecraftose.main.command.node.CommandNodeLiteral;
import jpize.tests.minecraftose.main.command.source.CommandSource;

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
