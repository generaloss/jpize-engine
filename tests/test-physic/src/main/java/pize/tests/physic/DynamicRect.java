package pize.tests.physic;

import pize.physic.BoundingRect;
import pize.physic.RectBody;
import pize.physic.Motion2D;

public class DynamicRect extends RectBody{

    private final Motion2D motion;

    public DynamicRect(BoundingRect rect){
        super(rect);

        motion = new Motion2D();
    }

    public Motion2D motion(){
        return motion;
    }

}
