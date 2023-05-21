package pize.gui.components;

import pize.Pize;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.Batch;
import pize.gui.UIComponent;
import pize.gui.constraint.Constraint;
import pize.math.Maths;

public class Slider extends UIComponent<Batch>{

    private final Texture handleTexture;
    private final UIComponent<Batch> background;
    private float value, prevValue, divisions;
    private boolean drag;

    public Slider(UIComponent<Batch> background,Texture handleTexture){
        this.background = background;
        this.handleTexture = handleTexture;

        super.setAsParentFor(background);
        background.setSize(Constraint.match_parent);
    }


    @Override
    public void render(Batch batch, float x, float y, float width, float height){
        background.render(batch);

        float sliderWidthT = height * handleTexture.aspect();

        batch.draw(handleTexture, x + value * ( width - sliderWidthT ), y, sliderWidthT, height);

        if(isTouchDown())
            drag = true;
        else if(Pize.isTouchReleased())
            drag = false;

        if(!drag)
            return;

        prevValue = value;

        float mouseX = Pize.getX();
        value = Maths.clamp(( mouseX - x - sliderWidthT / 2 ) / ( width - sliderWidthT ),0,1);

        if(divisions > 0)
            value = Maths.round(value * divisions) / divisions;
    }


    public float getValue(){
        return value;
    }

    public Slider setValue(double value){
        this.value = (float) value;
        return this;
    }

    public boolean isChanged(){
        return prevValue != value;
    }

    public Slider setDivisions(int divisions){
        this.divisions = divisions;
        return this;
    }

}
