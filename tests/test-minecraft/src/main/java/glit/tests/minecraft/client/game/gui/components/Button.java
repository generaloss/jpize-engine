package glit.tests.minecraft.client.game.gui.components;

import glit.graphics.texture.TextureRegion;
import glit.graphics.util.batch.Batch;
import glit.gui.Align;
import glit.gui.components.ExpandType;
import glit.gui.components.ExpandableImage;
import glit.gui.components.RegionMesh;
import glit.gui.constraint.Constraint;
import glit.tests.minecraft.client.game.Session;
import glit.tests.minecraft.client.game.audio.Sound;
import glit.tests.minecraft.client.game.gui.screen.Screen;
import glit.tests.minecraft.client.game.gui.text.Component;

public class Button extends MComponent{

    private final Session session;

    private final TextureRegion texture, hoverTexture, blockedTexture;
    private final ExpandableImage background;
    private final TextView textView;
    private Runnable listener;
    private boolean blocked;

    public Button(Session session, Component text){
        this.session = session;
        this.texture = session.getResourceManager().getTexture("button");
        this.hoverTexture = session.getResourceManager().getTexture("button_hover");
        this.blockedTexture = session.getResourceManager().getTexture("button_blocked");

        background = new ExpandableImage(texture, new RegionMesh(0,0, 2,2, 198,17, 200,20));
        background.setSize(Constraint.match_parent, Constraint.match_parent);
        background.setExpandType(ExpandType.HORIZONTAL);
        super.setAsParentFor(background);

        textView = new TextView(session, text);
        textView.setSize(Constraint.relative(Screen.TEXT_HEIGHT / Screen.BUTTON_HEIGHT));
        super.setAsParentFor(textView);
        super.alignItems(Align.CENTER);
    }


    public void setClickListener(Runnable listener){
        this.listener = listener;
    }

    public void block(boolean flag){
        blocked = flag;
    }

    public boolean isBlocked(){
        return blocked;
    }

    public Component getText(){
        return textView.getText();
    }

    public void setText(Component component){
        this.textView.setText(component);
    }

    @Override
    protected void render(Batch batch, float x, float y, float width, float height){
        if(super.isTouchDown()){
            session.getAudioManager().play(Sound.CLICK, 1, 1);
            if(listener != null)
                listener.run();
        }

        if(blocked)
            background.setTexture(blockedTexture);
        else if(this.isHover())
            background.setTexture(hoverTexture);
        else
            background.setTexture(texture);

        background.render(batch);
        textView.render(batch);
    }

    @Override
    public boolean isHover(){
        return super.isHover() && !blocked;
    }

    @Override
    protected float getWidthPixel(){
        return background.getWidth() / background.getPixelSize();
    }

    @Override
    protected float getHeightPixel(){
        return background.getHeight() / background.getPixelSize();
    }

}
