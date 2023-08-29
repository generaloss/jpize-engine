package jpize.gui.components;

import jpize.graphics.font.BitmapFont;
import jpize.graphics.util.batch.TextureBatch;
import jpize.graphics.util.color.Color;
import jpize.graphics.util.color.IColor;
import jpize.gui.UIComponent;
import jpize.gui.constraint.Constraint;
import jpize.math.Maths;
import jpize.math.vecmath.vector.Vec2f;

public class TextView extends UIComponent<TextureBatch>{

    private BitmapFont font;
    private String text;
    private final Color color, backgroundColor;

    private boolean shadow;
    private final Color shadowColor;
    private float shadowOffsetX, shadowOffsetY;


    public TextView(String text, BitmapFont font){
        this.text = text;
        this.font = font;
        color = new Color();
        backgroundColor = new Color(0, 0, 0, 0);
        shadowColor = new Color(0, 0, 0, 0);
    }

    @Override
    public void render(TextureBatch batch, float x, float y, float width, float height){
        if(font == null)
            return;

        // Calculate a True Text Gravity

        Vec2f bounds = font.getBounds(text);
        float renderX = x - bounds.x;// * (gravityOffsetX / parentWidth );
        float renderY = y - bounds.y;// * (gravityOffsetY / parentHeight);

        // Render

        if(backgroundColor.a() != 0)
            batch.drawQuad(backgroundColor, renderX, renderY, bounds.x, bounds.y);

        if(shadow){
            batch.setColor(shadowColor);
            font.drawText(batch, text,
                renderX + Maths.round(shadowOffsetX * font.getScale()),
                renderY + Maths.round(shadowOffsetY * font.getScale())
            );
        }

        batch.setColor(color);
        font.drawText(batch, text, renderX, renderY);
        batch.resetColor();
    }

    public void setWidth(Constraint constraintWidth){ }

    public void setHeight(Constraint width){ }


    public BitmapFont getFont(){
        return font;
    }

    public void setFont(BitmapFont font){
        this.font = font;
    }


    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }


    public Color getColor(){
        return color;
    }


    public void setShadow(float x, float y, IColor shadowColor){
        shadow = true;

        shadowOffsetX = x;
        shadowOffsetY = y;

        this.shadowColor.set(shadowColor);
    }

    public Color getShadowColor(){
        return shadowColor;
    }

    public void disableShadow(){
        shadow = false;
    }


    public Color getBackgroundColor(){
        return backgroundColor;
    }

}
