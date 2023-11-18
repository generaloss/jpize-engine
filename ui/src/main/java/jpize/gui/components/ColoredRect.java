package jpize.gui.components;

import jpize.graphics.util.batch.TextureBatch;
import jpize.graphics.util.color.Color;
import jpize.graphics.util.color.IColor;
import jpize.gui.UIComponent;

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
