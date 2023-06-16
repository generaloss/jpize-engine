package pize.tests.minecraft.game.gui.components;

import pize.graphics.util.batch.TextureBatch;
import pize.gui.UIComponent;
import pize.gui.constraint.Constraint;

public class BaseLayout extends UIComponent<TextureBatch>{
    
    public BaseLayout(){
        super.setSize(Constraint.matchParent(), Constraint.matchParent());
    }
    
    @Override
    protected void render(TextureBatch batch, float x, float y, float width, float height){
        // if(isHover()){
        //     batch.setAlpha(0.1F);
        //     batch.draw(TextureUtils.quadTexture(), x, y, width, height);
        //     batch.resetColor();
        // }
    }
    
}