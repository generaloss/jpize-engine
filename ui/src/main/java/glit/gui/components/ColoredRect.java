package glit.gui.components;

import glit.graphics.util.color.Color;
import glit.graphics.util.color.IColor;
import glit.graphics.util.batch.TextureBatchFast;
import glit.graphics.util.TextureUtils;
import glit.gui.UIComponent;

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
