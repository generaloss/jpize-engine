package pize.audio.util;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BooleanSupplier;

public class ContextTaskExecutor{
    
    private final ConcurrentLinkedQueue<Runnable> taskQueue;
    private final ConcurrentMap<BooleanSupplier, Runnable> pendingTasks;
    private boolean stopped;
    
    public ContextTaskExecutor(){
        taskQueue = new ConcurrentLinkedQueue<>();
        pendingTasks = new ConcurrentHashMap<>();
    }
    
    public void exec(Runnable runnable){
        if(!stopped)
            taskQueue.add(runnable);
    }

    public void execIf(Runnable runnable, BooleanSupplier condition){
        if(!stopped)
            pendingTasks.put(condition, runnable);
    }
    
    public void executeTasks(){
        if(stopped)
            return;
        
        taskQueue.forEach(task->Objects.requireNonNull(taskQueue.poll()).run());
        pendingTasks.forEach((key, value) -> {
            if(key.getAsBoolean()){
                pendingTasks.remove(key);
                value.run();
            }
        });
    }
    
    public void dispose(){
        stopped = true;
        taskQueue.clear();
    }
    
}
