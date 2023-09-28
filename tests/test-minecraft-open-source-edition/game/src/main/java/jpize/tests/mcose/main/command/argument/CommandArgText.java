package jpize.tests.mcose.main.command.argument;

import jpize.tests.mcose.server.Server;
import jpize.tests.mcose.main.command.source.CommandSource;

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