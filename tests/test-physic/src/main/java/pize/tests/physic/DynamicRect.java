package pize.tests.physic;

import pize.physic.BoundingBox2;
import pize.physic.RectBody;
import pize.physic.Motion2f;

public class DynamicRect extends RectBody{

    private final Motion2f motion;

    public DynamicRect(BoundingBox2 rect){
        super(rect);

        motion = new Motion2f();
    }

    public Motion2f motion(){
        return motion;
    }

}
