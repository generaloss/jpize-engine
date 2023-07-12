package pize.graphics.util.color;

public class Color extends IColor{
    
    public static ImmutableColor WHITE = new ImmutableColor(1, 1, 1, 1F);
    public static ImmutableColor BLACK = new ImmutableColor(0, 0, 0, 1F);
    

    private float r, g, b, a;


    public Color(float red, float green, float blue, float alpha){
        set(red, green, blue, alpha);
    }

    public Color(double red, double green, double blue, double alpha){
        set(red, green, blue, alpha);
    }

    public Color(int red, int green, int blue, int alpha){
        set(red, green, blue, alpha);
    }

    public Color(IColor color){
        set(color);
    }

    public Color(float[] color){
        set(color);
    }

    public Color(){
        reset();
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


    public void set(float red, float green, float blue, float alpha){
        r = red;
        g = green;
        b = blue;
        a = alpha;
    }

    public void set(double red, double green, double blue, double alpha){
        r = (float) red;
        g = (float) green;
        b = (float) blue;
        a = (float) alpha;
    }

    public void set(int red, int green, int blue, int alpha){
        r = red / 255F;
        g = green / 255F;
        b = blue / 255F;
        a = alpha / 255F;
    }

    public void set(float[] color){
        set(color[0], color[1], color[2], color[3]);
    }

    public void set(IColor color){
        set(color.r(), color.g(), color.b(), color.a());
    }


    public void set(float red, float green, float blue){
        r = red;
        g = green;
        b = blue;
    }

    public void set(double red, double green, double blue){
        r = (float) red;
        g = (float) green;
        b = (float) blue;
    }

    public void set(int red, int green, int blue){
        r = red / 255F;
        g = green / 255F;
        b = blue / 255F;
    }


    public Color setR(float r){
        this.r = r;
        return this;
    }

    public Color setG(float g){
        this.g = g;
        return this;
    }

    public Color setB(float b){
        this.b = b;
        return this;
    }

    public Color setA(float a){
        this.a = a;
        return this;
    }


    public Color mul(double r, double g, double b, double a){
        set(
            r() * r,
            g() * g,
            b() * b,
            a() * a
        );
        
        return this;
    }

    public Color mul(double value){
        set(
            r() * value,
            g() * value,
            b() * value,
            a()
        );
        
        return this;
    }


    public Color blend(IColor color){
        float totalAlpha = a + color.a();
        float w1 = a / totalAlpha;
        float w2 = color.a() / totalAlpha;

        this.r = r * w1 + color.r() * w2;
        this.g = g * w1 + color.g() * w2;
        this.b = b * w1 + color.b() * w2;
        this.a = Math.max(a, color.a());

        return this;
    }

    public void reset(){
        set(1, 1, 1, 1F);
    }

    public Color inverse(){
        r = 1 - r;
        g = 1 - g;
        b = 1 - b;
        
        return this;
    }


    public Color copy(){
        return new Color(this);
    }

}
