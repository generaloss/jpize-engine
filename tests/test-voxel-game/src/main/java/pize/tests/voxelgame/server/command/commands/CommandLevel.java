package pize.tests.voxelgame.server.command.commands;

import pize.tests.voxelgame.base.text.Component;
import pize.tests.voxelgame.base.text.TextColor;
import pize.tests.voxelgame.server.chunk.gen.ChunkGenerator;
import pize.tests.voxelgame.server.command.CommandContext;
import pize.tests.voxelgame.server.command.CommandDispatcher;
import pize.tests.voxelgame.server.command.argument.CommandArg;
import pize.tests.voxelgame.server.command.builder.Commands;
import pize.tests.voxelgame.server.level.LevelManager;
import pize.tests.voxelgame.server.level.ServerLevel;
import pize.tests.voxelgame.server.player.ServerPlayer;

public class CommandLevel{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("level")
            
            .then(Commands.literal("create")
                .then(Commands.argument("levelName", CommandArg.word())
                    .then(Commands.argument("seed", CommandArg.word())
                        .then(Commands.argument("generatorType", CommandArg.word())
                            .executes( CommandLevel::createLevel )
                        )
                    )
                )
            )
            
            .then(Commands.literal("goto")
                .then(Commands.argument("levelName", CommandArg.word())
                    .requiresPlayer()
                    .executes( CommandLevel::goToLevel )
                )
            )
            
            
        );
    }
    
    private static void createLevel(CommandContext context){
        // Level name, seed, generator Type
        final String levelName = context.getArg(0).asWord().getWord();
        final String seedLiteral = context.getArg(1).asWord().getWord();
        final int seed = seedLiteral.hashCode();
        final String generatorName = context.getArg(2).asWord().getWord();
        final ChunkGenerator generator = ChunkGenerator.fromString(generatorName);
        // Create level
        final ServerPlayer sender = context.getSource().asPlayer().getPlayer();
        final LevelManager levelManager = context.getServer().getLevelManager();
        
        if(levelManager.isLevelLoaded(levelName))
            sender.sendMessage(new Component().color(TextColor.DARK_RED).text("Level " + levelName + " already loaded"));
        else{
            levelManager.createLevel(levelName, seed, generator);
            context.getServer().getPlayerList().broadcastMessage(new Component().color(TextColor.YELLOW).text("Level '" + levelName + "' loaded"));
        }
    }
    
    public static void goToLevel(CommandContext context){
        // Level name
        final String levelName = context.getArg(0).asWord().getWord();
        // Go to level
        final ServerPlayer sender = context.getSource().asPlayer().getPlayer();
        final LevelManager levelManager = context.getServer().getLevelManager();
        
        if(!levelManager.isLevelLoaded(levelName))
            sender.sendMessage(new Component().color(TextColor.DARK_RED).text("Level " + levelName + " is not loaded"));
        else{
            final ServerLevel level = levelManager.getLevel(levelName);
            sender.teleport(level, level.getSpawnPosition());
            sender.sendMessage(new Component().text("You teleported to level " + levelName));
        }
    }
    
    
}
