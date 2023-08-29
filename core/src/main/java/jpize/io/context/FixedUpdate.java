package jpize.io.context;

import jpize.util.time.DeltaTimeCounter;
import jpize.util.time.TickGenerator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedUpdate{

    private final TickGenerator tickGenerator;
    private final DeltaTimeCounter deltaTimeCounter;
    private final ExecutorService executor;

    public FixedUpdate(int tps){
        this.tickGenerator = new TickGenerator(tps);
        this.deltaTimeCounter = new DeltaTimeCounter();
        this.executor = Executors.newFixedThreadPool(3);
    }

    public void start(Runnable runnable){
        if(tickGenerator.getSync().getTPS() == 0)
            return;

        tickGenerator.startAsync(()->{
            if(executor.isShutdown())
                return;

            deltaTimeCounter.count();
            executor.execute(runnable);
        });
    }

    public void stop(){
        tickGenerator.stop();
        executor.shutdownNow();
    }

    public float getDeltaTime(){
        return deltaTimeCounter.get();
    }

    public void setTPS(float updateTPS){
        tickGenerator.setTPS(updateTPS);
    }

}
