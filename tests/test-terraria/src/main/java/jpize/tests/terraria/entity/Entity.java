package jpize.tests.terraria.entity;

import jpize.graphics.util.batch.TextureBatch;
import jpize.physic.BoundingBox2f;
import jpize.physic.Velocity2f;
import jpize.physic.RectBody;

public abstract class Entity extends RectBody{

    private final Velocity2f velocity;

    public Entity(BoundingBox2f rect){
        super(rect);

        velocity = new Velocity2f();
    }

    public abstract void render(TextureBatch batch);

    public Velocity2f getVelocity(){
        return velocity;
    }

}
