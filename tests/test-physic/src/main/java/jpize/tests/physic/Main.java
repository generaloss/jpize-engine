package jpize.tests.physic;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.glfw.key.Key;
import jpize.graphics.util.batch.TextureBatch;
import jpize.io.context.ContextBuilder;
import jpize.io.context.JpizeApplication;
import jpize.math.vecmath.vector.Vec2f;
import jpize.physic.AxisAlignedRect;
import jpize.physic.Collider2f;

public class Main extends JpizeApplication{

    public static void main(String[] args){
        ContextBuilder.newContext("Physics")
                .size(1280, 720)
                .register()
                .setAdapter(new Main());
        Jpize.runContexts();
    }


    private TextureBatch batch;
    private DynamicRect rect1, rect2;

    @Override
    public void init(){
        batch = new TextureBatch();

        rect1 = new DynamicRect(new AxisAlignedRect(-25, -25, 25, 25));
        rect1.motion().setMax(50);

        rect2 = new DynamicRect(new AxisAlignedRect(-100, -100, 100, 100));
        rect2.getPosition().add(600, 400);
    }

    @Override
    public void render(){
        Jpize.window().setTitle("Physics (fps: " + Jpize.getFPS() + ")");

        if(Key.ESCAPE.isDown())
            Jpize.exit();
        if(Key.F11.isDown())
            Jpize.window().toggleFullscreen();

        if(Jpize.isTouched())
            rect1.motion().add(Jpize.getCursorPos().sub(rect1.getPosition()).nor());

        final Vec2f collidedMotion = Collider2f.getCollidedMovement(rect1.motion(), rect1, rect2);
        rect1.getPosition().add(collidedMotion);
        rect1.motion().clampToMax().reduce(0.3).collidedAxesToZero(collidedMotion);

        Gl.clearColorBuffer();
        Gl.clearColor(0.2, 0.1, 0.3);
        batch.begin();
        batch.drawQuad(1, 1, 0, 1,  rect1.getMin().x, rect1.getMin().y, rect1.rect().getWidth(), rect1.rect().getHeight());
        batch.drawQuad(0.5, 0.2, 0.2, 1,  rect2.getMin().x, rect2.getMin().y, rect2.rect().getWidth(), rect2.rect().getHeight());
        batch.end();
    }
    
    @Override
    public void dispose(){
        batch.dispose();
    }

}