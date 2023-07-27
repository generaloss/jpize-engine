package pize.tests.minecraftosp.main.net.packet;

import pize.net.tcp.packet.IPacket;
import pize.tests.minecraftosp.server.net.ServerLoginPacketHandler;
import pize.util.io.PizeInputStream;
import pize.util.io.PizeOutputStream;

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
    protected void write(PizeOutputStream stream) throws IOException{
        stream.writeUTF(accountSessionToken);
    }
    
    @Override
    public void read(PizeInputStream stream) throws IOException{
        accountSessionToken = stream.readUTF();
    }
    
    @Override
    public void handle(ServerLoginPacketHandler packetListener){
        packetListener.handleAuth(this);
    }
    
}
