package jpize.tests.mcose.main.net.packet.serverbound;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.mcose.server.net.PlayerGameConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class SBPacketPing extends IPacket<PlayerGameConnection>{
    
    public static final int PACKET_ID = 7;
    
    public SBPacketPing(){
        super(PACKET_ID);
    }
    
    
    public long timeNanos;
    
    public SBPacketPing(long timeNanos){
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
    public void handle(PlayerGameConnection handler){
        handler.ping(this);
    }
    
}
