package megalul.projectvostok.clientserver.net.packet;

import megalul.projectvostok.clientserver.net.Packet;
import megalul.projectvostok.clientserver.net.PacketHandler;

public class PacketLogin implements Packet{
    
    public final int clientVersionID;
    public final String profileName;
    
    /**
     * @param clientVersionID clientVersionID
     * @param profileName profileName
     */
    public PacketLogin(int clientVersionID, String profileName){
        this.clientVersionID = clientVersionID;
        this.profileName = profileName;
    }
    
    @Override
    public void handle(PacketHandler handler){
        handler.onLogin(this);
    }
    
}
