package pize.tests.minecraft.game.gui.components;

import pize.Pize;
import pize.graphics.util.batch.TextureBatch;
import pize.math.Maths;

public class ListView extends MComponent{
    
    private float scrollX, scrollY;
    
    @Override
    protected void render(TextureBatch batch, float x, float y, float width, float height){
        float tx = 0;// Pize.getX();
        float ty = 0;// Pize.getY();
        float sw = 0;// Pize.getWidth() / 2F;
        float sh = 0;// Pize.getHeight() / 2F;
        
        batch.beginScissor(x - sw + tx, y - sh + tx, width - sw + ty, height - sh + ty);
        
        scrollX = Maths.clamp(scrollX + Pize.mouse().getScrollX(Pize.keyboard()) * 50, -1000, 1000);
        scrollY = Maths.clamp(scrollY + Pize.mouse().getScrollY(Pize.keyboard()) * 50, -1000, 1000);
        setChildShift(scrollX, -scrollY);
    }
    
    @Override
    protected void renderEnd(TextureBatch batch){
        batch.endScissor();
    }
    
    
    public boolean isHoverIgnoreChildren(){
        float mouseX = Pize.getX();
        float mouseY = Pize.getY();
        return !(mouseX < x || mouseY < y || mouseX > x + width || mouseY > y + height);
    }
    
}
