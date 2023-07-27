package pize.tests.voxelgame.main.command.argument;

import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.main.command.source.CommandSource;

public class CommandArgText extends CommandArg{
    
    // Результат парсинга
    private String text;
    
    @Override
    public int parse(String remainingChars, CommandSource source, Server server){
        text = remainingChars;
        return remainingChars.length();
    }
    
    public String getText(){
        return text;
    }
    
}