package glit.tests.minecraft.client.game.gui.components;

import glit.math.Maths;
import glit.tests.minecraft.client.game.Session;
import glit.tests.minecraft.client.game.gui.text.Component;
import glit.tests.minecraft.client.game.options.SoundCategory;

public class VolumeSlider extends Slider{

    private final SoundCategory soundCategory;

    public VolumeSlider(Session session, SoundCategory soundCategory){
        super(session);

        this.soundCategory = soundCategory;

        float initialVolume = session.getOptions().getSoundVolume(soundCategory);
        setText(new Component().translation(soundCategory.getTranslateKey(), new Component().formattedText("" + Maths.round(initialVolume * 100))));
        setValue(initialVolume);
        setDivisions(100);
    }

    public boolean updateVolume(){
        if(!isChanged())
            return false;

        float volume = getValue();
        setText(new Component().translation(soundCategory.getTranslateKey(), new Component().formattedText("" + Maths.round(volume * 100))));

        session.getOptions().setSoundVolume(soundCategory, volume);
        session.getOptions().save();

        return true;
    }

}
