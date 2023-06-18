package pize.tests.voxelgame.clientserver.net;

import pize.math.Maths;
import pize.util.StringUtils;

public class PlayerProfile{
    
    public static final String[] funnyNames = {"Makcum", "Kriper", "IlyaPro", "ViktorPlay", "Kirbo", "IbremMiner"};
    
    
    private final String name;
    
    public PlayerProfile(String name){
        this.name = name;
    }
    
    public PlayerProfile(){
        this(funnyNames[Maths.random(0, funnyNames.length - 1)] + Maths.random(51, 99));
    }
    
    
    public String getName(){
        return name;
    }
    
    public static boolean isNameValid(String name){
        return !StringUtils.isBlank(name) && name.length() <= 16;
    }
    
}
