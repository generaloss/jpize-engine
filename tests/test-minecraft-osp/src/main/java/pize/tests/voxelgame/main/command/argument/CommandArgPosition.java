package pize.tests.voxelgame.main.command.argument;

import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.main.command.source.CommandSource;

public class CommandArgPosition extends CommandArg{
    
    // Результат парсинга
    private Vec3f position;
    
    @Override
    public int parse(String remainingChars, CommandSource source, Server server){
        // Разделяем оставшуюся часть команды на части
        final String[] args = remainingChars.split(" ");
        // Если количество частей меньше трех (X Y Z), завершить парсинг
        if(args.length < 3)
            return 0;
        
        try{
            int parsedLength = 2; // Кол-во пробелов между X Y Z
            
            // X
            float x = 0;
            if(args[0].startsWith("~")){ // Если координата относительная
                parsedLength++;
                if(args[0].length() != 1) // Если в координате есть не только '~', но и число
                    x = Float.parseFloat(args[0].substring(1));
                
                x += source.getPosition().x;
            }else
                x = Float.parseFloat(args[0]);
            
            // Y
            float y = 0;
            if(args[1].startsWith("~")){
                parsedLength++;
                if(args[1].length() != 1)
                    y = Float.parseFloat(args[1].substring(1));
                
                y += source.getPosition().y;
            }else
                y = Float.parseFloat(args[1]);
            
            // Z
            float z = 0;
            if(args[2].startsWith("~")){
                parsedLength++;
                if(args[2].length() != 1)
                    z = Float.parseFloat(args[2].substring(1));
                
                z += source.getPosition().z;
            }else
                z = Float.parseFloat(args[2]);
            
            // Если парсинг удался - устанавливаем позицию
            position = new Vec3f(x, y, z);
            // возвращаем количество символом, которые удалось преобразовать
            return args[0].length() + args[1].length() + args[2].length() + parsedLength;
        }catch(Exception e){
            return 0;
        }
    }
    
    public Vec3f getPosition(){
        return position;
    }
    
}
