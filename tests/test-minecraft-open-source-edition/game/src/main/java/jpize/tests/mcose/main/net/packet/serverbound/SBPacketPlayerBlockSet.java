package jpize.tests.mcose.main.net.packet.serverbound;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.mcose.server.net.PlayerGameConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class SBPacketPlayerBlockSet extends IPacket<PlayerGameConnection>{
    
    public static final int PACKET_ID = 11;
    
    public SBPacketPlayerBlockSet(){
        super(PACKET_ID);
    }
    
    public int x, y, z;
    public short blockData;
    
    public SBPacketPlayerBlockSet(int x, int y, int z, short blockData){
        this();
        
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockData = blockData;
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeInt(x);
        stream.writeInt(y);
        stream.writeInt(z);
        stream.writeShort(blockData);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        x = stream.readInt();
        y = stream.readInt();
        z = stream.readInt();
        blockData = stream.readShort();
    }
    
    @Override
    public void handle(PlayerGameConnection handler){
        handler.playerBlockSet(this);
    }
    
}
