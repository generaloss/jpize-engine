package pize.tests.voxelgame.server.player;

import pize.net.tcp.TcpConnection;
import pize.net.tcp.packet.IPacket;
import pize.tests.voxelgame.clientserver.entity.Player;
import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.server.level.ServerLevel;
import pize.tests.voxelgame.server.net.PlayerConnectionAdapter;

public class ServerPlayer extends Player{
    
    private final Server server;
    private final PlayerConnectionAdapter connectionAdapter;
    
    private int renderDistance;

    public ServerPlayer(ServerLevel level, TcpConnection connection, String name){
        super(level, name);
        this.server = level.getServer();
        this.connectionAdapter = new PlayerConnectionAdapter(this, connection);
        this.renderDistance = server.getConfiguration().getMaxRenderDistance(); //: 0
    }
    
    public Server getServer(){
        return server;
    }
    
    public PlayerConnectionAdapter getConnectionAdapter(){
        return connectionAdapter;
    }
    
    public void sendPacket(IPacket<?> packet){
        connectionAdapter.sendPacket(packet);
    }
    
    public int getRenderDistance(){
        return renderDistance;
    }
    
    public void setRenderDistance(int renderDistance){
        this.renderDistance = renderDistance;
    }
    
}
