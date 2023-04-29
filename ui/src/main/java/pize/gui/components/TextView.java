package pize.gui.components;

import pize.graphics.font.BitmapFont;
import pize.graphics.util.*;
import pize.graphics.util.batch.TextureBatch;
import pize.graphics.util.color.Color;
import pize.graphics.util.color.IColor;
import pize.gui.UIComponent;
import pize.gui.constraint.Constraint;
import pize.math.Maths;
import pize.math.vecmath.tuple.Tuple2f;

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

        Tuple2f bounds = font.getBounds(text);
        float renderX = x - bounds.x;// * (gravityOffsetX / parentWidth );
        float renderY = y - bounds.y;// * (gravityOffsetY / parentHeight);

        // Render

        if(backgroundColor.a() != 0){
            batch.setColor(backgroundColor);
            batch.draw(TextureUtils.quadTexture(), renderX, renderY, bounds.x, bounds.y);
        }

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
