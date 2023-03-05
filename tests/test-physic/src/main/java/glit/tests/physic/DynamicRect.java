package glit.tests.physic;

import glit.physic.BoundingRect;
import glit.physic.RectBody;
import glit.physic.Velocity2D;

public class DynamicRect extends RectBody{

    private final Velocity2D velocity;

    public DynamicRect(BoundingRect rect){
        super(rect);

        velocity = new Velocity2D();
    }

    public Velocity2D vel(){
        return velocity;
    }

}
