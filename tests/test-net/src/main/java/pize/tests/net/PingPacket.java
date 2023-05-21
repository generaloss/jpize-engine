package pize.tests.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
    protected void write(DataOutputStream stream) throws IOException{
        stream.writeLong(time);
    }
    
    @Override
    public void read(DataInputStream stream) throws IOException{
        time = stream.readLong();
    }
}
