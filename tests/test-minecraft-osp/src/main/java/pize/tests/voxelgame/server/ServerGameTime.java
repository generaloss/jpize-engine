package pize.tests.voxelgame.server;

import pize.tests.voxelgame.main.time.GameTime;
import pize.tests.voxelgame.main.net.packet.CBPacketTime;

public class ServerGameTime extends GameTime{
    
    private final Server server;
    
    public ServerGameTime(Server server){
        this.server = server;
    }
    
    public Server getServer(){
        return server;
    }
    
    
    @Override
    public void setTicks(long ticks){
        super.setTicks(ticks);
        server.getPlayerList().broadcastPacket(new CBPacketTime(ticks));
    }
    
}
