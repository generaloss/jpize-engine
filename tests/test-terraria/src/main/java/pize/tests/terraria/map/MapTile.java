package pize.tests.terraria.map;

import pize.tests.terraria.tile.TileType;

public class MapTile{

    private TileType type;
    private byte light;

    public MapTile(TileType type, int light){
        setType(type);
        setLight(light);
    }


    public byte getLight(){
        return light;
    }

    public void setLight(int light){
        this.light = (byte) light;
    }


    public TileType getType(){
        return type;
    }

    public void setType(TileType type){
        this.type = type;
    }

}
