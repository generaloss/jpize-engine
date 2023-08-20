package pize.lib.gl.vertex;

import pize.lib.gl.type.GlType;

public class GlVertexAttr{

    private final int count;
    private final GlType type;
    private final boolean normalize;


    public GlVertexAttr(int count, GlType type, boolean normalize){
        this.type = type;
        this.count = count;
        this.normalize = normalize;
    }
    
    public GlVertexAttr(int count, GlType type){
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
