package jpize.tests.minecraft.gui.screen.screens;

import jpize.Jpize;
import jpize.graphics.util.batch.TextureBatch;
import jpize.gui.Align;
import jpize.gui.LayoutType;
import jpize.gui.components.Layout;
import jpize.gui.constraint.Constraint;
import jpize.tests.minecraft.Session;
import jpize.tests.minecraft.gui.components.TextView;
import jpize.tests.minecraft.gui.screen.Screen;
import jpize.tests.minecraft.gui.text.Component;
import jpize.tests.minecraft.gui.text.TextComponent;

public class IngameGUI extends Screen{

    private final Layout layout;

    public IngameGUI(Session session){
        super(session);

        // Main Layout
        layout = new Layout();
        layout.setLayoutType(LayoutType.VERTICAL);
        layout.alignItems(Align.LEFT_UP);

        // <FPS>
        TextView fps = new TextView(session, new Component().formattedText(Jpize.getFPS() + " FPS"));
        fps.setPosition(Constraint.relativeToHeight(0.005));
        fps.disableShadow(true);
        fps.show(session.getOptions().isShowFps());
        layout.put("fps", fps);
    }


    @Override
    public void render(TextureBatch batch){
        ((TextComponent) ((TextView) layout.get("fps")).getText().getComponent(0)) .setText(Jpize.getFPS() + " FPS");

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
