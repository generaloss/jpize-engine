package glit.tests.minecraft.client.run;

import glit.Glit;
import glit.context.ContextListener;
import glit.graphics.util.Gl;
import glit.tests.minecraft.client.game.Session;
import glit.tests.minecraft.utils.log.Logger;

public class Minecraft implements ContextListener{

    public static void main(String[] args){
        Glit.create("Minecraft", 925, 530);
        Glit.window().setIcon("icon.png");
        Glit.init(new Minecraft());
    }


    private Session session;

    @Override
    public void init(){
        Thread.currentThread().setName("Render Thread");

        session = new Session();
    }

    @Override
    public void render(){
        Gl.clearBufferColor();
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
