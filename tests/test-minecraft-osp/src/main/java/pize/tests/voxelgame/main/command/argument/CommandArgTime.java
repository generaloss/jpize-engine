package pize.tests.voxelgame.main.command.argument;

import pize.tests.voxelgame.main.time.TimeUnit;
import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.main.command.source.CommandSource;

public class CommandArgTime extends CommandArg{
    
    // Результат парсинга
    private TimeUnit unit;
    private float time;
    
    @Override
    public int parse(String remainingChars, CommandSource source, Server server){
        // Разделяем оставшуюся часть команды на части
        final String[] args = remainingChars.split(" ");
        
        // Если количество частей меньше 1 (время), завершить парсинг
        if(args.length < 1)
            return 0;
        
        String timeString = args[0];
        
        // Устанавливаем еденицу измерения
        unit = null;
        if(timeString.endsWith("d")) unit = TimeUnit.DAYS;
        if(timeString.endsWith("m")) unit = TimeUnit.MINUTES;
        if(timeString.endsWith("s")) unit = TimeUnit.SECONDS;
        if(timeString.endsWith("t")) unit = TimeUnit.TICKS;

        if(unit != null)
            timeString = timeString.substring(0, timeString.length() - 1);
        else
            unit = TimeUnit.TICKS; // Ticks - default
        
        // Устанавливаем время
        try{
            time = Float.parseFloat(timeString);
        }catch(Exception ignored){
            return 0;
        }
        return args[0].length();
    }
    
    public TimeUnit getUnit(){
        return unit;
    }
    
    public float getTime(){
        return time;
    }
    
}
