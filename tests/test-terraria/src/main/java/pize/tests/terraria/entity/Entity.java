package pize.tests.terraria.entity;

import pize.graphics.util.batch.Batch;
import pize.physic.BoundingRect;
import pize.physic.RectBody;
import pize.physic.Motion2D;

public abstract class Entity extends RectBody{

    private final Motion2D motion;

    public Entity(BoundingRect rect){
        super(rect);

        motion = new Motion2D();
    }

    public abstract void render(Batch batch);

    public Motion2D getMotion(){
        return motion;
    }

}
