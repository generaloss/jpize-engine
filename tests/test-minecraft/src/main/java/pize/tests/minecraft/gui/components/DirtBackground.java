package pize.tests.minecraft.gui.components;

import pize.Jize;
import pize.gl.texture.GlWrap;
import pize.graphics.texture.Region;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;
import pize.gui.constraint.Constraint;
import pize.tests.minecraft.Session;

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
        batch.draw(texture, 0, 0, Jize.getWidth(), Jize.getHeight(), new Region(0, 0,
            Jize.getWidth() / (8F * size * width),
            Jize.getHeight() / (8F * size * height)
        ));
        batch.setColor(1, 1, 1, 1);
    }
    
}