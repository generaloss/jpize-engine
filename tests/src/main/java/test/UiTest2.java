package test;

import jpize.Jpize;
import jpize.app.JpizeApplication;
import jpize.gl.Gl;
import jpize.graphics.font.Font;
import jpize.graphics.font.FontLoader;
import jpize.graphics.texture.Texture;
import jpize.ui.context.PuiLoader;
import jpize.ui.context.UIContext;
import jpize.ui.palette.Rect;
import jpize.ui.palette.TextField;
import jpize.ui.palette.TextView;

public class UiTest2 extends JpizeApplication{

    private final Font font;
    private final Texture background, icon, minecraftIcon;
    private final UIContext ui;

    private final Rect serverIcon;
    private final TextView fps;

    public UiTest2(){
        this.font = FontLoader.getDefault();
        this.font.setScale(0.7F);
        this.background = new Texture("ui/background.png");
        this.icon = new Texture("ui/icon.png");
        this.minecraftIcon = new Texture("ui/minecraft-icon.png");

        this.ui = new PuiLoader()
            .putRes("font", font)
            .putRes("background", background)
            .putRes("icon", icon)
            .loadRes("ui/ui.pui");
        this.ui.enable();

        this.serverIcon = ui.findByID("server_icon");
        this.fps = ui.findByID("fps");

        // Jpize.setVsync(false);
        TextField text_field = ui.findByID("text_field");
        text_field.addInputCallback((view, text) -> {
            System.out.println(text);
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
