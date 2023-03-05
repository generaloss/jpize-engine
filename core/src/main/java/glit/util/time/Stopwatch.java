package glit.util.time;

public class Stopwatch{

    private long lastMillis, millis;
    private boolean started, paused;

    public Stopwatch start(){
        if(started)
            return this;

        started = true;
        lastMillis = System.currentTimeMillis();
        millis = lastMillis;
        return this;
    }

    public Stopwatch reset(){
        lastMillis = System.currentTimeMillis();
        millis = lastMillis;
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

    public Stopwatch setMillis(long millis){
        lastMillis = this.millis - millis;
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

    public long getMillis(){
        if(!started)
            return 0;
        if(!paused)
            millis = System.currentTimeMillis();
        return millis - lastMillis;
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
