package jpize.tests.mcose.main.net.packet.clientbound;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.mcose.client.net.ClientPacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class CBPacketPong extends IPacket<ClientPacketHandler>{
    
    public static final int PACKET_ID = 7;
    
    public CBPacketPong(){
        super(PACKET_ID);
    }
    
    
    public long timeNanos;
    
    public CBPacketPong(long timeNanos){
        this();
        this.timeNanos = timeNanos;
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeLong(timeNanos);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        timeNanos = stream.readLong();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.pong(this);
    }
    
}

