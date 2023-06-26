package pize.tests.voxelgame.server.command.commands;

import pize.tests.voxelgame.base.net.packet.CBPacketDisconnect;
import pize.tests.voxelgame.server.command.CommandContext;
import pize.tests.voxelgame.server.command.CommandDispatcher;
import pize.tests.voxelgame.server.command.argument.CommandArg;
import pize.tests.voxelgame.server.command.builder.Commands;
import pize.tests.voxelgame.server.player.ServerPlayer;

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
        final ServerPlayer sender = context.getSource().asPlayer().getPlayer();
        final ServerPlayer targetPlayer = context.getArg(0).asPlayer().getPlayer();
        // Reason
        final String reason = context.getArg(1).asText().getText();
        // Kick
        targetPlayer.sendPacket(new CBPacketDisconnect("You been kicked by " + sender.getName() + ", reason: " + reason));
        context.getServer().getPlayerList().broadcastMessage(targetPlayer.getName() + " was kicked by " + sender.getName() + ", reason: " + reason);
    }
    
}
