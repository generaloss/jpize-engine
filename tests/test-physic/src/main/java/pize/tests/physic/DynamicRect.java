package pize.tests.physic;

import pize.physic.BoundingBox2;
import pize.physic.RectBody;
import pize.physic.Velocity2f;

public class DynamicRect extends RectBody{

    private final Velocity2f velocity;

    public DynamicRect(BoundingBox2 rect){
        super(rect);

        velocity = new Velocity2f();
    }

    public Velocity2f motion(){
        return velocity;
    }

}
