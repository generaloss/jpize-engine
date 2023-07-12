package pize.util.time;

import pize.math.Maths;

public class DeltaTimeCounter{

    private long lastTime;
    private float deltaTime;
    
    public DeltaTimeCounter(){
        lastTime = System.nanoTime();
    }
    
    public void update(){
        final long currentTime = System.nanoTime();
        deltaTime = (currentTime - lastTime) / Maths.nanosInSecond;
        lastTime = currentTime;
    }
    
    public float get(){
        return deltaTime;
    }
    
}
