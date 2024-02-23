package jpize.util.time;

import jpize.util.Utils;
import jpize.util.math.Maths;

public class Sync{

    private long prevTime;
    private int frameTime;
    private boolean enabled;

    public Sync(double tps){
        setTps(tps);
        enable(true);
    }


    public boolean isEnabled(){
        return enabled;
    }

    public void enable(boolean enabled){
        this.enabled = enabled;
    }


    public double getTps(){
        return (frameTime != 0) ? (Maths.nanosInSec / frameTime) : 0;
    }

    public void setTps(double tps){
        if(tps == 0)
            return;

        frameTime = (int) (Maths.msInSec / tps); // Время между кадрами, при данном количестве тиков в секунду [tps]
        prevTime = System.currentTimeMillis();   // Для подсчета времени между кадрами
    }


    public void sync(){
        if(!enabled || frameTime == 0)
            return;

        final long deltaTime = System.currentTimeMillis() - prevTime; // Текущее время между кадрами
        if(deltaTime >= 0){
            
            final long sleepTime = frameTime - deltaTime; // Время для коррекции количества тиков в секунду
            if(sleepTime > 0)
                Utils.delayElapsedMillis(sleepTime);
        }
        
        prevTime = System.currentTimeMillis();
    }

}