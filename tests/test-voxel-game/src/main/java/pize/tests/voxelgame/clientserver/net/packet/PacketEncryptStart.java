package pize.tests.voxelgame.clientserver.net.packet;

import pize.net.security.PublicRSA;
import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class PacketEncryptStart extends IPacket{
    
    public static final int PACKET_ID = 5;
    
    public PacketEncryptStart(){
        super(PACKET_ID);
    }
    
    
    public PublicRSA publicServerKey;
    
    public PacketEncryptStart(PublicRSA publicServerKey){
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

