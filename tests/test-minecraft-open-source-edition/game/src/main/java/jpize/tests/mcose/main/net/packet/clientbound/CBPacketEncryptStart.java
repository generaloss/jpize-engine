package jpize.tests.mcose.main.net.packet.clientbound;

import jpize.net.security.PublicRSA;
import jpize.net.tcp.packet.IPacket;
import jpize.tests.mcose.client.net.ClientPacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class CBPacketEncryptStart extends IPacket<ClientPacketHandler>{
    
    public static final int PACKET_ID = 5;
    
    public CBPacketEncryptStart(){
        super(PACKET_ID);
    }
    
    
    public PublicRSA publicServerKey;
    
    public CBPacketEncryptStart(PublicRSA publicServerKey){
        this();
        this.publicServerKey = publicServerKey;
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.write(publicServerKey.getKey().getEncoded());
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        publicServerKey = new PublicRSA(stream.readAllBytes());
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.encryptStart(this);
    }
    
}

