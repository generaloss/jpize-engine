package pize.tests.voxelgame.main;

import pize.tests.voxelgame.SharedConstants;

public class Version{
    
    private final String name;
    private final int id;
    private final boolean stable;
    
    public Version(String name){
        this.name = name;
        id = name.hashCode();
        stable = SharedConstants.STABLE;
    }
    
    public Version(){
        this(SharedConstants.VERSION_NAME);
    }
    
    
    public String getName(){
        return name;
    }
    
    public int getID(){
        return id;
    }
    
    public boolean isStable(){
        return stable;
    }
    
}
