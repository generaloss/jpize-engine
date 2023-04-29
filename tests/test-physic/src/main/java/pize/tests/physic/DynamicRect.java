package pize.tests.physic;

import pize.physic.BoundingRect;
import pize.physic.RectBody;
import pize.physic.Velocity2D;

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
