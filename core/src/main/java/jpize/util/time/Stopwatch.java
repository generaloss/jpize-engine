package jpize.util.time;

import jpize.math.Maths;

public class Stopwatch{

    private long lastNanos, nanos;
    private boolean started, paused;

    public Stopwatch start(){
        if(started)
            return this;

        started = true;
        lastNanos = System.nanoTime();
        nanos = lastNanos;
        return this;
    }

    public Stopwatch reset(){
        lastNanos = System.nanoTime();
        nanos = lastNanos;
        return this;
    }

    public Stopwatch stop(){
        if(started)
            started = false;
        return this;
    }

    public Stopwatch pause(){
        paused = true;
        return this;
    }

    public Stopwatch resume(){
        paused = false;
        return this;
    }
    
    public Stopwatch setNanos(long nanos){
        lastNanos = this.nanos - nanos;
        return this;
    }
    
    public Stopwatch setMillis(double millis){
        return setNanos(Maths.round(millis * 1000000));
    }

    public Stopwatch setSeconds(double seconds){
        return setMillis(seconds * 1000);
    }

    public Stopwatch setMinutes(double minutes){
        return setSeconds(minutes * 60);
    }

    public Stopwatch setHours(double hours){
        return setMinutes(hours * 60);
    }
    
    public long getNanos(){
        if(!started)
            return 0;
        if(!paused)
            nanos = System.nanoTime();
        return nanos - lastNanos;
    }
    
    public double getMillis(){
        return getNanos() / 1000000D;
    }

    public double getSeconds(){
        return getMillis() / 1000;
    }

    public double getMinutes(){
        return getSeconds() / 60;
    }

    public double getHours(){
        return getMinutes() / 60;
    }

}
