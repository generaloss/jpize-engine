package jpize.util.time;

import jpize.Jpize;
import jpize.math.Maths;

public class Sync{

    private long prevTime;
    private long frameNano;
    private boolean enabled;

    public Sync(double fps){
        setTPS(fps);
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


    public double getTPS(){
        return (frameNano != 0) ? Maths.NanosInSecond / frameNano : 0;
    }

    public void setTPS(double tps){
        if(tps == 0)
            return;

        frameNano = (long) (Maths.NanosInSecond / tps); // Время между кадрами, при данном количестве тиков в секунду [tps]
        prevTime = System.nanoTime();                   // Для подсчета времени между кадрами
    }


    public void sync(){
        if(!enabled || frameNano == 0)
            return;

        final long deltaNano = System.nanoTime() - prevTime; // Текущее время между кадрами
        final long sleepNano = frameNano - deltaNano;        // Время для коррекции количества тиков в секунду

        if(sleepNano > 0L){
            // Коррекция
            final long startTime = System.nanoTime();
            long elapsed;
            do{
                elapsed = System.nanoTime() - startTime;
            }while(elapsed < sleepNano);
        }

        prevTime = System.nanoTime();
    }

}