package pize.tests.voxelgame.server;

import java.util.StringJoiner;

public class CommandUtils{
    
    public static String joinArgs(String[] args, int beginIndex){
        final StringJoiner joiner = new StringJoiner(" ");
        for(int i = beginIndex; i < args.length; i++)
            joiner.add(args[i]);
        
        return joiner.toString();
    }
    
}
