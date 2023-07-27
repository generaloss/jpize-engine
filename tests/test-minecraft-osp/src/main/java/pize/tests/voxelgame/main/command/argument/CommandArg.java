package pize.tests.voxelgame.main.command.argument;

import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.main.command.source.CommandSource;

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
    
    public static CommandArgInteger numInt(){
        return new CommandArgInteger();
    }
    
    public static CommandArgLong numLong(){
        return new CommandArgLong();
    }
    
    public static CommandArgFloat numFloat(){
        return new CommandArgFloat();
    }
    
    public static CommandArgTime time(){
        return new CommandArgTime();
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
    
    public CommandArgInteger asNumInt(){
        return (CommandArgInteger) this;
    }
    
    public CommandArgLong asNumLong(){
        return (CommandArgLong) this;
    }
    
    public CommandArgFloat asNumFloat(){
        return (CommandArgFloat) this;
    }
    
    public CommandArgTime asTime(){
        return (CommandArgTime) this;
    }

}
