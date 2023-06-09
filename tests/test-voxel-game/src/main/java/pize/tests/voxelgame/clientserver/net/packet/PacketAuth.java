package pize.tests.voxelgame.clientserver.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class PacketAuth extends IPacket{
    
    public static final int PACKET_ID = 1;
    
    public PacketAuth(){
        super(PACKET_ID);
    }
    
    
    public String profileName;
    public String accountSessionToken;
    
    public PacketAuth(String profileName, String accountSessionToken){
        this();
        this.profileName = profileName;
        this.accountSessionToken = accountSessionToken;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeUTF(profileName);
        stream.writeUTF(accountSessionToken);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        profileName = stream.readUTF();
        accountSessionToken = stream.readUTF();
    }
    
}
