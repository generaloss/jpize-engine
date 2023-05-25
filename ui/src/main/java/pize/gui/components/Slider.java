package pize.gui.components;

import pize.Pize;
import pize.graphics.util.batch.Batch;
import pize.gui.UIComponent;
import pize.gui.constraint.Constraint;
import pize.math.Maths;

public class Slider extends UIComponent<Batch>{
    
    private final UIComponent<Batch> background, handle;
    private float value, prevValue, divisions;
    private boolean drag;

    public Slider(UIComponent<Batch> background, UIComponent<Batch> handle){
        this.background = background;
        this.handle = handle;
        
        super.setAsParentFor(background, handle);
        background.setSize(Constraint.matchParent());
        handle.setPosition(Constraint.relative(0));
    }


    @Override
    public void render(Batch batch, float x, float y, float width, float height){
        background.render(batch);
        
        float handleWidth = handle.getWidth();
        
        handle.getXConstraint().setValue(value * (width - handleWidth));
        handle.render(batch);

        if(isTouchDown())
            drag = true;
        else if(Pize.isTouchReleased())
            drag = false;

        if(!drag)
            return;

        prevValue = value;

        float mouseX = Pize.getX();
        value = Maths.clamp(( mouseX - x - handleWidth / 2 ) / ( width - handleWidth ),0,1);

        if(divisions > 0)
            value = Maths.round(value * divisions) / divisions;
    }


    public float getValue(){
        return value;
    }

    public Slider setValue(double value){
        if(value > 1 || value < 0)
            return this;
        
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
