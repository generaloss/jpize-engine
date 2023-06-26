package pize.tests.voxelgame.base.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class CBPacketAbilities extends IPacket<PacketHandler>{
    
    public static final int PACKET_ID = 21;
    
    public CBPacketAbilities(){
        super(PACKET_ID);
    }
    
    
    public boolean flyEnabled;
    
    public CBPacketAbilities(boolean flyEnabled){
        this();
        this.flyEnabled = flyEnabled;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeBoolean(flyEnabled);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        flyEnabled = stream.readBoolean();
    }
    
}
