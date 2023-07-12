package pize.tests.voxelgame.main.chat;

public class MessageSource{
    
    private final MessageSources source;
    
    public MessageSource(MessageSources source){
        this.source = source;
    }
    
    public MessageSources getSource(){
        return source;
    }
    
    
    public boolean isPlayer(){
        return source == MessageSources.PLAYER;
    }
    
    public boolean isServer(){
        return source == MessageSources.SERVER;
    }
    
    
    public MessageSourcePlayer asPlayer(){
        return (MessageSourcePlayer) this;
    }
    
}
