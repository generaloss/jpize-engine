package pize.tests.voxelgame.clientserver.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class PacketPlayerBlockSet extends IPacket{
    
    public static final int PACKET_ID = 11;
    
    public PacketPlayerBlockSet(){
        super(PACKET_ID);
    }
    
    public String playerName;
    public int x, y, z;
    public short state;
    
    public PacketPlayerBlockSet(String playerName, int x, int y, int z, short state){
        this();
        
        this.playerName = playerName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.state = state;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeUTF(playerName);
        stream.writeInt(x);
        stream.writeInt(y);
        stream.writeInt(z);
        stream.writeShort(state);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        playerName = stream.readUTF();
        x = stream.readInt();
        y = stream.readInt();
        z = stream.readInt();
        state = stream.readShort();
    }
    
}
