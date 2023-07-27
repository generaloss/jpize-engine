package pize.tests.voxelgame.server.command.vanilla;

import pize.tests.voxelgame.main.audio.Sound;
import pize.tests.voxelgame.main.text.Component;
import pize.tests.voxelgame.main.text.TextColor;
import pize.tests.voxelgame.server.chunk.gen.ChunkGenerator;
import pize.tests.voxelgame.main.command.CommandContext;
import pize.tests.voxelgame.server.chunk.gen.Generators;
import pize.tests.voxelgame.server.command.CommandDispatcher;
import pize.tests.voxelgame.main.command.argument.CommandArg;
import pize.tests.voxelgame.main.command.builder.Commands;
import pize.tests.voxelgame.server.level.LevelManager;
import pize.tests.voxelgame.server.level.ServerLevel;
import pize.tests.voxelgame.server.player.ServerPlayer;

import java.util.Collection;
import java.util.StringJoiner;

public class CommandLevel{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("level")
            
            .then(Commands.literal("create")
                .then(Commands.argument("levelName", CommandArg.word())
                    .then(Commands.argument("seed", CommandArg.word())
                        .then(Commands.argument("generatorID", CommandArg.word())
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
            
            .then(Commands.literal("list")
                .executes( CommandLevel::sendLevelList )
            )
            
        );
    }
    
    private static void createLevel(CommandContext context){
        // Level name, seed, generator Type
        final String levelName = context.getArg(0).asWord().getWord();
        final String seedLiteral = context.getArg(1).asWord().getWord();

        final String generatorID = context.getArg(2).asWord().getWord();
        final ChunkGenerator generator = Generators.fromID(generatorID);
        // Create level
        final ServerPlayer sender = context.getSource().asPlayer();
        final LevelManager levelManager = context.getServer().getLevelManager();
        
        if(levelManager.isLevelLoaded(levelName))
            sender.sendMessage(new Component().color(TextColor.DARK_RED).text("Level " + levelName + " already loaded"));
        else{
            // Parse seed
            int seed = seedLiteral.hashCode();
            try{
                seed = Integer.parseInt(seedLiteral);
            }catch(Exception ignored){ }

            levelManager.createLevel(levelName, seed, generator);
            context.getServer().getPlayerList().broadcastServerMessage(new Component().color(TextColor.YELLOW).text("Level '" + levelName + "' loaded"));
        }
    }
    
    public static void goToLevel(CommandContext context){
        // Level name
        final String levelName = context.getArg(0).asWord().getWord();
        // Go to level
        final ServerPlayer sender = context.getSource().asPlayer();
        final LevelManager levelManager = context.getServer().getLevelManager();
        
        if(!levelManager.isLevelLoaded(levelName))
            sender.sendMessage(new Component().color(TextColor.DARK_RED).text("Level " + levelName + " is not loaded"));
        else{
            final ServerLevel level = levelManager.getLevel(levelName);
            sender.teleport(level, level.getSpawnPosition());
            sender.sendMessage(new Component().text("You teleported to level " + levelName));
            sender.playSound(Sound.LEVEL_UP, 1, 1);
        }
    }
    
    private static void sendLevelList(CommandContext context){
        // Levels
        final LevelManager levelManager = context.getServer().getLevelManager();
        final Collection<ServerLevel> levels = levelManager.getLoadedLevels();
        
        // Create list
        final StringJoiner joiner = new StringJoiner(", ");
        for(ServerLevel level: levels)
            joiner.add(level.getConfiguration().getName());
        
        // Send levels
        context.getSource().sendMessage(new Component().color(TextColor.YELLOW).text("Levels: ").reset().text(joiner.toString()));
    }
    
    
}
