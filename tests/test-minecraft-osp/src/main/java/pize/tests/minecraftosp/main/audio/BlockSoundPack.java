package pize.tests.minecraftosp.main.audio;

import pize.tests.minecraftosp.main.block.BlockSetType;

public enum BlockSoundPack{

    GRASS(SoundGroup.DIG_GRASS, SoundGroup.DIG_GRASS, SoundGroup.DIG_GRASS),
    STONE(SoundGroup.DIG_STONE, SoundGroup.DIG_STONE, SoundGroup.DIG_STONE),
    WOOD (SoundGroup.DIG_WOOD , SoundGroup.DIG_WOOD , SoundGroup.DIG_WOOD ),
    SAND (SoundGroup.DIG_SAND , SoundGroup.DIG_SAND , SoundGroup.DIG_SAND ),
    GLASS(SoundGroup.DIG_STONE, SoundGroup.GLASS, SoundGroup.DIG_STONE);

    
    private final SoundGroup place, destroy, step;

    BlockSoundPack(SoundGroup place, SoundGroup destroy, SoundGroup step){
        this.place = place;
        this.destroy = destroy;
        this.step = step;
    }
    
    public SoundGroup getPlaceSounds(){
        return place;
    }
    
    public SoundGroup getDestroySounds(){
        return destroy;
    }
    
    public SoundGroup getStepSounds(){
        return step;
    }
    
    public Sound randomPlaceSound(){
        return place.random();
    }

    public Sound randomDestroySound(){
        return destroy.random();
    }

    public Sound randomStepSound(){
        return step.random();
    }

    public Sound randomSound(BlockSetType setType){
        return switch(setType){
            case PLACE, REPLACE -> randomPlaceSound();
            case DESTROY -> randomDestroySound();
        };
    }

}