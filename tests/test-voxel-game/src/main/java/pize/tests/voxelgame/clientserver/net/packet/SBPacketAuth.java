package pize.tests.voxelgame.clientserver.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.net.tcp.packet.PacketInputStream;
import pize.net.tcp.packet.PacketOutputStream;
import pize.tests.voxelgame.server.net.ServerLoginPacketHandler;

import java.io.IOException;

public class SBPacketAuth extends IPacket<ServerLoginPacketHandler>{
    
    public static final int PACKET_ID = 1;
    
    public SBPacketAuth(){
        super(PACKET_ID);
    }
    
    
    public String accountSessionToken;
    
    public SBPacketAuth(String accountSessionToken){
        this();
        this.accountSessionToken = accountSessionToken;
    }
    
    
    @Override
    protected void write(PacketOutputStream stream) throws IOException{
        stream.writeUTF(accountSessionToken);
    }
    
    @Override
    public void read(PacketInputStream stream) throws IOException{
        accountSessionToken = stream.readUTF();
    }
    
    @Override
    public void handle(ServerLoginPacketHandler packetListener){
        packetListener.handleAuth(this);
    }
    
}