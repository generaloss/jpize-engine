package pize.tests.minecraft.game.lang;

public class LanguageInfo{

    private final String code;
    private final String name;
    private final String region;

    public LanguageInfo(String code, String name, String region){
        this.code = code;
        this.name = name;
        this.region = region;
    }

    public String getCode(){
        return this.code;
    }

    public String getName(){
        return this.name;
    }

    public String getRegion(){
        return this.region;
    }

}
