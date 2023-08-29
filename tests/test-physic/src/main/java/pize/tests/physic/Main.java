package pize.tests.physic;

import pize.Jize;
import pize.io.context.ContextAdapter;
import pize.gl.Gl;
import pize.graphics.util.batch.TextureBatch;
import pize.glfw.key.Key;
import pize.io.context.ContextBuilder;
import pize.math.vecmath.vector.Vec2f;
import pize.physic.BoundingBox2f;
import pize.physic.Collider2f;

public class Main extends ContextAdapter{

    public static void main(String[] args){
        ContextBuilder.newContext("Physics")
                .size(1280, 720)
                .create()
                .init(new Main());

        Jize.runContexts();
    }


    private TextureBatch batch;
    private DynamicRect rect1, rect2;

    @Override
    public void init(){
        batch = new TextureBatch();

        rect1 = new DynamicRect(new BoundingBox2f(-25, -25, 25, 25));
        rect1.motion().setMax(50);

        rect2 = new DynamicRect(new BoundingBox2f(-100, -100, 100, 100));
        rect2.pos().add(600, 400);
    }

    @Override
    public void render(){
        Jize.window().setTitle("Physics (fps: " + Jize.getFPS() + ")");

        if(Key.ESCAPE.isDown())
            Jize.exit();
        if(Key.F11.isDown())
            Jize.window().toggleFullscreen();

        if(Jize.isTouched())
            rect1.motion().add(Jize.getCursorPos().sub(rect1.pos()).nor());
        
        final Vec2f collidedMotion = Collider2f.getCollidedMotion(rect1, rect1.motion(), rect2);
        rect1.pos().add(collidedMotion);
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