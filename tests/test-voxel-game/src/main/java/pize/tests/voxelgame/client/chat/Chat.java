package pize.tests.voxelgame.client.chat;

import pize.tests.voxelgame.client.ClientGame;
import pize.tests.voxelgame.clientserver.net.packet.SBPacketChatMessage;
import pize.util.io.TextProcessor;

import java.util.ArrayDeque;
import java.util.Collection;

public class Chat{
    
    private final ClientGame game;
    private final ArrayDeque<ChatMessage> messageList;
    private boolean opened;
    private final TextProcessor textProcessor;
    
    public Chat(ClientGame game){
        this.game = game;
        this.messageList = new ArrayDeque<>();
        this.textProcessor = new TextProcessor(false);
        
        textProcessor.setActive(false);
    }
    
    
    public Collection<ChatMessage> getMessages(){
        return messageList;
    }
    
    public void putMessage(String message){
        messageList.addFirst(new ChatMessage(message));
    }
    
    public void clear(){
        messageList.clear();
    }
    
    
    public String getEnteringText(){
        return textProcessor.toString();
    }
    
    public void enter(){
        game.sendPacket(new SBPacketChatMessage(textProcessor.toString()));
        textProcessor.clear();
    }
    
    
    public boolean isOpened(){
        return opened;
    }
    
    private void setOpened(boolean opened){
        this.opened = opened;
        textProcessor.setActive(opened);
        game.getSession().getController().getPlayerController().getRotationController().showMouse(opened);
    }
    
    public void close(){
        game.getSession().getController().getPlayerController().getRotationController().lockNextFrame();
        setOpened(false);
    }
    
    public void open(){
        setOpened(true);
    }
    
    public void openAsCommandLine(){
        open();
        textProcessor.insertChar('/');
    }
    
}
