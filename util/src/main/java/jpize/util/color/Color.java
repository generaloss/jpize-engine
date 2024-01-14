package jpize.util.color;

public class Color extends IColor{

    public static final ImmutableColor WHITE = new ImmutableColor(1, 1, 1, 1);
    public static final ImmutableColor BLACK = new ImmutableColor(0, 0, 0, 1);


    private float r, g, b, a;


    public Color(float red, float green, float blue, float alpha){
        set(red, green, blue, alpha);
    }

    public Color(float red, float green, float blue){
        set(red, green, blue, 1F);
    }

    public Color(double red, double green, double blue, double alpha){
        set(red, green, blue, alpha);
    }

    public Color(double red, double green, double blue){
        set(red, green, blue, 1F);
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


    public Color set(float red, float green, float blue, float alpha){
        r = red;
        g = green;
        b = blue;
        a = alpha;
        return this;
    }

    public Color set(double red, double green, double blue, double alpha){
        r = (float) red;
        g = (float) green;
        b = (float) blue;
        a = (float) alpha;
        return this;
    }

    public Color setInt(int red, int green, int blue, int alpha){
        r = red / 255F;
        g = green / 255F;
        b = blue / 255F;
        a = alpha / 255F;
        return this;
    }

    public Color set(float[] color){
        return set(color[0], color[1], color[2], color[3]);
    }

    public Color set(IColor color){
        return set(color.r(), color.g(), color.b(), color.a());
    }


    public Color setRgb(float red, float green, float blue){
        r = red;
        g = green;
        b = blue;
        return this;
    }

    public Color setRgb(double red, double green, double blue){
        r = (float) red;
        g = (float) green;
        b = (float) blue;
        return this;
    }

    public Color setRgb(float rgb){
        return setRgb(rgb, rgb, rgb);
    }

    public Color setRgb(double rgb){
        return setRgb(rgb, rgb, rgb);
    }

    public Color set3Int(int red, int green, int blue){
        r = red / 255F;
        g = green / 255F;
        b = blue / 255F;
        return this;
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


    public Color setR(double r){
        return setR((float) r);
    }

    public Color setG(double g){
        return setG((float) g);
    }

    public Color setB(double b){
        return setB((float) b);
    }

    public Color setA(double a){
        return setA((float) a);
    }


    public Color add3(double r, double g, double b){
        return setRgb(r() + r, g() + g, b() + b);
    }

    public Color add3(Color color){
        return add3(color.r, color.g, color.b);
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

    public Color mul3(double value){
        setRgb(
            r() * value,
            g() * value,
            b() * value
        );

        return this;
    }

    public Color div3(double value){
        setRgb(
            r() / value,
            g() / value,
            b() / value
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

    public Color reset(){
        return set(1, 1, 1, 1);
    }

    public Color inverse3(){
        r = 1 - r;
        g = 1 - g;
        b = 1 - b;
        return this;
    }

    public Color inverse4(){
        a = 1 - a;
        return inverse3();
    }


    public Color copy(){
        return new Color(this);
    }


    public static Color newInt(int red, int green, int blue, int alpha){
        return new Color(red / 255F, green / 255F, blue / 255F, alpha / 255F);
    }

    public static Color newInt(int red, int green, int blue){
        return newInt(red, green, blue, 255);
    }

}
