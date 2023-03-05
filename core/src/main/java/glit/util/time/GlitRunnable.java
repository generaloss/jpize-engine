package glit.util.time;

import glit.util.Utils;

public abstract class GlitRunnable implements Runnable{

    private boolean interrupt;

    public void runLater(long delay){
        Utils.delayMillis(delay);
        run();
    }

    public void runTimer(long delay, long period){
        runLater(delay);
        while(!Thread.interrupted() && !interrupt)
            runLater(period);
    }

    public void runLaterAsync(long delay){
        Thread thread = new Thread(()->runLater(delay), "DeltaRunnable-thread");
        thread.setDaemon(true);
        thread.start();
    }

    public void runTimerAsync(long delay, long period){
        Thread thread = new Thread(()->runTimer(delay, period), "DeltaRunnable-thread");
        thread.setDaemon(true);
        thread.start();
    }

    public void stop(){
        interrupt = true;
    }

}