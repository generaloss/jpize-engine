package jpize.gl.vertex;

import jpize.gl.type.GlType;

public class GlVertAttr{

    private final int count;
    private final GlType type;
    private final boolean normalize;


    public GlVertAttr(int count, GlType type, boolean normalize){
        this.type = type;
        this.count = count;
        this.normalize = normalize;
    }
    
    public GlVertAttr(int count, GlType type){
        this(count, type, false);
    }


    public int getCount(){
        return count;
    }

    public GlType getType(){
        return type;
    }

    public boolean isNormalize(){
        return normalize;
    }

}
