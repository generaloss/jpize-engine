package jpize.tests.minecraftose.main.net.packet.clientbound;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.minecraftose.client.net.ClientConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class CBPacketDisconnect extends IPacket<ClientConnection>{
    
    public static final int PACKET_ID = 3;
    
    public CBPacketDisconnect(){
        super(PACKET_ID);
    }
    
    
    public String reasonComponent;
    
    public CBPacketDisconnect(String reasonComponent){
        this();
        this.reasonComponent = reasonComponent;
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeUTF(reasonComponent);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        reasonComponent = stream.readUTF();
    }

    @Override
    public void handle(ClientConnection handler){
        handler.disconnect(this);
    }
    
}
