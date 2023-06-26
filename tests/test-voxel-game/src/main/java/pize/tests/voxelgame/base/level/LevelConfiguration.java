package pize.tests.voxelgame.base.level;

public class LevelConfiguration{
    
    private String name;
    
    public void load(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
}
