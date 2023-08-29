package jpize.tests.minecraftosp.main.level;

public class LevelConfiguration{
    
    protected String name;
    
    public void load(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
}
