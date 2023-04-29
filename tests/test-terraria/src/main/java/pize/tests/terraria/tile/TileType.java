package pize.tests.terraria.tile;

public enum TileType{

    AIR(0, false),
    DIRT(1, true),
    STONE(2, true),
    GRASS(3, true);

    public final short id;
    public final boolean collidable;

    TileType(int id, boolean collidable){
        this.id = (short) id;
        this.collidable = collidable;
    }

}
