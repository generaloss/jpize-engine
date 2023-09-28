package jpize.tests.mcose.main.net;

import jpize.math.Maths;
import jpize.util.StringUtils;

public class PlayerProfile{
    
    private final String username;
    
    public PlayerProfile(String username){
        this.username = username;
    }
    
    
    public String getUsername(){
        return username;
    }
    
    public static boolean isNameInvalid(String name){
        return StringUtils.isBlank(name) || name.length() > 16 || name.length() < 3 || name.contains(" ");
    }
    
    public static String genFunnyName(){
        final String[] funnyNames = {"Makcum", "Kriper", "IlyaPro", "ViktorPlay", "Kirbo", "IbremMiner", "intbyte", "ABelevka", "Dmitry"};
        return funnyNames[Maths.random(0, funnyNames.length - 1)] + Maths.random(51, 99);
    }
    
}
