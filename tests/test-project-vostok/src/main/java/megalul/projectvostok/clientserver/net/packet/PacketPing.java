package megalul.projectvostok.clientserver.net.packet;

import megalul.projectvostok.clientserver.net.Packet;
import megalul.projectvostok.clientserver.net.PacketHandler;

public class PacketPing implements Packet{
    
    public final long timeMillis;
    
    /**
     * @param timeMillis timeMillis
     */
    public PacketPing(long timeMillis){
        this.timeMillis = timeMillis;
    }
    
    @Override
    public void handle(PacketHandler handler){
        handler.onPing(this);
    }
    
}
