package pize.util.time;

import pize.util.Utils;

public class PizeRunnable{

    private final Runnable runnable;
    private boolean interrupt;

    public PizeRunnable(Runnable runnable){
        this.runnable = runnable;
    }


    public void runLater(long delayMillis){
        Utils.delayMillis(delayMillis);
        runnable.run();
    }

    public void runTimer(long delayMillis, long periodMillis){
        runLater(delayMillis);
        while(!Thread.interrupted() && !interrupt)
            runLater(periodMillis);
    }

    public void runLaterAsync(long delayMillis){
        final Thread thread = new Thread(() -> runLater(delayMillis), "PizeRunnable-Thread");
        thread.setDaemon(true);
        thread.start();
    }

    public void runTimerAsync(long delayMillis, long periodMillis){
        final Thread thread = new Thread(() -> runTimer(delayMillis, periodMillis), "PizeRunnable-Thread");
        thread.setDaemon(true);
        thread.start();
    }

    public void stop(){
        interrupt = true;
    }

}