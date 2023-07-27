package pize.tests.voxelgame.main.lang;

public class LangInfo{
    
    private final String name, code, region;
    
    public LangInfo(String name, String code, String region){
        this.name = name;
        this.code = code;
        this.region = region;
    }
    
    public String getName(){
        return name;
    }
    
    public String getCode(){
        return code;
    }
    
    public String getRegion(){
        return region;
    }
    
}
