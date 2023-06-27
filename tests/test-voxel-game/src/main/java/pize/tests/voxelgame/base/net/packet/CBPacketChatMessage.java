package pize.tests.voxelgame.base.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;
import pize.tests.voxelgame.base.text.ComponentText;
import pize.tests.voxelgame.base.text.TextStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CBPacketChatMessage extends IPacket<PacketHandler>{
    
    public static final int PACKET_ID = 18;
    
    public CBPacketChatMessage(){
        super(PACKET_ID);
    }
    
    
    public List<ComponentText> components;
    
    public CBPacketChatMessage(List<ComponentText> components){
        this();
        this.components = components;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeShort(components.size());
        
        for(ComponentText component: components)
            writeComponent(stream, component);
    }
    
    private void writeComponent(PacketOutputStream stream, ComponentText component) throws IOException{
        stream.writeByte(component.getStyle().getData());
        stream.writeColor(component.getColor());
        stream.writeUTF(component.getText());
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        components = new ArrayList<>();
        final short componentsNum = stream.readShort();
        
        for(int i = 0; i < componentsNum; i++)
            readComponent(stream);
    }
    
    private void readComponent(PacketInputStream stream) throws IOException{
        components.add(
            new ComponentText(
                new TextStyle(stream.readByte()),
                stream.readColor(),
                stream.readUTF()
            )
        );
    }
    
}