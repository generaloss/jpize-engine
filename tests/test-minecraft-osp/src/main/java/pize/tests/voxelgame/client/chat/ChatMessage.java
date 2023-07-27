package pize.tests.voxelgame.client.chat;

import pize.tests.voxelgame.main.chat.MessageSource;
import pize.tests.voxelgame.main.text.ComponentText;
import pize.util.time.Stopwatch;

import java.util.List;

public class ChatMessage{
    
    private final MessageSource source;
    private final List<ComponentText> components;
    private final Stopwatch stopwatch;
    
    public ChatMessage(MessageSource source, List<ComponentText> components){
        this.source = source;
        this.components = components;
        this.stopwatch = new Stopwatch().start();
    }
    
    
    public MessageSource getSource(){
        return source;
    }
    
    public List<ComponentText> getComponents(){
        return components;
    }
    
    public double getSeconds(){
        return stopwatch.getSeconds();
    }
    
}
