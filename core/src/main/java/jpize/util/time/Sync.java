package jpize.util.time;

import jpize.Jpize;
import jpize.math.Maths;
import jpize.sdl.Sdl;

public class Sync{

    private int prevTime;
    private int frameTime;
    private boolean enabled;

    public Sync(double tps){
        setTps(tps);
        enable(true);
    }

    public Sync(){
        this(Jpize.window().getDisplayMode().refreshRate);
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
        prevTime = Sdl.getTicks();               // Для подсчета времени между кадрами
    }


    public void sync(){
        if(!enabled || frameTime == 0)
            return;

        final int deltaTime = Sdl.getTicks() - prevTime; // Текущее время между кадрами
        if(deltaTime >= 0){
            
            final int sleepTime = frameTime - deltaTime; // Время для коррекции количества тиков в секунду
            if(sleepTime > 0)
                Sdl.delay(sleepTime);
        }
        
        prevTime = Sdl.getTicks();
    }

}