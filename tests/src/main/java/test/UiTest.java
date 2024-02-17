package test;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.graphics.font.FontLoader;
import jpize.graphics.texture.Texture;
import jpize.app.JpizeApplication;
import jpize.sdl.input.Key;
import jpize.ui.component.UIContext;
import jpize.ui.loader.PuiLoader;
import jpize.ui.palette.TextField;

public class UiTest extends JpizeApplication{

    UIContext ui;

    public void init(){
        var background = new Texture("ui/background_1.png");
        var font = FontLoader.getDefaultBold();

        var loader = new PuiLoader();
        loader.setRes("background", background);
        loader.setRes("font", font);

        ui = loader.loadCtxRes("ui/view.pui");
        ui.enable();

        TextField username = ui.findByID("username");
        TextField password = ui.findByID("password");

        username.addEnterCallback(((view, text) -> password.focus()));
        password.addEnterCallback(((view, text) -> username.focus()));
    }

    public void render(){
        Gl.clearColorBuffer();
        ui.render();

        if(Key.ESCAPE.isDown())
            Jpize.exit();
    }

    public void dispose(){
        ui.dispose();
    }

}
