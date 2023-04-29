package pize.math;

/**
 * Math Utilities
 **/
public class Maths{

    public static final float PI = (float) Math.PI;
    public static final float PI2 = PI * 2;
    public static final float halfPI = PI / 2;
    public static final float toDeg = 180 / PI;
    public static final float toRad = PI / 180;
    public static final float sqrt2 = Mathc.sqrt(2);
    public static final float sqrt3 = Mathc.sqrt(3);
    public static final float sqrt4 = Mathc.sqrt(4);
    public static final int SECOND_IN_NANOS = 1000000000;


    public static float sinFromCos(float cos){
        return Mathc.sqrt(1 - cos * cos);
    }

    public static float cosFromSin(float sin){
        return Mathc.sqrt(1 - sin * sin);
    }


    public static int abs(int a){
        return a < 0 ? -a : a;
    }

    public static float abs(float a){
        return a < 0 ? -a : a;
    }

    public static double abs(double a){
        return a < 0 ? -a : a;
    }


    public static int floor(double a){
        return (int) (a < 0 ? a - 1 : a);
    }

    public static int round(double a){
        return floor(a + 0.5);
    }

    public static int ceil(double a){
        return (int) (a > 0 ? a + 1 : a);
    }


    public static float frac(float a){
        return floor(a) - a;
    }

    public static double frac(double a){
        return floor(a) - a;
    }


    public static int clamp(int a, int min, int max){
        return Math.max(min, Math.min(a, max));
    }

    public static float clamp(float a, float min, float max){
        return Math.max(min, Math.min(a, max));
    }

    public static double clamp(double a, double min, double max){
        return Math.max(min, Math.min(a, max));
    }


    public static float random(float min, float max){
        return lerp(min, max, Mathc.random());
    }

    public static int random(int min, int max){
        return round(lerp(min, max, Mathc.random()));
    }
    
    public static float random(float max){
        return Mathc.random() * max;
    }

    public static int random(int max){
        return round(Math.random() * max);
    }

    static public boolean randomBoolean(float chance){
        return Math.random() < chance;
    }

    static public boolean randomBoolean(){
        return randomBoolean(0.5F);
    }

    public static long randomSeed(int length){
        if(length <= 0)
            return 0;

        StringBuilder seed = new StringBuilder();
        for(int i = 0; i < length; i++)
            seed.append(random((i == 0 ? 1 : 0), 9));

        return Long.parseLong(seed.toString());
    }
    
    
    public static float lerp(float start, float end, float t){
        return start + (end - start) * t;
    }
    
    public static int lerp(int start, int end, int t){
        return start + (end - start) * t;
    }
    
    public static float cerp(float a, float b, float c, float d, float t){
        float p = (d - c) - (a - b);
        float q = (a - b) - p;
        float r = c - a;
        
        return t * (t * (t * p + q) + r) + b; // pt3 + qt2 + rt + b
    }
    
    
    public static float cubicCurve(float t){
        return -2 * t * t * t  +  3 * t * t;
    }
    
    public static float cosineCurve(float t){
        return (1 - Mathc.cos(t / PI)) / 2;
    }
    
    public static float quinticCurve(float t){
        return t * t * t * (t * (t * 6 - 15) + 10);
    }
    
    public static float hermiteCurve(float t){
        return t * t * (3 - 2 * t);
    }


    static public float map(float value, float fromLow, float fromHigh, float toLow, float toHigh){
        return (value - fromLow) * (toHigh - toLow) / (fromHigh - fromLow) + toLow;
    }

    public static double toInterval(double value, double start, double end){
        double interval = end - start + 1;

        if(value < start)
            value += interval * ceil((start - value) / interval);

        if(value > end)
            value -= interval * ceil((value - end) / interval);

        return value;
    }

}
