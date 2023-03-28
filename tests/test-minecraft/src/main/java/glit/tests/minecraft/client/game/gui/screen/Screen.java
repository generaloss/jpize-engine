package glit.tests.minecraft.client.game.gui.screen;

import glit.Glit;
import glit.context.Disposable;
import glit.context.Resizable;
import glit.graphics.gl.Wrap;
import glit.graphics.texture.Region;
import glit.graphics.texture.Texture;
import glit.graphics.util.batch.Batch;
import glit.io.glfw.Key;
import glit.tests.minecraft.client.game.Session;
import glit.tests.minecraft.client.game.gui.text.Component;

public abstract class Screen implements Resizable, Disposable{

    public static final float BUTTON_HEIGHT = 0.055F;
    public static final float TEXT_SCALING = 8 / 20F;
    public static final float TEXT_HEIGHT = BUTTON_HEIGHT * TEXT_SCALING;
    public static final float TITLE_SIZE = 0.125F;
    public static final float BUTTON_OFFSET_Y = 0.013F;


    public final Session session;
    private final Texture options_background;

    public Screen(Session session){
        this.session = session;

        options_background = session.getResourceManager().getTexture("options_background").getTexture();
        options_background.getParameters().setWrap(Wrap.REPEAT);
        options_background.update();
    }

    public void update(Batch batch){
        if(shouldCloseOnEsc() && Glit.isDown(Key.ESCAPE))
            close();

        if(renderDirtBackground()){
            batch.setColor(0.25F, 0.25F, 0.25F, 1F);
            batch.draw(options_background, 0, 0, Glit.getWidth(), Glit.getHeight(), new Region(0, 0, 11 * Glit.getAspect(), 11));
            batch.setColor(1, 1, 1, 1F);
        }
    }

    public void toScreen(String id){
        session.getScreenManager().setCurrentScreen(id);
    }


    public abstract void render(Batch batch);

    public abstract void onShow();

    public abstract boolean shouldCloseOnEsc();

    public abstract boolean renderDirtBackground();

    public abstract void close();


    public Component boolToText(boolean bool){
        return new Component().translation(bool ? "text.on" : "text.off");
    }

}
