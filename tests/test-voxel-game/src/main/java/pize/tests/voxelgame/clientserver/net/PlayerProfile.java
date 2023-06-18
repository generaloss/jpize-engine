package pize.tests.voxelgame.clientserver.net;

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
    
    public static boolean isNameValid(String name){
        return !StringUtils.isBlank(name) && name.length() <= 16;
    }
    
    public static String genFunnyName(){
        final String[] funnyNames = {"Makcum", "Kriper", "IlyaPro", "ViktorPlay", "Kirbo", "IbremMiner", "intbyte", "@a.belevka", "Dmitry"};
        return funnyNames[Maths.random(0, funnyNames.length - 1)] + Maths.random(51, 99);
    }
    
}
