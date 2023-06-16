package pize.util.time;

public abstract class TickGenerator implements Runnable{

    private final Sync sync;
    private boolean interrupt;

    public TickGenerator(float ticksPerSecond){
        sync = new Sync(ticksPerSecond);
    }
    
    
    public void setTPS(float ticksPerSecond){
        sync.setFps(ticksPerSecond);
    }
    
    public void start(){
        interrupt = false;
        while(!Thread.interrupted() && !interrupt){
            run();
            sync.sync();
        }
    }

    public void startAsync(){
        final Thread thread = new Thread(this::start);
        thread.setDaemon(true);
        thread.start();
    }

    public void stop(){
        interrupt = true;
    }

}
