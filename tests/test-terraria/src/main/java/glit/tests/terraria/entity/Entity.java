package glit.tests.terraria.entity;

import glit.graphics.util.batch.Batch;
import glit.physic.BoundingRect;
import glit.physic.RectBody;
import glit.physic.Velocity2D;

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
