package glit.tests.minecraft.client.game.gui.screen.screens;

import glit.Glit;
import glit.graphics.util.batch.Batch;
import glit.gui.Align;
import glit.gui.LayoutType;
import glit.gui.components.Layout;
import glit.gui.constraint.Constraint;
import glit.tests.minecraft.client.game.Session;
import glit.tests.minecraft.client.game.gui.components.TextView;
import glit.tests.minecraft.client.game.gui.screen.Screen;
import glit.tests.minecraft.client.game.gui.text.Component;
import glit.tests.minecraft.client.game.gui.text.TextComponent;

public class IngameGUI extends Screen{

    private final Layout layout;

    public IngameGUI(Session session){
        super(session);

        // Main Layout
        layout = new Layout();
        layout.setLayoutType(LayoutType.VERTICAL);
        layout.alignItems(Align.LEFT_UP);

        // <FPS>
        TextView fps = new TextView(session, new Component().formattedText(Glit.getFps() + " FPS"));
        fps.setPosition(Constraint.relativeToHeight(0.005));
        fps.disableShadow(true);
        fps.show(session.getOptions().isShowFps());
        layout.put("fps", fps);
    }


    @Override
    public void render(Batch batch){
        ((TextComponent) ((TextView) layout.get("fps")).getText().getComponent(0)) .setText(Glit.getFps() + " FPS");

        layout.render(batch);
    }

    @Override
    public void resize(int width, int height){ }


    public void showFps(boolean showFps){
        layout.get("fps").show(showFps);
    }


    @Override
    public void onShow(){ }

    @Override
    public void close(){ }

    @Override
    public void dispose(){ }

    @Override
    public boolean shouldCloseOnEsc(){
        return false;
    }

    @Override
    public boolean renderDirtBackground(){
        return false;
    }

}
