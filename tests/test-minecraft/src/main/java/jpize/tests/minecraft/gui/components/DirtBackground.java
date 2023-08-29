package jpize.tests.minecraft.gui.components;

import jpize.Jpize;
import jpize.gl.texture.GlWrap;
import jpize.graphics.texture.Region;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.batch.TextureBatch;
import jpize.gui.constraint.Constraint;
import jpize.tests.minecraft.Session;

public class DirtBackground extends MComponent{
    
    private final Texture texture;
    
    public DirtBackground(Session session){
        this.texture = session.getResourceManager().getTexture("options_background").getTexture();
        this.texture.getParameters().setWrap(GlWrap.REPEAT);
        this.texture.update();
        
        this.setSize(Constraint.pixel(1));
    }
    
    @Override
    protected void render(TextureBatch batch, float x, float y, float width, float height){
        final int size = 5;
        
        batch.setColor(0.25, 0.25, 0.25, 1);
        batch.draw(texture, 0, 0, Jpize.getWidth(), Jpize.getHeight(), new Region(0, 0,
            Jpize.getWidth() / (8F * size * width),
            Jpize.getHeight() / (8F * size * height)
        ));
        batch.setColor(1, 1, 1, 1);
    }
    
}