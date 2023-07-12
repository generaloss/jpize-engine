package pize.audio.util;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TaskExecutor{
    
    private final ConcurrentLinkedQueue<Runnable> taskQueue;
    private boolean stopped;
    
    public TaskExecutor(){
        taskQueue = new ConcurrentLinkedQueue<>();
    }
    
    public void newTask(Runnable runnable){
        if(!stopped)
            taskQueue.add(runnable);
    }
    
    public void executeTasks(){
        if(stopped)
            return;
        
        taskQueue.forEach(task->Objects.requireNonNull(taskQueue.poll()).run());
    }
    
    public void dispose(){
        stopped = true;
        taskQueue.clear();
    }
    
}
