package glit.graphics.util.batch;

import glit.context.Disposable;
import glit.graphics.texture.Region;
import glit.graphics.texture.Texture;
import glit.graphics.texture.TextureRegion;
import glit.graphics.util.color.Color;
import glit.graphics.util.color.IColor;
import glit.math.vecmath.vector.Vec2f;

public abstract class Batch implements Disposable{

    public static int QUAD_INDICES = 6;
    public static int QUAD_VERTICES = 4;

    protected final Color color;

    public Batch(){
        color = new Color();
    }


    public abstract void draw(Texture texture, float x, float y, float width, float height);

    public abstract void draw(TextureRegion texReg, float x, float y, float width, float height);

    public abstract void draw(Texture texture, float x, float y, float width, float height, Region region);

    public abstract void draw(TextureRegion texReg, float x, float y, float width, float height, Region region);


    public void resetColor(){
        color.set(1, 1, 1, 1F);
    }

    public void setColor(IColor color){
        this.color.set(color);
    }

    public void setColor(float r, float g, float b, float a){
        color.set(r, g, b, a);
    }

    public Color getColor(){
        return color;
    }

    public void setAlpha(double a){
        color.setA((float) a);
    }


    public abstract Vec2f getTransformOrigin();

    public abstract void setTransformOrigin(double x, double y);

    public abstract void rotate(float angle);

    public abstract void shear(float angleX, float angleY);

    public abstract void scale(float scale);

    public abstract void scale(float x, float y);

    public abstract void flip(boolean x, boolean y);


    public abstract int size();


}
