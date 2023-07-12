package pize.tests.audio;

import pize.Pize;
import pize.audio.sound.Sound;
import pize.app.Disposable;
import pize.graphics.texture.Texture;
import pize.graphics.gl.Gl;
import pize.graphics.util.batch.TextureBatch;
import pize.gui.Align;
import pize.gui.LayoutType;
import pize.gui.components.*;
import pize.gui.constraint.Constraint;

public class AudioPlayerUI implements Disposable{

    private final TextureBatch batch;
    private final Texture sliderBarTexture, sliderHandleTexture;
    
    private final Layout layout;
    private Sound sound;

    public AudioPlayerUI(){
        batch = new TextureBatch();

        sliderBarTexture = new Texture("slider_bar.png");
        sliderHandleTexture = new Texture("slider_handle.png");
        
        layout = new Layout();
        layout.setLayoutType(LayoutType.VERTICAL);
        layout.alignItems(Align.UP);
        
        createUI();
    }
    
    private void createUI(){
        // Background and Handle for Sliders
        RegionMesh sliderBackgroundTextureMesh = new RegionMesh(0, 0, 3, 4, 93, 20, 96, 24);
        NinePatchImage sliderBackground = new NinePatchImage(sliderBarTexture, sliderBackgroundTextureMesh);
        sliderBackground.setExpandType(ExpandType.HORIZONTAL);
        
        Image sliderHandle = new Image(sliderHandleTexture);
        sliderHandle.setSize(Constraint.aspect(sliderHandleTexture.aspect()), Constraint.relative(1));
        
        // Slider Position
        Slider positionSlider = new Slider(sliderBackground, sliderHandle);
        positionSlider.setSize(Constraint.relative(1), Constraint.relative(0.5));
        layout.put("position", positionSlider);
        
        // Slider Pitch
        Slider pitchSlider = new Slider(sliderBackground.copy(), sliderHandle.copy());
        pitchSlider.setSize(Constraint.relative(1), Constraint.relative(0.5));
        pitchSlider.setValue(1 / 2F);
        layout.put("pitch", pitchSlider);
    }
    
    
    public void update(){
        updateSound();

        // Render
        Gl.clearColor(1, 1, 1, 1);
        Gl.clearColorBuffer();
        
        batch.begin();
        layout.render(batch);
        batch.end();
    }
    
    
    private float currentPosition;
    
    private void updateSound(){
        final Slider positionSlider = layout.get("position");
        final Slider pitchSlider = layout.get("pitch");
        float sliderPosition = positionSlider.getValue();
        
        // Pause while setting position
        if(positionSlider.isTouchDown())
            sound.pause();
        else if(positionSlider.isTouchReleased() && sliderPosition != 1)
            sound.play();
        
        if(sound.isPlaying() || sound.isPaused()){
            // Set new sound position
            if(currentPosition != sliderPosition)
                sound.setPosition(sliderPosition * sound.getDuration());
            
            // Increase slider position
            currentPosition = positionSlider.getValue() + 1F / sound.getDuration() * Pize.getDt() * sound.getPitch();
            positionSlider.setValue(currentPosition);
        }
        
        // Set new pitch
        if(pitchSlider.isTouched() && pitchSlider.isChanged()){
            sound.setPitch(pitchSlider.getValue() * 2);
            sound.setPosition(currentPosition * sound.getDuration());
        }
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
    }

    @Override
    public void dispose(){
        sliderBarTexture.dispose();
        sliderHandleTexture.dispose();
    }

}
