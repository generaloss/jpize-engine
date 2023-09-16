package jpize.tests.minecraftose.main.audio;

public enum Sound{

    // Random
    CLICK   ("random.click"),
    EXPLODE ("random.explode"),

    GLASS_1 ("random.glass.1", 0.75F),
    GLASS_2 ("random.glass.2", 0.75F),
    GLASS_3 ("random.glass.3", 0.75F),

    LEVEL_UP("random.levelup"),

    // Hit
    FALL_BIG  ("damage.fallbig"),
    FALL_SMALL("damage.fallsmall"),

    HIT_1("damage.hit.1"),
    HIT_2("damage.hit.2"),
    HIT_3("damage.hit.3"),

    // Dig
    DIG_GRASS_1("dig.grass.1", 0.75F),
    DIG_GRASS_2("dig.grass.2", 0.75F),
    DIG_GRASS_3("dig.grass.3", 0.75F),
    DIG_GRASS_4("dig.grass.4", 0.75F),

    DIG_STONE_1("dig.stone.1", 0.75F),
    DIG_STONE_2("dig.stone.2", 0.75F),
    DIG_STONE_3("dig.stone.3", 0.75F),
    DIG_STONE_4("dig.stone.4", 0.75F),

    DIG_WOOD_1("dig.wood.1", 0.75F),
    DIG_WOOD_2("dig.wood.2", 0.75F),
    DIG_WOOD_3("dig.wood.3", 0.75F),
    DIG_WOOD_4("dig.wood.4", 0.75F),

    DIG_SAND_1("dig.sand.1", 0.75F),
    DIG_SAND_2("dig.sand.2", 0.75F),
    DIG_SAND_3("dig.sand.3", 0.75F),
    DIG_SAND_4("dig.sand.4", 0.75F);


    private final String ID;
    private final float pitch;

    Sound(String ID, float pitch){
        this.ID = ID;
        this.pitch = pitch;
    }

    Sound(String ID){
        this(ID, 1F);
    }

    public String getID(){
        return ID;
    }

    public float getPitch(){
        return pitch;
    }

}
