package megalul.projectvostok.client.block.model;

public enum BlockFace{
    
    NEGATIVE_X(-1,  0,  0),
    POSITIVE_X( 1,  0,  0),
    NEGATIVE_Y( 0, -1,  0),
    POSITIVE_Y( 0,  1,  0),
    NEGATIVE_Z( 0,  0, -1),
    POSITIVE_Z( 0,  0,  1);
    
    
    public final int x, y, z;
    
    BlockFace(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    
    public static BlockFace fromNormal(int x, int y, int z){
        if(y == 1)
            return POSITIVE_Y;
        
        else if(x == 1)
            return POSITIVE_X;
        else if(z == 1)
            return POSITIVE_Z;
        else if(x == -1)
            return NEGATIVE_X;
        else if(z == -1)
            return NEGATIVE_Z;
        
        else
            return NEGATIVE_Y;
    }
    
}
