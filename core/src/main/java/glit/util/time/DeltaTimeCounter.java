package glit.util.time;

import glit.math.Mathc;
import glit.math.Maths;

public class DeltaTimeCounter{

    private long lastTime;
    private float deltaTime;
    private int precision;

    public DeltaTimeCounter(){
        setPrecision(-1);
    }

    public void update(){
        long currentTime = System.nanoTime();
        deltaTime = (float) (currentTime - lastTime) / Maths.SECOND_IN_NANOS;
        lastTime = currentTime;

        if(precision != -1)
            deltaTime = (float) Mathc.round(deltaTime * precision) / precision;
    }

    public void setPrecision(int precision){
        this.precision = precision == -1 ? -1 : (int) Math.pow(10, precision);
    }

    public float get(){
        return deltaTime;
    }

}
