package megalul.projectvostok.clientserver.net.packet;

import megalul.projectvostok.clientserver.net.Packet;
import megalul.projectvostok.clientserver.net.PacketHandler;

public class PacketEncryptEnd implements Packet{
    
    public final String profileName;
    public final byte[] encryptedClientKey;
    
    /**
     * @param profileName profileName
     * @param encryptedClientKey encryptedClientKey
     */
    public PacketEncryptEnd(String profileName, byte[] encryptedClientKey){
        this.profileName = profileName;
        this.encryptedClientKey = encryptedClientKey;
    }
    
    @Override
    public void handle(PacketHandler handler){
        handler.onEncryptEnd(this);
    }
    
}
