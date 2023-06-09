package pize.tests.voxelgame.clientserver.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;

import java.io.IOException;

public class PacketLogin extends IPacket{
    
    public static final int PACKET_ID = 6;
    
    public PacketLogin(){
        super(PACKET_ID);
    }
    
    
    public int clientVersionID;
    public String profileName;
    
    public PacketLogin(int clientVersionID, String profileName){
        this();
        this.clientVersionID = clientVersionID;
        this.profileName = profileName;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeInt(clientVersionID);
        stream.writeUTF(profileName);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        clientVersionID = stream.readInt();
        profileName = stream.readUTF();
    }
    
}
