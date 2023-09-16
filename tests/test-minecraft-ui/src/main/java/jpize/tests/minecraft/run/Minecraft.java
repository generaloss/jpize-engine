package jpize.tests.minecraft.run;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.gl.glenum.GlDepthFunc;
import jpize.gl.glenum.GlTarget;
import jpize.gl.tesselation.GlFace;
import jpize.gl.tesselation.GlPolygonMode;
import jpize.glfw.key.Key;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.batch.TextureBatch;
import jpize.io.context.JpizeApplication;
import jpize.io.context.ContextBuilder;
import jpize.tests.minecraft.Session;
import jpize.tests.minecraft.log.Logger;

public class Minecraft extends JpizeApplication{

    public static void main(String[] args){
        //ContextBuilder
        //        .newContext("Loading Minecraft...")
        //        .size(640, 360)
        //        .icon("icon.png")
        //        .borderless(true)
        //        .exitWhenWindowClose(false)
        //        .register().setAdapter(new LoadingWindow());

        Jpize.execSync(() ->
                ContextBuilder.newContext("Minecraft")
                        .size(925, 640)
                        .icon("icon.png")
                        .register().setAdapter(new Minecraft())
        );

        Jpize.runContexts();
    }

    private static class LoadingWindow extends JpizeApplication{
        final TextureBatch batch  = new TextureBatch();
        final Texture      splash = new Texture("loading-splash.jpg");
        final BitmapFont   font   = FontLoader.getDefault();

        public void init(){
            batch.begin();
            batch.setColor(1, 0.8, 0.9);
            batch.draw(splash, 0, 0, Jpize.getWidth(), Jpize.getHeight());
            font.drawText(batch, "Loading...", 20, 20);
            batch.end();

            batch.dispose();
            splash.dispose();
            font.dispose();

            Jpize.execSync(() ->
                ContextBuilder.newContext("Minecraft")
                        .size(925, 640)
                        .icon("icon.png")
                        .register().setAdapter(new Minecraft())
            );
        }
    }

    private Session session;

    @Override
    public void init(){
        Gl.clearColor(1, 1, 1);

        Gl.enable(GlTarget.DEPTH_TEST);
        Gl.depthFunc(GlDepthFunc.LEQUAL);
        Thread.currentThread().setName("Render Thread");

        this.session = new Session();

        Jpize.closeOtherWindows();
    }

    @Override
    public void render(){
        if(Key.NUM_1.isDown())
            Gl.polygonMode(GlFace.FRONT_AND_BACK, GlPolygonMode.FILL);
        if(Key.NUM_2.isDown())
            Gl.polygonMode(GlFace.FRONT_AND_BACK, GlPolygonMode.LINE);
        if(Key.NUM_3.isDown())
            Gl.polygonMode(GlFace.FRONT_AND_BACK, GlPolygonMode.POINT);

        Gl.clearColorDepthBuffers();
        session.render();
    }

    @Override
    public void resize(int width, int height){
        session.resize(width, height);
    }

    @Override
    public void dispose(){
        session.dispose();

        Logger.instance().info("EXIT.");
    }

}
