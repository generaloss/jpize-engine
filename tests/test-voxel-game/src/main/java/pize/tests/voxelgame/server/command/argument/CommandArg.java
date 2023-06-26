package pize.tests.voxelgame.server.command.argument;

import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.server.command.source.CommandSource;

public abstract class CommandArg{
    
    // Должно возвращать длину строки, которую удалось разобрать, если не удалось - 0
    public abstract int parse(String remainingChars, CommandSource source, Server server);
    
    
    public static CommandArgPosition position(){
        return new CommandArgPosition();
    }
    
    public static CommandArgPlayer player(){
        return new CommandArgPlayer();
    }
    
    public static CommandArgText text(){
        return new CommandArgText();
    }
    
    public static CommandArgWord word(){
        return new CommandArgWord();
    }
    
    
    public CommandArgPosition asPosition(){
        return (CommandArgPosition) this;
    }
    
    public CommandArgPlayer asPlayer(){
        return (CommandArgPlayer) this;
    }
    
    public CommandArgText asText(){
        return (CommandArgText) this;
    }
    
    public CommandArgWord asWord(){
        return (CommandArgWord) this;
    }

}
