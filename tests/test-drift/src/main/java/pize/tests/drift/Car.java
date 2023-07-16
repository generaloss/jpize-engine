package pize.tests.drift;

import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;
import pize.io.glfw.Key;
import pize.math.Mathc;
import pize.math.Maths;
import pize.math.vecmath.vector.Vec2f;

public class Car extends CarPart{

    private final Wheel wheelFront1, wheelFront2, wheelBack1, wheelBack2;
    private float backWheelsVelocity;
    private float wheelsRotation;

    public Car(){
        super(new Texture("coin.png"), 282, 612);
        super.setOrigin(0.5F, 0.288F);

        wheelFront1 = new Wheel(new Texture("wheel.png"), 28, 75) .setParent(this) .setPosition(-105, 340) .setOrigin(0.8, 0.5);
        wheelFront2 = new Wheel(new Texture("wheel.png"), 28, 75) .setParent(this) .setPosition( 105, 340) .setOrigin(0.2, 0.5);
        wheelBack1 =  new Wheel(new Texture("wheel.png"), 28, 75) .setParent(this) .setPosition(-105, 0  ) .setOrigin(0.8, 0.5);
        wheelBack2 =  new Wheel(new Texture("wheel.png"), 28, 75) .setParent(this) .setPosition( 105, 0  ) .setOrigin(0.2, 0.5);
    }

    public void update(){
        // Control moving
        backWheelsVelocity *= 0.995;
        if(Key.W.isPressed())
            backWheelsVelocity += 0.3;
        if(Key.S.isPressed())
            backWheelsVelocity -= 0.3;

        // Control wheels rotation
        // wheelsRotation = -(Pize.getX() - Pize.getWidth() / 2F) / Pize.getWidth() * 500;
        // wheelsRotation = Mathc.pow(Math.abs(wheelsRotation), 0.75) * Math.signum(wheelsRotation);
        if(Key.A.isPressed())
            wheelsRotation += 0.4;
        if(Key.D.isPressed())
            wheelsRotation -= 0.4;
        wheelsRotation = Maths.clamp(wheelsRotation, -65, 65);

        // Velocity
        final Vec2f directedLocalVelocity = new Vec2f(0, Math.abs(Maths.cosDeg(wheelsRotation)) * backWheelsVelocity);
        final Vec2f velocity = new Vec2f(directedLocalVelocity.copy().rotDeg(getRotation()));
        super.getPosition().add(velocity);

        // Rotation
        super.rotate(Mathc.asin(backWheelsVelocity / Mathc.sqrt(340 * 340 + backWheelsVelocity * backWheelsVelocity)) * 180 / Maths.PI * Maths.sinDeg(wheelsRotation));
        wheelFront1.setRotation(wheelsRotation);
        wheelFront2.setRotation(wheelsRotation);
    }


    public void render(TextureBatch batch){
        super.render(batch);
        wheelFront1.render(batch);
        wheelFront2.render(batch);
        wheelBack1.render(batch);
        wheelBack2.render(batch);
        batch.rotate(0);
    }

    public void dispose(){
        super.dispose();
        wheelFront1.dispose();
        wheelFront2.dispose();
        wheelBack1.dispose();
        wheelBack2.dispose();
    }

}
