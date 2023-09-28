package jpize.tests.mcose.main.command.argument;

import jpize.tests.mcose.server.Server;
import jpize.tests.mcose.main.command.source.CommandSource;

public class CommandArgFloat extends CommandArg{
    
    // Результат парсинга
    private float number;
    
    @Override
    public int parse(String remainingChars, CommandSource source, Server server){
        // Разделяем оставшуюся часть команды на части
        final String[] args = remainingChars.split(" ");
        
        // Если количество частей меньше 1 (число), завершить парсинг
        if(args.length < 1)
            return 0;
        
        // устанавливаем число
        number = Float.parseFloat(args[0]);
        return args[0].length();
    }
    
    public float getFloat(){
        return number;
    }
    
}
