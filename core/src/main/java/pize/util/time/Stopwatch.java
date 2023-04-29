package pize.util.time;

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
    
    public Stopwatch setMillis(long millis){
        setNanos(millis * 1000000);
        return this;
    }

    public Stopwatch setSeconds(long seconds){
        setMillis(seconds * 1000);
        return this;
    }

    public Stopwatch setMinutes(long minutes){
        setSeconds(minutes * 60);
        return this;
    }

    public Stopwatch setHours(long hours){
        setMinutes(hours * 60);
        return this;
    }
    
    public long getNanos(){
        if(!started)
            return 0;
        if(!paused)
            nanos = System.nanoTime();
        return nanos - lastNanos;
    }
    
    public long getMillis(){
        return getNanos() / 1000000;
    }

    public long getSeconds(){
        return getMillis() / 1000;
    }

    public long getMinutes(){
        return getSeconds() / 60;
    }

    public long getHours(){
        return getMinutes() / 60;
    }

}
