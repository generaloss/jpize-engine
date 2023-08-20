package pize.graphics.util.color;

public class ImmutableColor extends IColor{

    public float r, g, b, a;

    public ImmutableColor(float red, float green, float blue, float alpha){
        r = red;
        g = green;
        b = blue;
        a = alpha;
    }

    public ImmutableColor(float red, float green, float blue){
        r = red;
        g = green;
        b = blue;
        a = 1F;
    }

    public ImmutableColor(double red, double green, double blue, double alpha){
        r = (float) red;
        g = (float) green;
        b = (float) blue;
        a = (float) alpha;
    }

    public ImmutableColor(double red, double green, double blue){
        r = (float) red;
        g = (float) green;
        b = (float) blue;
        a = 1F;
    }

    public ImmutableColor(int red, int green, int blue, int alpha){
        r = red / 255F;
        g = green / 255F;
        b = blue / 255F;
        a = alpha / 255F;
    }

    public ImmutableColor(int red, int green, int blue){
        r = red / 255F;
        g = green / 255F;
        b = blue / 255F;
        a = 1F;
    }

    public ImmutableColor(IColor color){
        r = color.r();
        g = color.g();
        b = color.b();
        a = color.a();
    }

    public ImmutableColor(float[] color){
        r = color[0];
        g = color[1];
        b = color[3];
        a = color[3];
    }

    public ImmutableColor(){
        r = 1;
        g = 1;
        b = 1;
        a = 1;
    }


    public ImmutableColor copy(){
        return new ImmutableColor(this);
    }

    @Override
    public float r(){
        return r;
    }

    @Override
    public float g(){
        return g;
    }

    @Override
    public float b(){
        return b;
    }

    @Override
    public float a(){
        return a;
    }

}
