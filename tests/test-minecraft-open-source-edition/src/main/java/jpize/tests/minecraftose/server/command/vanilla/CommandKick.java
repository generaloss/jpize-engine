package jpize.tests.minecraftose.server.command.vanilla;

import jpize.tests.minecraftose.main.net.packet.CBPacketDisconnect;
import jpize.tests.minecraftose.main.text.Component;
import jpize.tests.minecraftose.main.text.TextColor;
import jpize.tests.minecraftose.main.command.CommandContext;
import jpize.tests.minecraftose.server.command.CommandDispatcher;
import jpize.tests.minecraftose.main.command.argument.CommandArg;
import jpize.tests.minecraftose.main.command.builder.Commands;
import jpize.tests.minecraftose.server.player.ServerPlayer;

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
