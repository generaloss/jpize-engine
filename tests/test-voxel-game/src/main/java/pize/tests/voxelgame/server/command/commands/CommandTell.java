package pize.tests.voxelgame.server.command.commands;

import pize.tests.voxelgame.base.text.Component;
import pize.tests.voxelgame.base.text.TextColor;
import pize.tests.voxelgame.server.command.CommandContext;
import pize.tests.voxelgame.server.command.CommandDispatcher;
import pize.tests.voxelgame.server.command.argument.CommandArg;
import pize.tests.voxelgame.server.command.builder.Commands;
import pize.tests.voxelgame.server.player.ServerPlayer;

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
        final ServerPlayer sender = context.getSource().asPlayer().getPlayer();
        final ServerPlayer targetPlayer = context.getArg(0).asPlayer().getPlayer();
        final String text = context.getArg(1).asText().getText();
        // Tell
        targetPlayer.sendMessage(new Component().color(TextColor.YELLOW).text("<" + sender.getName() + "> tells you: \"").reset().text(text).color(TextColor.YELLOW).text("\""));
    }
    
}
