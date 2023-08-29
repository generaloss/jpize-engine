package pize.tests.minecraft.gui.components;

import pize.Jize;
import pize.graphics.util.batch.TextureBatch;
import pize.math.Maths;

public class ListView extends MComponent{
    
    private float scrollX, scrollY;
    
    @Override
    protected void render(TextureBatch batch, float x, float y, float width, float height){
        batch.getScissor().begin(228, x, y, width, height);
        
        scrollX = Maths.clamp(scrollX + Jize.mouse().getScrollX() * 50, -1000, 1000);
        scrollY = Maths.clamp(scrollY + Jize.mouse().getScroll() * 50, -200, 0);
        setChildShift(scrollX, -scrollY);
    }
    
    @Override
    protected void renderEnd(TextureBatch batch){
        batch.getScissor().end(228);
    }
    
    
    public boolean isHoverIgnoreChildren(){
        float mouseX = Jize.getX();
        float mouseY = Jize.getY();
        return !(mouseX < x || mouseY < y || mouseX > x + width || mouseY > y + height);
    }
    
}
