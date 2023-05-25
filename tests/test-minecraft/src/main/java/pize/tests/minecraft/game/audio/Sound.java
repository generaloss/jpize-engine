package pize.tests.minecraft.game.audio;

import pize.tests.minecraft.game.options.SoundCategory;

public enum Sound{

    CLICK(SoundCategory.MASTER, "click", 0.25F);


    public final SoundCategory category;
    public final String resourceId;
    public final float maxVolume;

    Sound(SoundCategory category, String resourceId, float maxVolume){
        this.category = category;
        this.resourceId = resourceId;
        this.maxVolume = maxVolume;
    }

}
