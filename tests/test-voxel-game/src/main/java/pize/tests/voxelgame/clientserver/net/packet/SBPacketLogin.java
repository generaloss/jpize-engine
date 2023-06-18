package pize.tests.voxelgame.clientserver.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;
import pize.tests.voxelgame.server.net.ServerLoginPacketHandler;

import java.io.IOException;

public class SBPacketLogin extends IPacket<ServerLoginPacketHandler>{
    
    public static final int PACKET_ID = 6;
    
    public SBPacketLogin(){
        super(PACKET_ID);
    }
    
    
    public int clientVersionID;
    public String profileName;
    
    public SBPacketLogin(int clientVersionID, String profileName){
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
    
    @Override
    public void handle(ServerLoginPacketHandler packetHandler){
        packetHandler.handleLogin(this);
    }
    
}