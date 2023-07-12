package pize.tests.terraria.entity;

import pize.graphics.util.batch.TextureBatch;
import pize.physic.BoundingBox2;
import pize.physic.Motion2f;
import pize.physic.RectBody;

public abstract class Entity extends RectBody{

    private final Motion2f motion;

    public Entity(BoundingBox2 rect){
        super(rect);

        motion = new Motion2f();
    }

    public abstract void render(TextureBatch batch);

    public Motion2f getMotion(){
        return motion;
    }

}
