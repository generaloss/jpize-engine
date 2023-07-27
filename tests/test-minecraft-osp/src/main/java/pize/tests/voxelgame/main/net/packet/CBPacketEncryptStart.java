package pize.tests.voxelgame.main.net.packet;

import pize.net.security.PublicRSA;
import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;

import java.io.IOException;

public class CBPacketEncryptStart extends IPacket<PacketHandler>{
    
    public static final int PACKET_ID = 5;
    
    public CBPacketEncryptStart(){
        super(PACKET_ID);
    }
    
    
    public PublicRSA publicServerKey;
    
    public CBPacketEncryptStart(PublicRSA publicServerKey){
        this();
        this.publicServerKey = publicServerKey;
    }
    
    
    @Override
    protected void write(PizeOutputStream stream) throws IOException{
        stream.write(publicServerKey.getKey().getEncoded());
    }
    
    @Override
    public void read(PizeInputStream stream) throws IOException{
        publicServerKey = new PublicRSA(stream.readAllBytes());
    }
    
}

