package jpize.gui.components;

import jpize.graphics.util.batch.TextureBatch;
import jpize.gui.UIComponent;
import jpize.gui.constraint.Constraint;

public class Layout extends UIComponent<TextureBatch>{

    public Layout(){
        super.setSize(Constraint.matchParent(), Constraint.matchParent());
    }

    @Override
    protected void render(TextureBatch batch, float x, float y, float width, float height){ }

}
