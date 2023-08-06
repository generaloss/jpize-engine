package pize.tests.minecraftosp.main.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.tests.minecraftosp.server.net.PlayerLoginConnection;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;

import java.io.IOException;

public class SBPacketEncryptEnd extends IPacket<PlayerLoginConnection>{
    
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
    public void handle(PlayerLoginConnection packetHandler){
        packetHandler.handleEncryptEnd(this);
    }
    
}
