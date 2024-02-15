package test;

import jpize.Jpize;
import jpize.app.JpizeApplication;
import jpize.gl.Gl;
import jpize.graphics.font.Font;
import jpize.graphics.font.FontLoader;
import jpize.graphics.texture.Texture;
import jpize.sdl.Sdl;
import jpize.ui.loader.PuiLoader;
import jpize.ui.UIContext;
import jpize.ui.palette.*;

public class UiTest2 extends JpizeApplication{

    private final Font font;
    private final Texture background, icon, minecraftIcon;
    private final UIContext ui;

    private final Rect serverIcon;
    private final TextView fps;

    public UiTest2(){
        this.font = FontLoader.getDefault();
        this.font.setScale(0.7F);
        this.background = new Texture("ui/background_2.png");
        this.icon = new Texture("ui/icon.png");
        this.minecraftIcon = new Texture("ui/minecraft-icon.png");

        this.ui = new PuiLoader()
            .setRes("font", font)
            .setRes("background", background)
            .setRes("icon", icon)
            .loadCtxRes("ui/ui.pui");
        this.ui.enable();

        this.serverIcon = ui.findByID("server_icon");
        this.fps = ui.findByID("fps");

        // Jpize.setVsync(false);

        Button button1 = ui.findByID("button 1");
        button1.input().addPressCallback((view, btn) -> Sdl.enableVsync(!Sdl.isVsyncEnabled()));


        Slider s1 = ui.findByID("slider 1");
        Slider s2 = ui.findByID("slider 2");
        Slider s3 = ui.findByID("slider 3");

        final TextField text_field = ui.findByID("text_field");
        text_field.setText("0.35 0.1 0.9  0.25 0 0.7");
        text_field.addInputCallback((view, text) -> {

            String[] c = text.split(" +");
            if(c.length != 6) return;
            try{
                float r1 = Float.parseFloat(c[0]);
                float g1 = Float.parseFloat(c[1]);
                float b1 = Float.parseFloat(c[2]);
                s1.handle().style().background().color().set(r1, g1, b1);
                s2.handle().style().background().color().set(r1, g1, b1);
                s3.handle().style().background().color().set(r1, g1, b1);

                float r2 = Float.parseFloat(c[3]);
                float g2 = Float.parseFloat(c[4]);
                float b2 = Float.parseFloat(c[5]);
                s1.line().style().background().color().set(r2, g2, b2);
                s2.line().style().background().color().set(r2, g2, b2);
                s3.line().style().background().color().set(r2, g2, b2);

            }catch(Exception ignored){ }
        });
    }


    public Font getFont(){
        return font;
    }

    public UIContext getUI(){
        return ui;
    }

    public Texture getMinecraftIcon(){
        return minecraftIcon;
    }

    @Override
    public void render(){
        fps.setText("fps: " + Jpize.getFPS());
        Gl.clearColorBuffer();
        ui.render();
    }

    @Override
    public void dispose(){
        font.dispose();
        background.dispose();
        icon.dispose();
        ui.dispose();
    }

}
