package pize.tests.voxelgame.client.chat;

import pize.util.time.Stopwatch;

public class ChatMessage{
    
    private final String message;
    private final Stopwatch stopwatch;
    
    public ChatMessage(String message){
        this.message = message;
        this.stopwatch = new Stopwatch().start();
    }
    
    
    public String getMessage(){
        return message;
    }
    
    public double getSeconds(){
        return stopwatch.getSeconds();
    }
    
}
