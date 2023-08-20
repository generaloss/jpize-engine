package pize.tests.minecraft.game.gui.components;

import pize.Pize;
import pize.lib.gl.texture.GlWrap;
import pize.graphics.texture.Region;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;
import pize.gui.constraint.Constraint;
import pize.tests.minecraft.game.Session;

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
        batch.draw(texture, 0, 0, Pize.getWidth(), Pize.getHeight(), new Region(0, 0,
            Pize.getWidth() / (8F * size * width),
            Pize.getHeight() / (8F * size * height)
        ));
        batch.setColor(1, 1, 1, 1);
    }
    
}