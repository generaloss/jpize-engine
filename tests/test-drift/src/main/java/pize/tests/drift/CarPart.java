package pize.tests.drift;

import pize.app.Disposable;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;
import pize.math.vecmath.vector.Vec2f;

public class CarPart implements Disposable{

    private final Vec2f position, finalPosition, size, origin;
    private float rotation, finalRotation;
    private final Texture texture;

    private CarPart parent;

    public CarPart(Texture texture, float width, float height){
        this.texture = texture;
        this.size = new Vec2f(width, height);

        this.position = new Vec2f();
        this.origin = new Vec2f(0.5);
        this.finalPosition = new Vec2f();
    }

    public void render(TextureBatch batch){
        if(parent != null){
            finalRotation = rotation + parent.finalRotation;
            finalPosition.set(position) // that position
                .rotDeg(parent.finalRotation).add(parent.finalPosition); // parent
        }else{
            finalRotation = rotation;
            finalPosition.set(position); // that position
        }

        final Vec2f renderPosition = finalPosition.copy().sub(size.copy().mul(origin)); // origin

        batch.setTransformOrigin(origin.x, origin.y);
        batch.rotate(finalRotation);
        batch.draw(texture, renderPosition.x, renderPosition.y, size.x, size.y);
    }


    public <P extends CarPart> P setParent(CarPart parent){
        this.parent = parent;
        return (P) this;
    }

    public <P extends CarPart> P setRotation(float rotation){
        this.rotation = rotation;
        return (P) this;
    }

    public <P extends CarPart> P setOrigin(double x, double y){
        this.origin.set(x, y);
        return (P) this;
    }

    public <P extends CarPart> P setPosition(double x, double y){
        this.position.set(x, y);
        return (P) this;
    }

    public float getRotation(){
        return rotation;
    }

    public Vec2f getPosition(){
        return position;
    }

    public void rotate(float angle){
        rotation += angle;
    }


    public void dispose(){
        texture.dispose();
    }

}
