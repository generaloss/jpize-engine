package pize.tests.voxelgame.main.net;

import pize.math.Maths;
import pize.util.StringUtils;

public class PlayerProfile{
    
    private final String name;
    
    public PlayerProfile(String name){
        this.name = name;
    }
    
    
    public String getName(){
        return name;
    }
    
    public static boolean isNameInvalid(String name){
        return StringUtils.isBlank(name) || name.length() > 16 || name.length() < 3 || name.contains(" ");
    }
    
    public static String genFunnyName(){
        final String[] funnyNames = {"Makcum", "Kriper", "IlyaPro", "ViktorPlay", "Kirbo", "IbremMiner", "intbyte", "ABelevka", "Dmitry"};
        return funnyNames[Maths.random(0, funnyNames.length - 1)] + Maths.random(51, 99);
    }
    
}
