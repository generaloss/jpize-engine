package pize.tests.minecraftosp.server.command.vanilla;

import pize.tests.minecraftosp.main.net.packet.CBPacketDisconnect;
import pize.tests.minecraftosp.main.text.Component;
import pize.tests.minecraftosp.main.text.TextColor;
import pize.tests.minecraftosp.main.command.CommandContext;
import pize.tests.minecraftosp.server.command.CommandDispatcher;
import pize.tests.minecraftosp.main.command.argument.CommandArg;
import pize.tests.minecraftosp.main.command.builder.Commands;
import pize.tests.minecraftosp.server.player.ServerPlayer;

public class CommandKick{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("kick")
            .then(Commands.argument("player", CommandArg.player())
                .then(Commands.argument("reason", CommandArg.text())
                    .requiresPlayer()
                    .executes( CommandKick::kick )
                )
            )
        );
    }
    
    private static void kick(CommandContext context){
        // Players
        final ServerPlayer sender = context.getSource().asPlayer();
        final ServerPlayer targetPlayer = context.getArg(0).asPlayer().getPlayer();
        // Reason
        final String reason = context.getArg(1).asText().getText();
        // Kick
        targetPlayer.sendPacket(new CBPacketDisconnect("You been kicked by " + sender.getName() + ", reason: " + reason));
        context.getServer().getPlayerList().broadcastServerMessage(new Component().color(TextColor.DARK_RED).text(targetPlayer.getName() + " was kicked by " + sender.getName() + ", reason: " + reason));
    }
    
}
