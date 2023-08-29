package pize.tests.minecraft.run;

import pize.Jize;
import pize.gl.Gl;
import pize.gl.glenum.GlDepthFunc;
import pize.gl.glenum.GlTarget;
import pize.io.context.ContextAdapter;
import pize.io.context.ContextBuilder;
import pize.tests.minecraft.Session;
import pize.tests.minecraft.log.Logger;

public class Minecraft extends ContextAdapter{

    public static void main(String[] args){
        ContextBuilder.newContext("Minecraft")
                .size(925, 640)
                .icon("icon.png")
                .create()
                .init(new Minecraft());

        Jize.runContexts();
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
