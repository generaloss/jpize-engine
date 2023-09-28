package jpize.tests.mcose.main.net.packet.clientbound;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.mcose.client.net.ClientPacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class CBPacketDisconnect extends IPacket<ClientPacketHandler>{
    
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
    public void handle(ClientPacketHandler handler){
        handler.disconnect(this);
    }
    
}
