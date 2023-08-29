package jpize.tests.minecraft.gui.components;

import jpize.math.Maths;
import jpize.tests.minecraft.Session;
import jpize.tests.minecraft.gui.text.Component;
import jpize.tests.minecraft.options.SoundCategory;

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
