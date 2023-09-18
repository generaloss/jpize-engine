package jpize.tests.minecraftose.main.net.packet.clientbound;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.minecraftose.client.net.ClientConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class CBPacketAbilities extends IPacket<ClientConnection>{
    
    public static final int PACKET_ID = 21;
    
    public CBPacketAbilities(){
        super(PACKET_ID);
    }
    
    
    public boolean flyEnabled;
    
    public CBPacketAbilities(boolean flyEnabled){
        this();
        this.flyEnabled = flyEnabled;
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeBoolean(flyEnabled);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        flyEnabled = stream.readBoolean();
    }

    @Override
    public void handle(ClientConnection handler){
        handler.abilities(this);
    }
    
}
