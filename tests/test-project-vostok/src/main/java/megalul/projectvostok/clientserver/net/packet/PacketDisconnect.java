package megalul.projectvostok.clientserver.net.packet;

import megalul.projectvostok.clientserver.net.Packet;
import megalul.projectvostok.clientserver.net.PacketHandler;

public class PacketDisconnect implements Packet{
    
    public final String reasonComponent;
    
    /**
     * @param reasonComponent reasonComponent
     */
    public PacketDisconnect(String reasonComponent){
        this.reasonComponent = reasonComponent;
    }
    
    @Override
    public void handle(PacketHandler handler){
        handler.onDisconnect(this);
    }
    
}
