package pize.tests.terraria.map;

import static pize.tests.terraria.tile.TileType.AIR;

public class WorldMap{

    private final int width, height;
    private final MapTile[] tiles;

    public WorldMap(int width, int height){
        this.width = width;
        this.height = height;
        tiles = new MapTile[width * height];

        for(int i = 0; i < tiles.length; i++)
            tiles[i] = new MapTile(AIR, 15);
    }


    public MapTile getTile(int x, int y){
        if(isOutOfBounds(x, y))
            return null;

        return tiles[getIndex(x, y)];
    }


    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }


    public MapTile[] getTiles(){
        return tiles;
    }

    public int getIndex(int x, int y){
        return y * width + x;
    }

    public boolean isOutOfBounds(int x, int y){
        return x >= width || y >= height || x < 0 || y < 0;
    }

}