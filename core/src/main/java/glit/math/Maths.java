package glit.math;

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


    public static int module(int a){
        return a < 0 ? -a : a;
    }

    public static float module(float a){
        return a < 0 ? -a : a;
    }

    public static double module(double a){
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


    public static float fract(float a){
        a = module(a);
        return a - floor(a);
    }

    public static double fract(double a){
        a = module(a);
        return a - floor(a);
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
        return Mathc.random() * (max - min) + min;
    }

    public static int random(int min, int max){
        return round(Math.random() * (max - min) + min);
    }

    public static int random(int max){
        return round(Math.random() * max);
    }

    static public boolean randomBoolean(float chance){
        return Math.random() < chance;
    }

    static public boolean randomBoolean(){
        return Math.random() < 0.5;
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

    public static int lerp(int start, int end, float t){
        return start + round((end - start) * t);
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
