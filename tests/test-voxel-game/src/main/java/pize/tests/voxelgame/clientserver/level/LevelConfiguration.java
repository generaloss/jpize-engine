package pize.tests.voxelgame.clientserver.level;

public class LevelConfiguration{
    
    private String name;
    
    public void load(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
}
