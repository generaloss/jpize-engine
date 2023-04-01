package glit.tests.minecraft.client.game.gui.components;

import glit.Pize;
import glit.graphics.util.batch.Batch;
import glit.gui.UIComponent;
import glit.math.Maths;

public abstract class MComponent extends UIComponent<Batch>{

    @Override
    public void update(){
        int pixelSizeX = Maths.round((float) Pize.getHeight() / getWidthPixel());
        int pixelSizeY = Maths.round((float) Pize.getHeight() / getHeightPixel());

        x = Maths.round(x / pixelSizeX) * pixelSizeX;
        y = Maths.round(y / pixelSizeY) * pixelSizeY;
        width = Maths.round(width);
        height = Maths.round(height);
    }

    protected abstract float getWidthPixel();

    protected abstract float getHeightPixel();

}
