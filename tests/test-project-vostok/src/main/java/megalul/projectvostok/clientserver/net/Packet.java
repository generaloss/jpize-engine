package megalul.projectvostok.clientserver.net;

import java.io.Serializable;

public interface Packet extends Serializable{
    
    void handle(PacketHandler handler);
    
}
