package glit.tests.terraria.world;

import glit.tests.terraria.entity.Entity;
import glit.tests.terraria.map.WorldMap;

import java.util.ArrayList;
import java.util.List;

import static glit.tests.terraria.tile.TileType.DIRT;

public class World{

    private final WorldMap wallMap, tileMap;
    private final List<Entity> entities;

    public World(int width, int height){
        wallMap = new WorldMap(width, height);
        tileMap = new WorldMap(width, height);
        for(int i = 0; i < tileMap.getTiles().length; i++)
            tileMap.getTiles()[i].setType(DIRT);

        entities = new ArrayList<>();
    }


    public WorldMap getWallMap(){
        return wallMap;
    }

    public WorldMap getTileMap(){
        return tileMap;
    }

    public List<Entity> getEntities(){
        return entities;
    }

}
