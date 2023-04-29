package pize.tests.minecraft.client.game.gui.components;

import pize.Pize;
import pize.graphics.texture.Region;
import pize.graphics.texture.TextureRegion;
import pize.graphics.util.batch.Batch;
import pize.gui.Align;
import pize.gui.constraint.Constraint;
import pize.math.Maths;
import pize.tests.minecraft.client.game.Session;
import pize.tests.minecraft.client.game.audio.Sound;
import pize.tests.minecraft.client.game.gui.screen.Screen;
import pize.tests.minecraft.client.game.gui.text.Component;

public class Slider extends MComponent{

    protected final Session session;

    private final TextureRegion barTexture, handleTexture, handleHoverTexture;
    private final Region barRegionLeft, barRegionRight, handleRegionLeft, handleRegionRight;
    private float value, prevValue, divisions;
    private boolean drag;
    private final TextView textView;


    public Slider(Session session){
        this.session = session;

        barTexture = session.getResourceManager().getTexture("button_blocked");
        handleTexture = session.getResourceManager().getTexture("button");
        handleHoverTexture = session.getResourceManager().getTexture("button_hover");

        barRegionLeft = new Region();
        barRegionRight = new Region();

        // Calc Handle Regions

        double regionOffset = 8.0 / 200 * 0.5;

        handleRegionLeft  = new Region(0, 0, regionOffset, 1);
        handleRegionRight = new Region(1 - regionOffset, 0, 1, 1);

        // Text

        super.alignItems(Align.CENTER);

        textView = new TextView(session, null);
        textView.setSize(Constraint.relative(Screen.TEXT_SCALING));
        textView.disableShadow(true);
        super.setAsParentFor(textView);
    }


    @Override
    public void render(Batch batch, float x, float y, float width, float height){
        // Calc Bar Regions

        double regionOffset = super.aspect() / barTexture.aspect() * 0.5;

        double halfTextureWidth = barTexture.getTexture().getWidth() * 0.5;
        regionOffset = Maths.round(regionOffset * halfTextureWidth) / halfTextureWidth;

        barRegionLeft.set(0, 0, regionOffset, 1);
        barRegionRight.set(1 - regionOffset, 0, 1, 1);

        // Render Bar

        float halfBarWidth = width * 0.5F;
        batch.draw(barTexture, x, y, halfBarWidth, height, barRegionLeft);
        batch.draw(barTexture, x + halfBarWidth, y, halfBarWidth, height, barRegionRight);

        // Render Handle

        float handleWidth = height * (handleRegionLeft.aspect() + handleRegionRight.aspect()) * super.aspect();

        float halfHandleWidth = handleWidth * 0.5F;
        float handleX = value * (width - handleWidth);

        TextureRegion currentTexture = isHover() ? handleHoverTexture : handleTexture;
        batch.draw(currentTexture, x + handleX,                   y, halfHandleWidth, height, handleRegionLeft);
        batch.draw(currentTexture, x + handleX + halfHandleWidth, y, halfHandleWidth, height, handleRegionRight);

        // Click Sound

        if(isTouchReleased())
            session.getAudioManager().play(Sound.CLICK, 1, 1);

        // Touch

        if(isTouchDown())
            drag = true;
        else if(Pize.isTouchReleased())
            drag = false;

        prevValue = value;

        if(!drag)
            return;

        float mouseX = Pize.getX();
        value = Maths.clamp((mouseX - x - halfHandleWidth) / (width - handleWidth), 0, 1);

        if(divisions > 0)
            value = Maths.round(value * divisions) / divisions;
    }


    public Component getText(){
        return textView.getText();
    }

    public void setText(Component component){
        this.textView.setText(component);
    }


    public float getValue(){
        return value;
    }

    public Slider setValue(double value){
        this.value = (float) Maths.clamp(value, 0, 1);
        prevValue = this.value;

        return this;
    }

    public boolean isChanged(){
        return prevValue != value;
    }

    public Slider setDivisions(int divisions){
        this.divisions = divisions;
        return this;
    }

    @Override
    protected float getWidthPixel(){
        return 0;//background.getWidth() / background.getPixelSize();
    }

    @Override
    protected float getHeightPixel(){
        return 0;//background.getHeight() / background.getPixelSize();
    }

}
