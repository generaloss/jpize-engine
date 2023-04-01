package glit.tests.minecraft.client.run;

import glit.Pize;
import glit.context.ContextListener;
import glit.graphics.gl.DepthFunc;
import glit.graphics.gl.Gl;
import glit.graphics.gl.Target;
import glit.tests.minecraft.client.game.Session;
import glit.tests.minecraft.utils.log.Logger;

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
        Gl.clearBuffer(true);
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
