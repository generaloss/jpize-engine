package pize.tests.minecraft.run;

import pize.Pize;
import pize.app.AppAdapter;
import pize.graphics.gl.DepthFunc;
import pize.graphics.gl.Gl;
import pize.graphics.gl.Target;
import pize.tests.minecraft.game.Session;
import pize.tests.minecraft.utils.log.Logger;

public class Minecraft extends AppAdapter{

    public static void main(String[] args){
        Pize.create("Minecraft", 925, 640);
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
