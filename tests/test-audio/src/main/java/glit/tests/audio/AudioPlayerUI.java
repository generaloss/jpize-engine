package glit.tests.audio;

import glit.Glit;
import glit.audio.sound.Sound;
import glit.context.Disposable;
import glit.graphics.texture.Texture;
import glit.graphics.gl.Gl;
import glit.graphics.util.batch.TextureBatch;
import glit.gui.Align;
import glit.gui.LayoutType;
import glit.gui.components.*;
import glit.gui.constraint.Constraint;

public class AudioPlayerUI implements Disposable{

    private final TextureBatch batch;
    private final Texture sliderBarTexture, sliderHandleTexture;
    private final Layout layout;

    private Sound sound;
    private boolean playing;
    private float pointer;

    public AudioPlayerUI(){
        batch = new TextureBatch();

        sliderBarTexture = new Texture("slider_bar.png");
        sliderHandleTexture = new Texture("slider_handle.png");

        // UI
        layout = new Layout();
        layout.setLayoutType(LayoutType.VERTICAL);
        layout.alignItems(Align.UP);

        RegionMesh sliderTextureMesh = new RegionMesh(0, 0, 3, 4, 93, 20, 96, 24);
        ExpandableImage sliderBackground = new ExpandableImage(sliderBarTexture, sliderTextureMesh);
        sliderBackground.setExpandType(ExpandType.HORIZONTAL);

        Slider positionSlider = new Slider(sliderBackground, sliderHandleTexture);
        positionSlider.setSize(Constraint.relative(1), Constraint.relative(0.5));
        layout.put("position", positionSlider);

        Slider pitchSlider = new Slider(sliderBackground.clone(), sliderHandleTexture);
        pitchSlider.setSize(Constraint.relative(1), Constraint.relative(0.5));
        pitchSlider.setValue(1 / 2F);
        layout.put("pitch", pitchSlider);
    }


    public void renderUI(){
        Slider positionSlider = layout.get("position");
        Slider pitchSlider = layout.get("pitch");

        float currentPointer = positionSlider.getValue();

        if(playing){
            if(sound.isStopped()){
                playing = false;
                return;
            }

            if(currentPointer != pointer)
                sound.setPosition(currentPointer * sound.getDuration());

            pointer = positionSlider.getValue() + 1 / sound.getDuration() * Glit.getDeltaTime() * sound.getPitch();
            positionSlider.setValue(pointer);
        }
        if(positionSlider.isTouchDown())
            sound.pause();
        else if(positionSlider.isTouchReleased()){
            sound.play();
            playing = true;
        }

        if(sound != null && pitchSlider.isTouched() && pitchSlider.isChanged()){
            sound.setPitch(pitchSlider.getValue() * 2);
            sound.setPosition(currentPointer * sound.getDuration());
        }

        // Render

        Gl.clearColor(1, 1, 1, 1);
        Gl.clearBufferColor();
        batch.begin();
        layout.render(batch);
        batch.end();
    }


    public void setSound(Sound sound){
        if(this.sound != null)
            this.sound.stop();

        this.sound = sound;
    }

    public void play(){
        if(sound == null)
            return;

        sound.play();
        playing = true;
    }


    @Override
    public void dispose(){
        sliderBarTexture.dispose();
        sliderHandleTexture.dispose();
    }

}
