package pize.math;

public class Maths{

    public static final float epsilon = 1E-19F;
    public static final float PI = (float) Math.PI;
    public static final float PI2 = PI * 2;
    public static final float halfPI = PI / 2;
    public static final float toDeg = 180 / PI;
    public static final float toRad = PI / 180;
    public static final float sqrt2 = Mathc.sqrt(2);
    public static final float sqrt3 = Mathc.sqrt(3);
    public static final float sqrt4 = Mathc.sqrt(4);
    public static final float nanosInSecond = 1000000000;
    public static final float nanosInMs = 1000000;
    

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
        return a - floor(a);
    }

    public static double frac(double a){
        return a - floor(a);
    }
    
    public static double frac(double value, double min, double max){
        final double interval = max - min;
        return frac((value - min) / interval) * interval + min;
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
    
    public static double random(double min, double max){
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
    
    public static boolean randomBoolean(float chance){
        return Math.random() < chance;
    }
    
    public static boolean randomBoolean(){
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
    
    public static void randomInts(int[] array, int min, int max){
        final int range = max - min;
        for(int i = 0; i < array.length; i++)
            array[i] = Maths.round(Math.random() * range + min);
    }
    
    public static void randomShorts(Short[] array, int min, int max){
        final int range = max - min;
        for(int i = 0; i < array.length; i++){
            array[i] = (short) Maths.round(Math.random() * range + min);
        }
    }
    
    public static void randomBytes(byte[] array, int min, int max){
        final int range = max - min;
        for(int i = 0; i < array.length; i++)
            array[i] = (byte) Maths.round(Math.random() * range + min);
    }
    
    
    public static float lerp(float start, float end, float t){
        return start + (end - start) * t;
    }
    
    public static double lerp(double start, double end, double t){
        return start + (end - start) * t;
    }
    
    public static int lerp(int start, int end, int t){
        return start + (end - start) * t;
    }
    
    public static float cerp(float a, float b, float c, float d, float t){
        float p = (d - c) - (a - b);
        float q = (a - b) - p;
        float r = c - a;
        
        return t * (t * (t * p + q) + r) + b; // pt^3 + qt^2 + rt^1 + b
    }
    
    
    public static float cubic(float t){
        return -2 * t * t * t  +  3 * t * t;
    }
    
    public static float cosine(float t){
        return (1 - Mathc.cos(t / PI)) / 2;
    }
    
    public static float quintic(float t){
        return t * t * t * (t * (t * 6 - 15) + 10);
    }
    
    public static float hermite(float t){
        return t * t * (3 - 2 * t);
    }
    
    
    public static float sigmoid(float x){
        return 1 / (1 + Mathc.exp(-x));
    }
    
    public static float relu(float x){
        return Math.max(0, x);
    }
    
    public static float leakyRelu(float x){
        return Math.max(0.1F * x, x);
    }


    public static float map(float value, float fromLow, float fromHigh, float toLow, float toHigh){
        return (value - fromLow) * (toHigh - toLow) / (fromHigh - fromLow) + toLow;
    }
    
    public static double map(double value, double fromLow, double fromHigh, double toLow, double toHigh){
        return (value - fromLow) * (toHigh - toLow) / (fromHigh - fromLow) + toLow;
    }
    
    
    public static float sinDeg(double a){
        return Mathc.sin(a * toRad);
    }
    
    public static float cosDeg(double a){
        return Mathc.cos(a * toRad);
    }
    
    public static float tanDeg(double a){
        return Mathc.tan(a * toRad);
    }
    
    
    public static float invSqrt(float a){
        final float aHalf = a * 0.5F;
        int i = Float.floatToIntBits(a);
        i = 0x5f3759df - (i >> 1);
        a = Float.intBitsToFloat(i);
        a *= (1.5F - aHalf * a * a);
        return a;
    }
    
    public static double invSqrt(double a){
        final double aHalf = a * 0.5;
        long i = Double.doubleToLongBits(a);
        i = 0x5fe6ec85e7de30daL - (i >> 1);
        a = Double.longBitsToDouble(i);
        a *= (1.5 - aHalf * a * a);
        return a;
    }
    
    
    public static float dot(float[] a, float[] b){
        float result = 0;
        for(int i = 0; i < a.length; i++)
            result += a[i] * b[i];
        
        return result;
    }
    
    public static void mul(float[] in, float[] w, float[] out){
        for(int o = 0; o < out.length; o++)
            for(int i = 0; i < in.length; i++)
                out[o] += in[i] * w[i];
    }

}
