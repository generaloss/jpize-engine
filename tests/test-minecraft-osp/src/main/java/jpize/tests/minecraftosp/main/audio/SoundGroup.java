package jpize.tests.minecraftosp.main.audio;

import jpize.math.Maths;

public enum SoundGroup{

    HIT(Sound.HIT_1, Sound.HIT_2, Sound.HIT_3),

    DIG_GRASS(Sound.DIG_GRASS_1, Sound.DIG_GRASS_2, Sound.DIG_GRASS_3, Sound.DIG_GRASS_4),
    DIG_STONE(Sound.DIG_STONE_1, Sound.DIG_STONE_2, Sound.DIG_STONE_3, Sound.DIG_STONE_4),
    DIG_WOOD (Sound.DIG_WOOD_1,  Sound.DIG_WOOD_2,  Sound.DIG_WOOD_3,  Sound.DIG_WOOD_4 ),
    DIG_SAND (Sound.DIG_SAND_1,  Sound.DIG_SAND_2,  Sound.DIG_SAND_3,  Sound.DIG_SAND_4 ),
    GLASS    (Sound.GLASS_1,     Sound.GLASS_2,     Sound.GLASS_3);

    private final Sound[] sounds;

    SoundGroup(Sound... sounds){
        this.sounds = sounds;
    }

    public Sound[] getSounds(){
        return sounds;
    }

    public Sound random(){
        return sounds[Maths.random(sounds.length - 1)];
    }

}
