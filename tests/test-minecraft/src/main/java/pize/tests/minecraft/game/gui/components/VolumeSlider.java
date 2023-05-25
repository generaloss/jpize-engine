package pize.tests.minecraft.game.gui.components;

import pize.math.Maths;
import pize.tests.minecraft.game.Session;
import pize.tests.minecraft.game.gui.text.Component;
import pize.tests.minecraft.game.options.SoundCategory;

public class VolumeSlider extends Slider{

    private final SoundCategory soundCategory;

    public VolumeSlider(Session session, SoundCategory soundCategory){
        super(session);

        this.soundCategory = soundCategory;

        float initialVolume = session.getOptions().getSoundVolume(soundCategory);
        setText(new Component().translation(soundCategory.getTranslateKey(), new Component().formattedText(String.valueOf(Maths.round(initialVolume * 100)))));
        setValue(initialVolume);
        setDivisions(100);
    }

    
    public void updateVolume(){
        if(!isChanged())
            return;

        float volume = getValue();
        setText(new Component().translation(soundCategory.getTranslateKey(), new Component().formattedText(String.valueOf(Maths.round(volume * 100)))));

        session.getOptions().setSoundVolume(soundCategory, volume);
        session.getOptions().save();
    }

}
