package jpize.tests.minecraft.gui.components;

import jpize.graphics.util.batch.TextureBatch;
import jpize.gui.UIComponent;
import jpize.gui.constraint.Constraint;

public class BaseLayout extends UIComponent<TextureBatch>{
    
    public BaseLayout(){
        super.setSize(Constraint.matchParent(), Constraint.matchParent());
    }
    
    @Override
    protected void render(TextureBatch batch, float x, float y, float width, float height){
        // if(isHover()){
        //     batch.drawQuad(0.1, x, y, width, height);
        //     batch.resetColor();
        // }
    }
    
}