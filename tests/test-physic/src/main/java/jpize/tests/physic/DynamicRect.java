package jpize.tests.physic;

import jpize.physic.AxisAlignedRect;
import jpize.physic.RectBody;
import jpize.physic.Velocity2f;

public class DynamicRect extends RectBody{

    private final Velocity2f velocity;

    public DynamicRect(AxisAlignedRect rect){
        super(rect);

        velocity = new Velocity2f();
    }

    public Velocity2f motion(){
        return velocity;
    }

}
