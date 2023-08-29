package jpize.tests.net.packet;

import jpize.net.tcp.packet.IPacket;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class PingPacket extends IPacket{
    
    public static final int PACKET_TYPE_ID = 18;
    
    public PingPacket(){
        super(PACKET_TYPE_ID);
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
    protected void write(JpizeOutputStream stream) throws IOException{
        stream.writeLong(time);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        time = stream.readLong();
    }
    
}
