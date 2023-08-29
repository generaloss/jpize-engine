package jpize.tests.minecraftosp.main.net.packet;

import jpize.net.tcp.packet.IPacket;
import jpize.net.tcp.packet.PacketHandler;
import jpize.tests.minecraftosp.main.chat.MessageSource;
import jpize.tests.minecraftosp.main.chat.MessageSourcePlayer;
import jpize.tests.minecraftosp.main.chat.MessageSourceServer;
import jpize.tests.minecraftosp.main.chat.MessageSources;
import jpize.tests.minecraftosp.main.text.ComponentText;
import jpize.tests.minecraftosp.main.text.TextStyle;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

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
    protected void write(JpizeOutputStream stream) throws IOException{
        // Write source
        stream.writeByte(source.getSource().ordinal());
        if(source.getSource() == MessageSources.PLAYER)
            stream.writeUTF(playerName);
        
        // Write components
        stream.writeShort(components.size());
        for(ComponentText component: components)
            writeComponent(stream, component);
    }
    
    private void writeComponent(JpizeOutputStream stream, ComponentText component) throws IOException{
        stream.writeByte(component.getStyle().getData());
        stream.writeColor(component.getColor());
        stream.writeUTF(component.getText());
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
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
    
    private void readComponent(JpizeInputStream stream) throws IOException{
        components.add(
            new ComponentText(
                new TextStyle(stream.readByte()),
                stream.readColor(),
                stream.readUTF()
            )
        );
    }
    
}