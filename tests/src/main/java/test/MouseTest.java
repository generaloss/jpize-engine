package test;

import io.github.libsdl4j.api.video.SDL_DisplayMode;
import jpize.Jpize;
import jpize.gl.Gl;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.util.batch.TextureBatch;
import jpize.io.context.JpizeApplication;
import jpize.sdl.input.Btn;
import jpize.sdl.input.Key;

import java.util.StringJoiner;

import static io.github.libsdl4j.api.video.SdlVideo.SDL_GetDisplayMode;

public class MouseTest extends JpizeApplication{

    TextureBatch batch;
    BitmapFont font;

    public void init(){
        Gl.clearColor(0.4, 0.5, 0.7);

        batch = new TextureBatch();

        font = FontLoader.loadFnt("fonts/font.fnt");
        font.setScale(0.5F);
        font.options().textAreaWidth = Jpize.getWidth();

        SDL_DisplayMode mode = new SDL_DisplayMode();
        SDL_GetDisplayMode(Jpize.window().getDisplayIndex(), 0, mode);
        mode = Jpize.window().getDisplayMode();
        System.out.println(mode.w + ", " + mode.h + ", " + mode.refreshRate);
//        GlfwCursor cursor = new GlfwCursor("textures/texture15.png");
//        Jpize.mouse().setCursor(cursor);
    }

    public void update(){
        if(Key.ESCAPE.isDown()) Jpize.exit();
        if(Key.F11.isDown()) Jpize.window().toggleFullscreenDesktop();
        if(Key.C.isDown()) Jpize.input().toCenter();
        if(Key.NUM_1.isDown())Jpize.input().show();
        if(Key.NUM_2.isDown())Jpize.input().hide();
    }

    public void render(){
        String info =
                  "X: " + Jpize.getX() + ", Y: " + Jpize.getY() + "\n"
                + "Show (Num 1/2): " + Jpize.input().isShow() + "\n"
                + "Scroll X/Y: " + Jpize.input().getScrollX() + "/" + Jpize.input().getScroll() + "\n"
                + "To center - C";

        Gl.clearColorBuffer();
        batch.begin();

        font.options().invLineWrap = true;
        font.drawText(batch, info, 0, Jpize.getHeight());

        StringJoiner pressedKeys = new StringJoiner(", ");
        for(Btn button: Btn.values())
            if(button.isPressed())
                pressedKeys.add(button.toString());
        font.options().invLineWrap = false;
        font.drawText(batch, "Pressed buttons: " + pressedKeys, 0, 0);

        batch.end();
    }

}
