package jpize.tests.minecraftosp.server.command.vanilla;

import jpize.math.vecmath.vector.Vec3f;
import jpize.tests.minecraftosp.client.block.Block;
import jpize.tests.minecraftosp.main.command.CommandContext;
import jpize.tests.minecraftosp.main.command.argument.CommandArg;
import jpize.tests.minecraftosp.main.command.builder.Commands;
import jpize.tests.minecraftosp.main.command.source.CommandSource;
import jpize.tests.minecraftosp.main.net.packet.CBPacketBlockUpdate;
import jpize.tests.minecraftosp.main.registry.Registry;
import jpize.tests.minecraftosp.main.text.Component;
import jpize.tests.minecraftosp.main.text.TextColor;
import jpize.tests.minecraftosp.server.command.CommandDispatcher;
import jpize.tests.minecraftosp.server.level.ServerLevel;

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
        final int x = position.xf();
        final int y = position.y();
        final int z = position.zf();

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
