package pize.tests.voxelgame.server.player;

import pize.math.util.EulerAngles;
import pize.math.vecmath.tuple.Tuple3f;
import pize.net.tcp.TcpConnection;
import pize.net.tcp.packet.IPacket;
import pize.tests.voxelgame.base.entity.Entity;
import pize.tests.voxelgame.base.entity.Player;
import pize.tests.voxelgame.base.level.Level;
import pize.tests.voxelgame.base.net.packet.CBPacketAbilities;
import pize.tests.voxelgame.base.net.packet.CBPacketChatMessage;
import pize.tests.voxelgame.base.net.packet.CBPacketTeleportPlayer;
import pize.tests.voxelgame.base.text.Component;
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
    
    
    public void teleport(Level level, Tuple3f position, EulerAngles rotation){
        sendPacket(new CBPacketTeleportPlayer(level.getConfiguration().getName(), position, rotation));
        
        final Level oldLevel = getLevel();
        if(level != oldLevel){
            setLevel(level);
            oldLevel.removeEntity(this);
            level.addEntity(this);
        }
        getPosition().set(position);
        getRotation().set(rotation);
    }
    
    public void teleport(Entity entity){
        teleport(entity.getLevel(), entity.getPosition(), entity.getRotation());
    }
    
    public void teleport(Tuple3f position, EulerAngles rotation){
        teleport(getLevel(), position, rotation);
    }
    
    public void teleport(Level level, Tuple3f position){
        teleport(level, position, getRotation());
    }
    
    public void teleport(Tuple3f position){
        teleport(getLevel(), position, getRotation());
    }
    
    
    public void setFlyEnabled(boolean flyEnabled){
        sendPacket(new CBPacketAbilities(flyEnabled));
        super.setFlyEnabled(flyEnabled);
    }
    
    
    public void sendMessage(Component message){
        sendPacket(new CBPacketChatMessage(message.toFlatList()));
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
