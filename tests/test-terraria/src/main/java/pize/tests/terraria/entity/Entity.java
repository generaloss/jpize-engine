package pize.tests.terraria.entity;

import pize.graphics.util.batch.Batch;
import pize.physic.BoundingRect;
import pize.physic.RectBody;
import pize.physic.Velocity2D;

public abstract class Entity extends RectBody{

    private final Velocity2D velocity;

    public Entity(BoundingRect rect){
        super(rect);

        velocity = new Velocity2D();
    }

    public abstract void render(Batch batch);

    public Velocity2D getVelocity(){
        return velocity;
    }

}
