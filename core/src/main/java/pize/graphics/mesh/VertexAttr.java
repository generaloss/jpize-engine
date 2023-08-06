package pize.graphics.mesh;

import pize.graphics.gl.Type;

public class VertexAttr{

    private final int count;
    private final Type type;
    private final boolean normalize;


    public VertexAttr(int count, Type type, boolean normalize){
        this.type = type;
        this.count = count;
        this.normalize = normalize;
    }
    
    public VertexAttr(int count, Type type){
        this(count, type, false);
    }


    public int getCount(){
        return count;
    }

    public Type getType(){
        return type;
    }

    public boolean isNormalize(){
        return normalize;
    }

}
