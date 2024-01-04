package test;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.color.Color;
import jpize.io.context.JpizeApplication;
import jpize.sdl.input.Key;
import jpize.ui.constraint.Constr;
import jpize.ui.context.UIContext;
import jpize.ui.context.PuiLoader;
import jpize.ui.palette.Button;
import jpize.ui.palette.TextView;

public class UiTest extends JpizeApplication{

    Texture bg_0 = new Texture("ui/bg_0.jpg");
    Texture bg_1 = new Texture("ui/bg_1.png");
    Texture button_normal = new Texture("ui/button_normal.png");
    Texture button_pressed = new Texture("ui/button_pressed.png");
    BitmapFont font = FontLoader.getDefault();

    UIContext ui;

    public void init(){
        Jpize.window().setSizeCentered(1280, 720);
        font.setScale(0.8F);

        final PuiLoader loader = new PuiLoader()
            .putRes("font", font)
            .putRes("layout:bg_0", bg_0)
            .putRes("layout:bg_1", bg_1)
            .putRes("button:aspect", Constr.aspect(6))
            .putRes("button:color", new Color(1, 0, 0, 1));

        ui = loader.loadRes("ui/view_test.pui");
        ui.enable();

        final Button button = ui.getByID("done");
        // final Slider slider = ui.getByID("slider");

        button.input().addPressCallback((component, btn) -> component.style().background().color().setRgb(0.75));
        button.input().addReleaseCallback((component, btn) -> component.style().background().color().setRgb(0.5));

        // slider.addSliderCallback(((component, value) -> {
        //     slider.textview().setText("Slider: " + Maths.round(value * 100));
        //     slider.textview().color().setRgb(1 - value);
        //     component.style().background().color().setA(value);

        //     final Rect handle = ui.getByID("slider.handle");
        //     handle.style().setCornerRadius(Constr.px(value * 50));
        // }));

    }

    public void render(){
        final TextView fps = ui.getByID("fps");
        fps.setText("fps: " + (1 / Jpize.getDt()));
        // System.out.println(fps.getText());

        if(Key.ESCAPE.isDown()) Jpize.exit();

        if(Key.M.isDown()) Jpize.window().setSizeCentered(1280, 1280);
        if(Key.N.isDown()) Jpize.window().setSizeCentered(1280, 720);

        Gl.clearColorBuffer();
        ui.render();
    }

    public void resize(int width, int height){ }

    public void dispose(){
        ui.dispose();
    }

}
