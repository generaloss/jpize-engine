package jpize.tests.mcose.server.command.vanilla;

import jpize.tests.mcose.main.text.Component;
import jpize.tests.mcose.main.text.TextColor;
import jpize.tests.mcose.main.command.CommandContext;
import jpize.tests.mcose.server.command.CommandDispatcher;
import jpize.tests.mcose.main.command.argument.CommandArg;
import jpize.tests.mcose.main.command.builder.Commands;
import jpize.tests.mcose.server.player.ServerPlayer;

public class CommandTell{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("tell")
            
            .then(Commands.argument("player", CommandArg.player())
                .then(Commands.argument("text", CommandArg.text())
                    .requiresPlayer()
                    .executes( CommandTell::tell)
                )
            )
            
        );
    }
    
    private static void tell(CommandContext context){
        // Players
        final ServerPlayer sender = context.getSource().asPlayer();
        final ServerPlayer targetPlayer = context.getArg(0).asPlayer().getPlayer();
        final String text = context.getArg(1).asText().getText();
        // Tell
        targetPlayer.sendMessage(new Component().color(TextColor.YELLOW).text("<" + sender.getName() + "> tells you: \"").reset().text(text).color(TextColor.YELLOW).text("\""));
    }
    
}