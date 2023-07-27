package pize.tests.voxelgame.main.audio;

import pize.tests.voxelgame.main.Identifier;

public enum Sound{

    CLICK   ("random.click"),
    EXPLODE ("random.explode"),
    GLASS   ("random.glass"),
    LEVEL_UP("random.levelup"),

    FALL_BIG  ("damage.fallbig"),
    FALL_SMALL("damage.fallsmall"),
    HIT_1("damage.hit.1"),
    HIT_2("damage.hit.2"),
    HIT_3("damage.hit.3"),

    DIG_GRASS_1("dig.grass.1"),
    DIG_GRASS_2("dig.grass.2"),
    DIG_GRASS_3("dig.grass.3"),
    DIG_GRASS_4("dig.grass.4"),

    DIG_STONE_1("dig.stone.1"),
    DIG_STONE_2("dig.stone.2"),
    DIG_STONE_3("dig.stone.3"),
    DIG_STONE_4("dig.stone.4"),

    DIG_WOOD_1("dig.wood.1"),
    DIG_WOOD_2("dig.wood.2"),
    DIG_WOOD_3("dig.wood.3"),
    DIG_WOOD_4("dig.wood.4"),

    DIG_SAND_1("dig.sand.1"),
    DIG_SAND_2("dig.sand.2"),
    DIG_SAND_3("dig.sand.3"),
    DIG_SAND_4("dig.sand.4");


    private final String ID;

    Sound(String ID){
        this.ID = Identifier.DEFAULT_NAMESPACE + ":" + ID;
    }

    public String getID(){
        return ID;
    }

}
