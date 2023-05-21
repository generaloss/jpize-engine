package pize.util.time;

import pize.Pize;

public class Sync{

    private long prevTime;
    private long frameNano;
    private boolean enabled;

    public Sync(double fps){
        setFps(fps);
    }

    public Sync(){
        this(Pize.monitor().getRefreshRate());
    }

    public void setFps(double fps){
        frameNano = (long) (1000000000 / fps); // Время между кадрами, при [fps] количестве тпс
        prevTime = System.nanoTime(); // Для подсчета времени между кадрами

        enabled = true;
    }

    public void sync(){
        if(!enabled)
            return;

        final long deltaNano = System.nanoTime() - prevTime; // Текущее время между кадрами
        final long sleepNano = frameNano - deltaNano; // Время для коррекции тпс

        if(sleepNano > 0L){
            // Коррекция
            final long startTime = System.nanoTime();
            long elapsed;
            do{
                elapsed = System.nanoTime() - startTime;
            }
            while(elapsed < sleepNano);
        }

        prevTime = System.nanoTime();
    }

    public void enable(boolean value){
        enabled = value;
    }

    public boolean isEnabled(){
        return enabled;
    }

}