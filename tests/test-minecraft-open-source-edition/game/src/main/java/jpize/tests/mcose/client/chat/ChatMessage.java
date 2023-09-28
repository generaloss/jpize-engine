package jpize.tests.mcose.client.chat;

import jpize.tests.mcose.main.chat.MessageSource;
import jpize.tests.mcose.main.text.ComponentText;
import jpize.util.time.Stopwatch;

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
