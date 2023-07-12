package pize.gui.components;

import pize.graphics.util.batch.TextureBatch;
import pize.graphics.util.color.Color;
import pize.graphics.util.color.IColor;
import pize.gui.UIComponent;

public class ColoredRect extends UIComponent<TextureBatch>{

    private final Color color;

    public ColoredRect(IColor color){
        this.color = new Color(color);
    }


    public Color getColor(){
        return color;
    }

    @Override
    protected void render(TextureBatch batch, float x, float y, float width, float height){
        batch.drawQuad(color, x, y, width, height);
    }

}
