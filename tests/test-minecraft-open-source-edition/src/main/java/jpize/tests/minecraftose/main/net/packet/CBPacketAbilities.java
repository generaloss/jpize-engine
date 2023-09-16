package jpize.tests.minecraftose.main.net.packet;

import jpize.net.tcp.packet.IPacket;
import jpize.net.tcp.packet.PacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

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
    protected void write(JpizeOutputStream stream) throws IOException{
        stream.writeBoolean(flyEnabled);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        flyEnabled = stream.readBoolean();
    }
    
}
