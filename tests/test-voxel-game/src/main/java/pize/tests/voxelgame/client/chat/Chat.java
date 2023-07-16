package pize.tests.voxelgame.client.chat;

import pize.tests.voxelgame.client.ClientGame;
import pize.tests.voxelgame.main.chat.MessageSource;
import pize.tests.voxelgame.main.chat.MessageSourceOther;
import pize.tests.voxelgame.main.net.packet.CBPacketChatMessage;
import pize.tests.voxelgame.main.net.packet.SBPacketChatMessage;
import pize.tests.voxelgame.main.text.Component;
import pize.util.io.TextProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Chat{
    
    private final ClientGame game;
    private final CopyOnWriteArrayList<ChatMessage> messageList;
    private final TextProcessor textProcessor;
    private boolean opened;
    
    private final List<String> history;
    private int historyPointer = 0;
    
    public Chat(ClientGame game){
        this.game = game;
        this.messageList = new CopyOnWriteArrayList<>();
        this.history = new ArrayList<>();
        this.textProcessor = new TextProcessor(false);
        
        textProcessor.setActive(false);
        
        putMessage(new MessageSourceOther(), new Component().text("Enter /help for command list"));
    }
    
    
    public List<ChatMessage> getMessages(){
        return messageList;
    }
    
    public void putMessage(CBPacketChatMessage messagePacket){
        messageList.add(new ChatMessage(messagePacket.source, messagePacket.components));
    }
    
    public void putMessage(MessageSource source, Component component){
        messageList.add(new ChatMessage(source, component.toFlatList()));
    }
    
    
    public void clear(){
        messageList.clear();
    }
    
    
    public String getEnteringText(){
        return textProcessor.toString();
    }
    
    public void enter(){
        final String message = textProcessor.toString();
        game.sendPacket(new SBPacketChatMessage(message));
        textProcessor.clear();
        
        if(!history.isEmpty() && history.get(history.size() - 1).equals(message))
            return;
        
        history.add(history.size(), message);
        historyPointer = history.size() - 1;
    }
    
    public void historyUp(){
        if(historyPointer == history.size() - 1 && !history.get(history.size() - 1).equals(textProcessor.toString())){
            historyPointer++;
            if(!textProcessor.toString().isBlank())
                history.add(textProcessor.toString());
        }
        
        if(historyPointer - 1 < 0)
            return;
        
        historyPointer--;
        
        textProcessor.removeLine();
        textProcessor.insertLine(history.get(historyPointer));
    }
    
    public void historyDown(){
        if(historyPointer + 2 > history.size())
            return;
        
        historyPointer++;
        
        textProcessor.removeLine();
        textProcessor.insertLine(history.get(historyPointer));
    }
    
    public int getCursorX(){
        return textProcessor.getCursorX();
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
        historyPointer = history.size() - 1;
        textProcessor.removeLine();
        setOpened(false);
    }
    
    public void open(){
        setOpened(true);
    }
    
    public void openAsCommandLine(){
        open();
        textProcessor.insertChar('/');
    }
    
    
    public TextProcessor getTextProcessor(){
        return textProcessor;
    }
    
}
