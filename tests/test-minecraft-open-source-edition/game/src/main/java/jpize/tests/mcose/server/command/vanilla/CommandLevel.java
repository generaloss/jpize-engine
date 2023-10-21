package jpize.tests.mcose.server.command.vanilla;

import jpize.tests.mcose.main.audio.Sound;
import jpize.tests.mcose.main.text.Component;
import jpize.tests.mcose.main.text.TextColor;
import jpize.tests.mcose.server.gen.ChunkGenerator;
import jpize.tests.mcose.main.command.CommandContext;
import jpize.tests.mcose.server.gen.Generators;
import jpize.tests.mcose.server.command.CommandDispatcher;
import jpize.tests.mcose.main.command.argument.CommandArg;
import jpize.tests.mcose.main.command.builder.Commands;
import jpize.tests.mcose.server.level.LevelManager;
import jpize.tests.mcose.server.level.ServerLevel;
import jpize.tests.mcose.server.player.ServerPlayer;

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

            .then(Commands.literal("list")
                .executes( CommandLevel::sendLevelList )
            )

            .then(Commands.argument("levelName", CommandArg.word())
                    .requiresPlayer()
                    .executes( CommandLevel::goToLevel )
            )
            
        );
    }
    
    private static void createLevel(CommandContext context){
        // Level name, seed, generator Type
        final String levelName = context.getArg(0).asWord().getWord();
        final String seed = context.getArg(1).asWord().getWord();

        final String generatorID = context.getArg(2).asWord().getWord();
        final ChunkGenerator generator = Generators.fromID(generatorID);
        // Create level
        final ServerPlayer sender = context.getSource().asPlayer();
        final LevelManager levelManager = context.getServer().getLevelManager();
        
        if(levelManager.isLevelLoaded(levelName))
            sender.sendMessage(new Component().color(TextColor.DARK_RED).text("Level " + levelName + " already loaded"));
        else{
            // Parse seed

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