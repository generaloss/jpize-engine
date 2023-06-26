package pize.tests.voxelgame.base.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;
import pize.tests.voxelgame.server.net.ServerLoginPacketHandler;

import java.io.IOException;

public class SBPacketEncryptEnd extends IPacket<ServerLoginPacketHandler>{
    
    public static final int PACKET_ID = 4;
    
    public SBPacketEncryptEnd(){
        super(PACKET_ID);
    }
    
    
    public byte[] encryptedClientKey;
    
    public SBPacketEncryptEnd(byte[] encryptedClientKey){
        this();
        this.encryptedClientKey = encryptedClientKey;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeByteArray(encryptedClientKey);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        encryptedClientKey = stream.readByteArray();
    }
    
    @Override
    public void handle(ServerLoginPacketHandler packetHandler){
        packetHandler.handleEncryptEnd(this);
    }
    
}
