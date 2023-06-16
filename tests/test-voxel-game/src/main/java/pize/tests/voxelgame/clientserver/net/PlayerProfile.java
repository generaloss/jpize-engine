package pize.tests.voxelgame.clientserver.net;

import pize.math.Maths;
import pize.util.StringUtils;

public class PlayerProfile{
    
    private final String name;
    
    public PlayerProfile(String name){
        this.name = name;
    }
    
    public PlayerProfile(){
        this("Makcum-20" + Maths.randomSeed(2));
    }
    
    
    public String getName(){
        return name;
    }
    
    public static boolean isNameValid(String name){
        return !StringUtils.isBlank(name);
    }
    
}
