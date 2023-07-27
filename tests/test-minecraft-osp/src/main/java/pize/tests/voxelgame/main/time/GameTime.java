package pize.tests.voxelgame.main.time;

import pize.math.Maths;
import pize.tests.voxelgame.main.Tickable;

public class GameTime implements Tickable{

    public static final int TICKS_PER_SECOND = 20;
    public static final int TICKS_IN_SECOND = 20;
    public static final int SECONDS_IN_MINUTE = 60;
    public static final int TICKS_IN_MINUTE = TICKS_IN_SECOND * SECONDS_IN_MINUTE;
    public static final int MINUTES_IN_DAY = 20;
    public static final int TICKS_IN_DAY = TICKS_IN_MINUTE * MINUTES_IN_DAY;

    public static final int TIME_DAY = 1000;
    public static final int TIME_MIDNIGHT = 18000;
    public static final int TIME_NIGHT = 13000;
    public static final int TIME_NOON = 6000;


    private long ticks;

    @Override
    public void tick(){
        ticks++;
    }
    
    
    public long getTicks(){
        return ticks;
    }
    
    public float getSeconds(){
        return (float) ticks / TICKS_IN_SECOND;
    }
    
    public float getMinutes(){
        return (float) ticks / TICKS_IN_MINUTE;
    }
    
    public float getDays(){
        return (float) ticks / TICKS_IN_DAY;
    }
    
    public int getDayNumber(){
        return Maths.floor(getDays());
    }
    
    public String getTime(){
        return
            String.format("%02d", Maths.floor(getDayMinutes())) + ":" +
            String.format("%02d", Maths.floor(Maths.frac(getDayMinutes()) * SECONDS_IN_MINUTE));
    }
    
    
    public void setTicks(long ticks){
        this.ticks = ticks;
    }
    
    public void setSeconds(double seconds){
        setTicks(Math.round(seconds * TICKS_IN_SECOND));
    }
    
    public void setMinutes(double minutes){
        setTicks(Math.round(minutes * TICKS_IN_MINUTE));
    }
    
    public void setDays(double days){
        setTicks(Math.round(days * TICKS_IN_DAY));
    }
    
    public void setTime(float time, TimeUnit unit){
        switch(unit){
            case TICKS -> setTicks((long) time);
            case SECONDS -> setSeconds(time);
            case MINUTES -> setMinutes(time);
            case DAYS -> setDays(time);
        }
    }
    
    
    public long getDayTicks(){
        return ticks % TICKS_IN_DAY;
    }
    
    public float getDaySeconds(){
        return (float) getDayTicks() / TICKS_IN_SECOND;
    }
    
    public float getDayMinutes(){
        return (float) getDayTicks() / TICKS_IN_MINUTE;
    }
    
    
    public long getFloorDayTicks(){
        return Math.floorDiv(ticks, TICKS_IN_DAY) * TICKS_IN_DAY;
    }
    
    public void setDayTicks(long ticks){
        setTicks(getFloorDayTicks() + ticks);
    }
    
    public void setDaySeconds(double seconds){
        setDayTicks(Math.round(seconds * TICKS_IN_SECOND));
    }
    
    public void setDayMinutes(double minutes){
        setDayTicks(Math.round(minutes * TICKS_IN_MINUTE));
    }
    
    public void setDayTime(float time, TimeUnit unit){
        switch(unit){
            case TICKS -> setDayTicks((long) time);
            case SECONDS -> setDaySeconds(time);
            case MINUTES -> setDayMinutes(time);
        }
    }
    
    
    public void setDay(){
        setDayTicks(TIME_DAY);
    }
    
    public void setMidnight(){
        setDayTicks(TIME_MIDNIGHT);
    }
    
    public void setNight(){
        setDayTicks(TIME_NIGHT);
    }
    
    public void setNoon(){
        setDayTicks(TIME_NOON);
    }

}
