package pize.graphics.util.batch;

import pize.app.Disposable;
import pize.graphics.texture.Region;
import pize.graphics.texture.Texture;
import pize.graphics.texture.TextureRegion;
import pize.graphics.util.color.Color;
import pize.graphics.util.color.IColor;
import pize.math.vecmath.vector.Vec2f;

public abstract class Batch implements Disposable{

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

    public void setColor(double r, double g, double b, double a){
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
