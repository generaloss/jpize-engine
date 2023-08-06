package pize.tests.minecraftosp.main.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.tests.minecraftosp.server.net.PlayerLoginConnection;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;

import java.io.IOException;

public class SBPacketLogin extends IPacket<PlayerLoginConnection>{
    
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
    protected void write(PizeOutputStream stream) throws IOException{
        stream.writeInt(clientVersionID);
        stream.writeUTF(profileName);
    }
    
    @Override
    public void read(PizeInputStream stream) throws IOException{
        clientVersionID = stream.readInt();
        profileName = stream.readUTF();
    }
    
    @Override
    public void handle(PlayerLoginConnection packetHandler){
        packetHandler.handleLogin(this);
    }
    
}
