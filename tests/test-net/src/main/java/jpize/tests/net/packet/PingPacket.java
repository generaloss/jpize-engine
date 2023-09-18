package jpize.tests.net.packet;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.net.handler.MyPacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class PingPacket extends IPacket<MyPacketHandler>{
    
    public static final int PACKET_ID = 2;
    
    public PingPacket(){
        super(PACKET_ID);
    }
    
    
    private long time;
    
    public PingPacket(long time){
        this();
        this.time = time;
    }
    
    public long getTime(){
        return time;
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeLong(time);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        time = stream.readLong();
    }

    @Override
    public void handle(MyPacketHandler handler){
        handler.ping(this);
    }
    
}
