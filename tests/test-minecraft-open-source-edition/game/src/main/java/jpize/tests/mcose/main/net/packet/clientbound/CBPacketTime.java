package jpize.tests.mcose.main.net.packet.clientbound;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.mcose.client.net.ClientPacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class CBPacketTime extends IPacket<ClientPacketHandler>{
    
    public static final int PACKET_ID = 22;
    
    public CBPacketTime(){
        super(PACKET_ID);
    }
    
    
    public long gameTimeTicks;
    
    public CBPacketTime(long gameTimeTicks){
        this();
        this.gameTimeTicks = gameTimeTicks;
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeLong(gameTimeTicks);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        gameTimeTicks = stream.readLong();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.time(this);
    }
    
}