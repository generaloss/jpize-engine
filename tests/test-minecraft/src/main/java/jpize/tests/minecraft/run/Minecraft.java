package jpize.tests.minecraft.run;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.gl.glenum.GlDepthFunc;
import jpize.gl.glenum.GlTarget;
import jpize.io.context.ContextAdapter;
import jpize.io.context.ContextBuilder;
import jpize.tests.minecraft.Session;
import jpize.tests.minecraft.log.Logger;

public class Minecraft extends ContextAdapter{

    public static void main(String[] args){
        ContextBuilder.newContext("Minecraft")
                .size(925, 640)
                .icon("icon.png")
                .create()
                .init(new Minecraft());

        Jpize.runContexts();
    }


    private Session session;

    @Override
    public void init(){
        Gl.clearColor(1, 1, 1);

        Gl.enable(GlTarget.DEPTH_TEST);
        Gl.depthFunc(GlDepthFunc.LEQUAL);
        Thread.currentThread().setName("Render Thread");

        session = new Session();
    }

    @Override
    public void render(){
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
