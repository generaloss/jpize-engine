package jpize.tests.minecraftose.main.net.packet.serverbound;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.minecraftose.server.net.PlayerLoginConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class SBPacketEncryptEnd extends IPacket<PlayerLoginConnection>{
    
    public static final byte PACKET_ID = 4;
    
    public SBPacketEncryptEnd(){
        super(PACKET_ID);
    }
    
    
    public byte[] encryptedClientKey;
    
    public SBPacketEncryptEnd(byte[] encryptedClientKey){
        this();
        this.encryptedClientKey = encryptedClientKey;
    }
    
    
    @Override
    protected void write(JpizeOutputStream stream) throws IOException{
        stream.writeByteArray(encryptedClientKey);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        encryptedClientKey = stream.readByteArray();
    }
    
    @Override
    public void handle(PlayerLoginConnection handler){
        handler.encryptEnd(this);
    }
    
}
