package pize.gui.components;

import pize.graphics.util.color.Color;
import pize.graphics.util.color.IColor;
import pize.graphics.util.batch.TextureBatchFast;
import pize.graphics.util.TextureUtils;
import pize.gui.UIComponent;

public class ColoredRect extends UIComponent<TextureBatchFast>{

    private final Color color;

    public ColoredRect(IColor color){
        this.color = new Color(color);
    }


    public Color getColor(){
        return color;
    }

    @Override
    protected void render(TextureBatchFast batch, float x, float y, float width, float height){
        batch.setColor(color);
        batch.draw(TextureUtils.quadTexture(), x, y, width, height);
        batch.resetColor();
    }

}
