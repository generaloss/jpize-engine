package jpize.tests.mcose.server.command.vanilla;

import jpize.math.vecmath.vector.Vec3f;
import jpize.tests.mcose.client.block.Block;
import jpize.tests.mcose.main.command.CommandContext;
import jpize.tests.mcose.main.command.argument.CommandArg;
import jpize.tests.mcose.main.command.builder.Commands;
import jpize.tests.mcose.main.command.source.CommandSource;
import jpize.tests.mcose.main.net.packet.clientbound.CBPacketBlockUpdate;
import jpize.tests.mcose.main.registry.Registry;
import jpize.tests.mcose.main.text.Component;
import jpize.tests.mcose.main.text.TextColor;
import jpize.tests.mcose.server.command.CommandDispatcher;
import jpize.tests.mcose.server.level.ServerLevel;

public class CommandSetBlock{

    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("setblock")
            .then(Commands.argument("position", CommandArg.position())
                .then(Commands.argument("blockID", CommandArg.numInt())
                    .executes(CommandSetBlock::setBlock)
                )
            )
        );
    }

    private static void setBlock(CommandContext context){
        // Args
        final Vec3f position = context.getArg(0).asPosition().getPosition();
        final int x = position.xFloor();
        final int y = position.yRound();
        final int z = position.zFloor();

        final byte blockID = (byte) context.getArg(1).asNumInt().getInt();
        final Block block = Registry.Block.get(blockID);
        if(block == null){
            context.getSource().sendMessage(new Component().color(TextColor.RED).text("Block ID is invalid."));
            return;
        }

        // Set block
        final CommandSource source = context.getSource();
        final ServerLevel level = source.getLevel();
        level.setBlock(x, y, z, block);

        context.getServer().getPlayerList().broadcastPacket(new CBPacketBlockUpdate(x, y, z, block.getDefaultData()));
        source.sendMessage(new Component().text("Block " + block.getID() + " set in: " + x + ", " + y + ", " + z));
    }

}