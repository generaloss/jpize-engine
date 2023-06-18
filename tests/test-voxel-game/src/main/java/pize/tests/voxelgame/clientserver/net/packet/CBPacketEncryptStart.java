package pize.tests.voxelgame.clientserver.net.packet;

import pize.net.security.PublicRSA;
import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketHandler;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

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
    protected void write(PacketOutputStream stream) throws IOException{
        stream.write(publicServerKey.getKey().getEncoded());
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        publicServerKey = new PublicRSA(stream.readAllBytes());
    }
    
}
