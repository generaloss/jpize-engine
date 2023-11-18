package jpize.util.time;

import jpize.math.Maths;

public class DeltaTimeCounter{

    private long lastTime;
    private float deltaTime;
    
    public DeltaTimeCounter(){
        lastTime = System.nanoTime();
    }
    
    public void count(){
        final long currentTime = System.nanoTime();
        deltaTime = (currentTime - lastTime) / Maths.NanosInSecond;
        lastTime = currentTime;
    }
    
    public float get(){
        return deltaTime;
    }
    
}
