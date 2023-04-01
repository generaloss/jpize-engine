package glit.tests.physic;

import glit.Pize;
import glit.context.ContextListener;
import glit.graphics.gl.Gl;
import glit.graphics.util.batch.TextureBatch;
import glit.graphics.util.TextureUtils;
import glit.io.glfw.Key;
import glit.math.vecmath.vector.Vec2d;
import glit.physic.BoundingRect;
import glit.physic.Collider2D;

public class Main implements ContextListener{

    public static void main(String[] args){
        Pize.create("Physics",1280,720);
        Pize.run(new Main());
    }


    private TextureBatch batch;
    private DynamicRect rect1, rect2;

    @Override
    public void init(){
        batch = new TextureBatch();

        rect1 = new DynamicRect(new BoundingRect(-25, -25, 25, 25));
        rect1.vel().setMax(50);

        rect2 = new DynamicRect(new BoundingRect(-100, -100, 100, 100));
        rect2.pos().add(600, 400);
    }

    @Override
    public void render(){
        Pize.window().setTitle("Physics (fps: " + Pize.getFPS() + ")");

        if(Pize.isDown(Key.ESCAPE))
            Pize.exit();
        if(Pize.isDown(Key.F11))
            Pize.window().toggleFullscreen();
        if(Pize.isDown(Key.V))
            Pize.window().toggleVsync();

        if(Pize.isTouched())
            rect1.vel().add(new Vec2d(Pize.getCursorPos().sub(rect1.pos())).nor());
        Vec2d move = Collider2D.getCollidedMove(rect1, rect1.vel(), rect2);
        rect1.pos().add(move);
        rect1.vel().clampToMax().reduce(0.3).collidedAxesToZero(move);

        Gl.clearBufferColor();
        Gl.clearColor(0.2, 0.1, 0.3);
        batch.begin();
        batch.setColor(1, 1, 0, 1);
        batch.draw(TextureUtils.quadTexture(), rect1.getMin().x, rect1.getMin().y, rect1.rect().getWidth(), rect1.rect().getHeight());
        batch.setColor(0.5F, 0.2F, 0.2F, 1);
        batch.draw(TextureUtils.quadTexture(), rect2.getMin().x, rect2.getMin().y, rect2.rect().getWidth(), rect2.rect().getHeight());
        batch.end();
    }

    @Override
    public void resize(int w, int h){}

    @Override
    public void dispose(){
        batch.dispose();
    }

}