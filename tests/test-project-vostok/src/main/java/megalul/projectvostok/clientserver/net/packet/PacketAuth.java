package megalul.projectvostok.clientserver.net.packet;

import megalul.projectvostok.clientserver.net.Packet;
import megalul.projectvostok.clientserver.net.PacketHandler;

public class PacketAuth implements Packet{
    
    public final String profileName;
    public final String accountSessionToken;
    
    /**
     * @param profileName profileName
     * @param accountSessionToken accountSessionToken
     */
    public PacketAuth(String profileName, String accountSessionToken){
        this.profileName = profileName;
        this.accountSessionToken = accountSessionToken;
    }
    
    @Override
    public void handle(PacketHandler handler){
        handler.onAuth(this);
    }
    
}
