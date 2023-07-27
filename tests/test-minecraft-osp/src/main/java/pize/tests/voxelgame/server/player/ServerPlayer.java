package pize.tests.voxelgame.server.player;

import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.TcpConnection;
import pize.net.tcp.packet.IPacket;
import pize.tests.voxelgame.main.audio.Sound;
import pize.tests.voxelgame.main.entity.Entity;
import pize.tests.voxelgame.main.entity.Player;
import pize.tests.voxelgame.main.level.Level;
import pize.tests.voxelgame.main.net.packet.CBPacketAbilities;
import pize.tests.voxelgame.main.net.packet.CBPacketChatMessage;
import pize.tests.voxelgame.main.net.packet.CBPacketPlaySound;
import pize.tests.voxelgame.main.net.packet.CBPacketTeleportPlayer;
import pize.tests.voxelgame.main.text.Component;
import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.server.level.ServerLevel;
import pize.tests.voxelgame.server.net.PlayerGameConnection;

public class ServerPlayer extends Player{
    
    private final Server server;
    private final PlayerGameConnection connectionAdapter;
    
    private int renderDistance;

    public ServerPlayer(ServerLevel level, TcpConnection connection, String name){
        super(level, name);
        this.server = level.getServer();
        this.connectionAdapter = new PlayerGameConnection(this, connection);
        this.renderDistance = server.getConfiguration().getMaxRenderDistance(); //: 0
    }
    
    public Server getServer(){
        return server;
    }

    @Override
    public ServerLevel getLevel(){
        return (ServerLevel) super.getLevel();
    }

    public void playSound(Sound sound, float volume, float pitch){
        sendPacket(new CBPacketPlaySound(sound, volume, pitch, this.getPosition()));
    }
    
    
    public void teleport(Level level, Vec3f position, EulerAngles rotation){
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
    
    public void teleport(Vec3f position, EulerAngles rotation){
        teleport(getLevel(), position, rotation);
    }
    
    public void teleport(Level level, Vec3f position){
        teleport(level, position, getRotation());
    }
    
    public void teleport(Vec3f position){
        teleport(getLevel(), position, getRotation());
    }
    
    
    public void setFlyEnabled(boolean flyEnabled){
        sendPacket(new CBPacketAbilities(flyEnabled));
        super.setFlyEnabled(flyEnabled);
    }


    public void disconnect(){
        getConnectionAdapter().getConnection().close();
    }
    
    public void sendToChat(Component message){
        server.getPlayerList().broadcastPlayerMessage(this, message);
    }
    
    public void sendMessage(Component message){
        sendPacket(new CBPacketChatMessage(message.toFlatList()));
    }
    
    
    public PlayerGameConnection getConnectionAdapter(){
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
