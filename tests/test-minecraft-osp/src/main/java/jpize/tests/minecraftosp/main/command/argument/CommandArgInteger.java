package jpize.tests.minecraftosp.main.command.argument;

import jpize.tests.minecraftosp.server.Server;
import jpize.tests.minecraftosp.main.command.source.CommandSource;

public class CommandArgInteger extends CommandArg{
    
    // Результат парсинга
    private int number;
    
    @Override
    public int parse(String remainingChars, CommandSource source, Server server){
        // Разделяем оставшуюся часть команды на части
        final String[] args = remainingChars.split(" ");
        
        // Если количество частей меньше 1 (число), завершить парсинг
        if(args.length < 1)
            return 0;
        
        // устанавливаем число
        number = Integer.parseInt(args[0]);
        return args[0].length();
    }
    
    public int getInt(){
        return number;
    }
    
}
