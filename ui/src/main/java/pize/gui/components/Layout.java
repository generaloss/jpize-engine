package pize.gui.components;

import pize.graphics.util.batch.TextureBatch;
import pize.gui.UIComponent;
import pize.gui.constraint.Constraint;

public class Layout extends UIComponent<TextureBatch>{

    public Layout(){
        super.setSize(Constraint.matchParent(), Constraint.matchParent());
    }

    @Override
    protected void render(TextureBatch batch, float x, float y, float width, float height){ }

}
