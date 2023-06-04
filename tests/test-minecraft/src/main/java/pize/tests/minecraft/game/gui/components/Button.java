package pize.tests.minecraft.game.gui.components;

import pize.graphics.texture.TextureRegion;
import pize.graphics.util.batch.TextureBatch;
import pize.gui.Align;
import pize.gui.components.ExpandType;
import pize.gui.components.NinePatchImage;
import pize.gui.components.RegionMesh;
import pize.gui.constraint.Constraint;
import pize.tests.minecraft.game.Session;
import pize.tests.minecraft.game.audio.Sound;
import pize.tests.minecraft.game.gui.screen.Screen;
import pize.tests.minecraft.game.gui.text.Component;

public class Button extends MComponent{

    private final Session session;

    private final TextureRegion texture, hoverTexture, blockedTexture;
    private final NinePatchImage background;
    private final TextView textView;
    private Runnable listener;
    private boolean blocked;

    public Button(Session session, Component text){
        this.session = session;
        this.texture = session.getResourceManager().getTexture("button");
        this.hoverTexture = session.getResourceManager().getTexture("button_hover");
        this.blockedTexture = session.getResourceManager().getTexture("button_blocked");

        background = new NinePatchImage(texture, new RegionMesh(0,0, 2,2, 198,17, 200,20));
        background.setSize(Constraint.matchParent(), Constraint.matchParent());
        background.setExpandType(ExpandType.HORIZONTAL);
        super.setAsParentFor(background);

        textView = new TextView(session, text);
        textView.setSize(Constraint.relative(Screen.TEXT_HEIGHT / Screen.BUTTON_HEIGHT));
        textView.setScissor(true);
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
    protected void render(TextureBatch batch, float x, float y, float width, float height){
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
    
}
