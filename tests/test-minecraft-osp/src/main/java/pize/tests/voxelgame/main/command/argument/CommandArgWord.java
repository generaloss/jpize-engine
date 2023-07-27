package pize.tests.voxelgame.main.command.argument;

import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.main.command.source.CommandSource;

public class CommandArgWord extends CommandArg{
    
    // Результат парсинга
    private String word;
    
    @Override
    public int parse(String remainingChars, CommandSource source, Server server){
        // Разделяем оставшуюся часть команды на части
        final String[] args = remainingChars.split(" ");
        
        // Если количество частей меньше 1 (слово), завершить парсинг
        if(args.length < 1)
            return 0;
        
        // устанавливаем слово
        word = args[0];
        return word.length();
    }
    
    public String getWord(){
        return word;
    }

}
