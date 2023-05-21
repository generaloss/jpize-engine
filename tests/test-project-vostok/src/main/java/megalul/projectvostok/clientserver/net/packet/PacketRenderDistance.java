package megalul.projectvostok.clientserver.net.packet;

import megalul.projectvostok.clientserver.net.Packet;
import megalul.projectvostok.clientserver.net.PacketHandler;

public class PacketRenderDistance implements Packet{
    
    public final String playerName;
    public final int renderDistance;
    
    /**
     * @param playerName playerName
     * @param renderDistance renderDistance
     */
    public PacketRenderDistance(String playerName, int renderDistance){
        this.playerName = playerName;
        this.renderDistance = renderDistance;
    }
    
    @Override
    public void handle(PacketHandler handler){
        handler.onRenderDistance(this);
    }
    
}
