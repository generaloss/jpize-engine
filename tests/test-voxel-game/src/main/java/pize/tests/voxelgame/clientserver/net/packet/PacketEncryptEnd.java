package pize.tests.voxelgame.clientserver.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class PacketEncryptEnd extends IPacket{
    
    public static final int PACKET_ID = 4;
    
    public PacketEncryptEnd(){
        super(PACKET_ID);
    }
    
    
    public String profileName;
    public byte[] encryptedClientKey;
    
    public PacketEncryptEnd(String profileName, byte[] encryptedClientKey){
        this();
        this.profileName = profileName;
        this.encryptedClientKey = encryptedClientKey;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeUTF(profileName);
        stream.write(encryptedClientKey);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        profileName = stream.readUTF();
        encryptedClientKey = stream.readAllBytes();
    }
    
}
