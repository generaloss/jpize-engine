package glit.tests.minecraft.client.world.block;

public enum BlockFace{

    NORTH( 0, 0,-1),
    EAST ( 1, 0, 0),
    SOUTH( 0, 0, 1),
    WEST (-1, 0, 0),
    UP   ( 0, 1, 0),
    DOWN ( 0,-1, 0),
    SELF ( 0, 0, 0);


    private final int modX, modY, modZ;

    BlockFace(int modX,int modY,int modZ){
        this.modX = modX;
        this.modY = modY;
        this.modZ = modZ;
    }


    public int getModX(){
        return modX;
    }

    public int getModY(){
        return modY;
    }

    public int getModZ(){
        return modZ;
    }

}
