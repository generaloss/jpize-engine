package pize.gui.components;

import pize.graphics.util.batch.Batch;
import pize.gui.UIComponent;
import pize.gui.constraint.Constraint;

public class Layout extends UIComponent<Batch>{

    public Layout(){
        super.setSize(Constraint.match_parent, Constraint.match_parent);
    }

    @Override
    protected void render(Batch batch, float x, float y, float width, float height){ }

}
