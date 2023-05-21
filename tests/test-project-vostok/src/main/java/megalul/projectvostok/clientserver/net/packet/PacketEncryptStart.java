package megalul.projectvostok.clientserver.net.packet;

import megalul.projectvostok.clientserver.net.Packet;
import megalul.projectvostok.clientserver.net.PacketHandler;

import java.security.Key;

public class PacketEncryptStart implements Packet{
    
    public final Key publicServerKey;
    
    /**
     * @param publicServerKey publicServerKey
     */
    public PacketEncryptStart(Key publicServerKey){
        this.publicServerKey = publicServerKey;
    }
    
    @Override
    public void handle(PacketHandler handler){
        handler.onEncryptStart(this);
    }
    
}

