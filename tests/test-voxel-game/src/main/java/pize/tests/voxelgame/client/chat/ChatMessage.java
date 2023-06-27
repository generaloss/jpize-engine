package pize.tests.voxelgame.client.chat;

import pize.tests.voxelgame.base.text.ComponentText;
import pize.util.time.Stopwatch;

import java.util.List;

public class ChatMessage{
    
    private final List<ComponentText> components;
    private final Stopwatch stopwatch;
    
    public ChatMessage(List<ComponentText> components){
        this.components = components;
        this.stopwatch = new Stopwatch().start();
    }
    
    
    public List<ComponentText> getComponents(){
        return components;
    }
    
    public double getSeconds(){
        return stopwatch.getSeconds();
    }
    
}
