package pize.graphics.util.color;

import pize.math.Mathc;
import pize.math.Maths;

public abstract class IColor{

    abstract public float r();

    abstract public float g();

    abstract public float b();

    abstract public float a();


    public String getHex(){
        final String red   = Integer.toHexString(Maths.round(Maths.clamp(r(), 0, 1) * 255));
        final String green = Integer.toHexString(Maths.round(Maths.clamp(g(), 0, 1) * 255));
        final String blue  = Integer.toHexString(Maths.round(Maths.clamp(b(), 0, 1) * 255));
        final String alpha = Integer.toHexString(Maths.round(Maths.clamp(a(), 0, 1) * 255));

        final StringBuilder sb = new StringBuilder("#");

        if(red.length() < 2)
            sb.append('0');
        sb.append(red);

        if(green.length() < 2)
            sb.append('0');
        sb.append(green);

        if(blue.length() < 2)
            sb.append('0');
        sb.append(blue);

        if(alpha.length() < 2)
            sb.append('0');
        sb.append(alpha);

        return sb.toString();
    }


    public float[] toArray(){
        return new float[]{ r(), g(), b(), a() };
    }


    @Override
    public String toString(){
        return r() + ", " + g() + ", " + b() + ", " + a();
    }


    public static int floatToIntColor(float value){
        int intBits = Float.floatToRawIntBits(value);
        intBits |= (int) (( intBits >>> 24) * (255F / 254F)) << 24;
        return intBits;
    }

    public static float intToFloatColor(int value){
        return Float.intBitsToFloat(value & 0xFEFFFFFF);
    }
    
    public static Color random(){
        return new Color(
            Mathc.random(),
            Mathc.random(),
            Mathc.random(),
            1F
        );
    }

}
