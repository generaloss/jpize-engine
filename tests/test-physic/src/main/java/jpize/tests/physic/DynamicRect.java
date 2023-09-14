package jpize.tests.physic;

import jpize.physic.axisaligned.rect.AARect;
import jpize.physic.axisaligned.rect.AARectBody;
import jpize.physic.utils.Velocity2f;

public class DynamicRect extends AARectBody{

    private final Velocity2f velocity;

    public DynamicRect(AARect rect){
        super(rect);

        velocity = new Velocity2f();
    }

    public Velocity2f motion(){
        return velocity;
    }

}
