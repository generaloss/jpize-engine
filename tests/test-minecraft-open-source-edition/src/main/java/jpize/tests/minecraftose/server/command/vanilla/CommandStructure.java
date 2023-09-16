package jpize.tests.minecraftose.server.command.vanilla;

import jpize.files.Resource;
import jpize.math.vecmath.vector.Vec3f;
import jpize.math.vecmath.vector.Vec3i;
import jpize.tests.minecraftose.main.command.CommandContext;
import jpize.tests.minecraftose.main.command.argument.CommandArg;
import jpize.tests.minecraftose.main.command.builder.Commands;
import jpize.tests.minecraftose.main.net.packet.CBPacketBlockUpdate;
import jpize.tests.minecraftose.main.text.Component;
import jpize.tests.minecraftose.main.text.TextColor;
import jpize.tests.minecraftose.server.command.CommandDispatcher;
import jpize.tests.minecraftose.server.level.ServerLevel;
import jpize.tests.minecraftose.server.player.PlayerList;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class CommandStructure{

    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("struct")
            .then(Commands.literal("save")
                .then(Commands.argument("begin", CommandArg.position())
                    .then(Commands.argument("end", CommandArg.position())
                        .then(Commands.argument("name", CommandArg.word())
                            .executes(CommandStructure::save)
                        )
                    )
                )
            )
            .then(Commands.literal("load")
                .then(Commands.argument("name", CommandArg.word())
                    .executes(CommandStructure::load)
                )
            )
            .then(Commands.literal("palette")
                .executes(CommandStructure::palette)
            )
        );
    }

    private static void palette(CommandContext context){
        final ServerLevel level = context.getSource().getLevel();
        final PlayerList playerList = context.getServer().getPlayerList();

        final Vec3f position = context.getSource().getPosition();
        final Vec3i start = new Vec3i(position.xFloor(), position.yRound(), position.zFloor());

        short blockID = 1;
        mainCycle: for(int i = 0; i < 6; i++)
            for(int j = 0; j < 6; j++){
                level.setBlockState(i + start.x, start.y, j + start.z, blockID);
                playerList.broadcastPacket(new CBPacketBlockUpdate(i + start.x, start.y, j + start.z, blockID));
                blockID++;
                if(blockID == 29)
                    break mainCycle;
            }
    }

    private static void save(CommandContext context){
        final ServerLevel level = context.getSource().getLevel();

        // Dimensions
        final Vec3i begin = context.getArg(0).asPosition().getPosition().floor().castInt();
        final Vec3i end = context.getArg(1).asPosition().getPosition().floor().castInt();
        final String name = context.getArg(2).asWord().getWord();

        final Vec3i start = begin.min(end);
        final Vec3i size = begin.copy().sub(end).abs();

        // File
        try{
            final Resource file = new Resource(name + ".struct", true);
            final JpizeOutputStream outStream = file.getJpizeOut();

            // Write
            outStream.writeVec3i(size);

            for(int x = 0; x < size.x; x++)
                for(int y = 0; y < size.y; y++)
                    for(int z = 0; z < size.z; z++)
                        outStream.writeShort(level.getBlockState(x + start.x, y + start.y, z + start.z));

            outStream.flush();

        }catch(IOException e){
            e.printStackTrace();
        }

        context.getSource().sendMessage(new Component().text("Structure saved as ").color(TextColor.YELLOW).text(name + ".struct"));
    }

    private static void load(CommandContext context){
        final ServerLevel level = context.getSource().getLevel();
        final PlayerList playerList = context.getServer().getPlayerList();

        final String name = context.getArg(0).asWord().getWord();

        final Vec3f position = context.getSource().getPosition();
        final Vec3i start = new Vec3i(position.xFloor(), position.yRound(), position.zFloor());

        // File
        try{
            final Resource file = new Resource(name + ".struct", true);
            final JpizeInputStream inStream = file.getJpizeIn();

            // Read
            final Vec3i size = inStream.readVec3i().add(1);

            for(int x = 0; x < size.x; x++)
                for(int y = 0; y < size.y; y++)
                    for(int z = 0; z < size.z; z++){
                        final short block = inStream.readShort();
                        level.setBlockState(x + start.x, y + start.y, z + start.z, block);
                        playerList.broadcastPacket(new CBPacketBlockUpdate(x + start.x, y + start.y, z + start.z, block));
                    }

        }catch(IOException e){
            e.printStackTrace();
        }

        context.getSource().sendMessage(new Component().text("Structure ").color(TextColor.YELLOW).text(name + ".struct").reset().text(" loaded"));
    }

}
