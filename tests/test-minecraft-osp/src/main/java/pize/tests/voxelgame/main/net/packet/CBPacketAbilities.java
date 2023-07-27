package pize.tests.voxelgame.main.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;

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
    protected void write(PizeOutputStream stream) throws IOException{
        stream.writeBoolean(flyEnabled);
    }
    
    @Override
    public void read(PizeInputStream stream) throws IOException{
        flyEnabled = stream.readBoolean();
    }
    
}
