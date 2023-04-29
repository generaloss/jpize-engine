package pize.tests.minecraft.client.run;

import pize.Pize;
import pize.context.ContextListener;
import pize.graphics.gl.DepthFunc;
import pize.graphics.gl.Gl;
import pize.graphics.gl.Target;
import pize.tests.minecraft.client.game.Session;
import pize.tests.minecraft.utils.log.Logger;

public class Minecraft implements ContextListener{

    public static void main(String[] args){
        Pize.create("Minecraft", 925, 530);
        Pize.window().setIcon("icon.png");
        Pize.run(new Minecraft());
    }


    private Session session;

    @Override
    public void init(){
        Gl.enable(Target.DEPTH_TEST);
        Gl.depthFunc(DepthFunc.LEQUAL);
        Thread.currentThread().setName("Render Thread");

        session = new Session();
    }

    @Override
    public void render(){
        Gl.clearBuffers(true);
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
