package pize.tests.minecraft.gui.components;

import pize.Jize;
import pize.graphics.util.batch.TextureBatch;
import pize.gui.constraint.Constraint;

public class Layout extends MComponent{
    
    public Layout(){
        super.setSize(Constraint.matchParent(), Constraint.matchParent());
    }
    
    @Override
    protected void render(TextureBatch batch, float x, float y, float width, float height){
        // if(isHoverIgnoreChildren())
        //     batch.drawQuad(0.85F, 1F, 0.9F, 0.3F, x, y, width, height);
    }
    
    public boolean isHoverIgnoreChildren(){
        float mouseX = Jize.getX();
        float mouseY = Jize.getY();
        return !(mouseX < x || mouseY < y || mouseX > x + width || mouseY > y + height);
    }
    
}
