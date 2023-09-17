package jpize.tests.minecraftose.main.net.packet.clientbound;

import jpize.net.security.PublicRSA;
import jpize.net.tcp.packet.IPacket;
import jpize.tests.minecraftose.client.net.ClientConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class CBPacketEncryptStart extends IPacket<ClientConnection>{
    
    public static final byte PACKET_ID = 5;
    
    public CBPacketEncryptStart(){
        super(PACKET_ID);
    }
    
    
    public PublicRSA publicServerKey;
    
    public CBPacketEncryptStart(PublicRSA publicServerKey){
        this();
        this.publicServerKey = publicServerKey;
    }
    
    
    @Override
    protected void write(JpizeOutputStream stream) throws IOException{
        stream.write(publicServerKey.getKey().getEncoded());
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        publicServerKey = new PublicRSA(stream.readAllBytes());
    }

    @Override
    public void handle(ClientConnection handler){
        handler.encryptStart(this);
    }
    
}

