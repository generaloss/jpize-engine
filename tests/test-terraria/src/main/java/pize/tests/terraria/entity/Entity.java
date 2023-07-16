package pize.tests.terraria.entity;

import pize.graphics.util.batch.TextureBatch;
import pize.physic.BoundingBox2;
import pize.physic.Velocity2f;
import pize.physic.RectBody;

public abstract class Entity extends RectBody{

    private final Velocity2f velocity;

    public Entity(BoundingBox2 rect){
        super(rect);

        velocity = new Velocity2f();
    }

    public abstract void render(TextureBatch batch);

    public Velocity2f getVelocity(){
        return velocity;
    }

}
