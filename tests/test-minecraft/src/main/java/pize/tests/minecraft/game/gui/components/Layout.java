package pize.tests.minecraft.game.gui.components;

import pize.Pize;
import pize.graphics.util.batch.TextureBatch;
import pize.gui.constraint.Constraint;

public class Layout extends MComponent{
    
    public Layout(){
        super.setSize(Constraint.matchParent(), Constraint.matchParent());
    }
    
    @Override
    protected void render(TextureBatch batch, float x, float y, float width, float height){
        // if(isHoverIgnoreChildren()){
        //     batch.setColor(0.85F, 1F, 0.9F, 0.3F);
        //     batch.draw(TextureUtils.quadTexture(), x, y, width, height);
        //     batch.resetColor();
        // }
    }
    
    public boolean isHoverIgnoreChildren(){
        float mouseX = Pize.getX();
        float mouseY = Pize.getY();
        return !(mouseX < x || mouseY < y || mouseX > x + width || mouseY > y + height);
    }
    
}
