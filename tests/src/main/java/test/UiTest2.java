package test;

import jpize.Jpize;
import jpize.app.JpizeApplication;
import jpize.gl.Gl;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.texture.Texture;
import jpize.ui.constraint.Constr;
import jpize.ui.context.PuiLoader;
import jpize.ui.context.UIContext;
import jpize.ui.palette.Button;
import jpize.ui.palette.Rect;

public class UiTest2 extends JpizeApplication{

    private final BitmapFont font;
    private final Texture background, icon, minecraftIcon;
    private final UIContext ui;

    private final Rect serverIcon;

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

        final Button start_button = this.ui.findByID("start_button");
        final Button stop_button = this.ui.findByID("stop_button");
        final Button kill_button = this.ui.findByID("kill_button");

        // start
        start_button.input().addPressCallback((view, btn) -> view.style().background().color().setRgb(0.8));
        start_button.input().addReleaseCallback((view, btn) -> view.style().background().color().setRgb(1));

        // stop
        stop_button.input().addPressCallback((view, btn) -> view.style().background().color().setRgb(0.8));
        stop_button.input().addReleaseCallback((view, btn) -> view.style().background().color().setRgb(1));

        // kill
        kill_button.input().addPressCallback((view, btn) -> view.style().background().color().setRgb(0.8));
        kill_button.input().addReleaseCallback((view, btn) -> view.style().background().color().setRgb(1));
    }


    public BitmapFont getFont(){
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
        if(Jpize.isTouched())
            ui.getRootComponent().size().set(Constr.px(Jpize.getX()), Constr.px(Jpize.getY()));

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
