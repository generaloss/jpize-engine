package pize.tests.voxelgame.main.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;
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
    protected void write(PizeOutputStream stream) throws IOException{
        stream.writeByteArray(encryptedClientKey);
    }
    
    @Override
    public void read(PizeInputStream stream) throws IOException{
        encryptedClientKey = stream.readByteArray();
    }
    
    @Override
    public void handle(ServerLoginPacketHandler packetHandler){
        packetHandler.handleEncryptEnd(this);
    }
    
}
