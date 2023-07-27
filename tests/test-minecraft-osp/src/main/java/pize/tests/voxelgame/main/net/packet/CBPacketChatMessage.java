package pize.tests.voxelgame.main.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;
import pize.tests.voxelgame.main.chat.MessageSource;
import pize.tests.voxelgame.main.chat.MessageSourcePlayer;
import pize.tests.voxelgame.main.chat.MessageSourceServer;
import pize.tests.voxelgame.main.chat.MessageSources;
import pize.tests.voxelgame.main.text.ComponentText;
import pize.tests.voxelgame.main.text.TextStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CBPacketChatMessage extends IPacket<PacketHandler>{
    
    public static final int PACKET_ID = 18;
    
    public CBPacketChatMessage(){
        super(PACKET_ID);
    }
    
    
    public MessageSource source;
    public String playerName;
    public List<ComponentText> components;
    
    private CBPacketChatMessage(MessageSource source, List<ComponentText> components){
        this();
        this.source = source;
        this.components = components;
    }
    
    public CBPacketChatMessage(List<ComponentText> components){
        this(new MessageSourceServer(), components);
    }
    
    public CBPacketChatMessage(String playerName, List<ComponentText> components){
        this(new MessageSourcePlayer(playerName), components);
        this.playerName = playerName;
    }
    
    
    @Override
    protected void write(PizeOutputStream stream) throws IOException{
        // Write source
        stream.writeByte(source.getSource().ordinal());
        if(source.getSource() == MessageSources.PLAYER)
            stream.writeUTF(playerName);
        
        // Write components
        stream.writeShort(components.size());
        for(ComponentText component: components)
            writeComponent(stream, component);
    }
    
    private void writeComponent(PizeOutputStream stream, ComponentText component) throws IOException{
        stream.writeByte(component.getStyle().getData());
        stream.writeColor(component.getColor());
        stream.writeUTF(component.getText());
    }
    
    @Override
    public void read(PizeInputStream stream) throws IOException{
        // Read source
        final MessageSources messageSource = MessageSources.values()[stream.readByte()];
        if(messageSource == MessageSources.PLAYER){
            playerName = stream.readUTF();
            source = new MessageSourcePlayer(playerName);
        }else
            source = new MessageSourceServer();
        
        // Read components
        components = new ArrayList<>();
        final short componentsNum = stream.readShort();
        
        for(int i = 0; i < componentsNum; i++)
            readComponent(stream);
    }
    
    private void readComponent(PizeInputStream stream) throws IOException{
        components.add(
            new ComponentText(
                new TextStyle(stream.readByte()),
                stream.readColor(),
                stream.readUTF()
            )
        );
    }
    
}