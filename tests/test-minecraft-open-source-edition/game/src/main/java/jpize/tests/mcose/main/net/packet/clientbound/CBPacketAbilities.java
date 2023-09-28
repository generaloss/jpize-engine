package jpize.tests.mcose.main.net.packet.clientbound;

import jpize.net.tcp.packet.IPacket;
import jpize.tests.mcose.client.net.ClientPacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class CBPacketAbilities extends IPacket<ClientPacketHandler>{
    
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
    public void handle(ClientPacketHandler handler){
        handler.abilities(this);
    }
    
}
