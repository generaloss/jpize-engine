package jpize.tests.minecraftose.server.player;

import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.TcpConnection;
import jpize.net.tcp.packet.IPacket;
import jpize.tests.minecraftose.main.audio.Sound;
import jpize.tests.minecraftose.main.entity.Entity;
import jpize.tests.minecraftose.main.entity.Player;
import jpize.tests.minecraftose.main.level.Level;
import jpize.tests.minecraftose.main.net.packet.clientbound.CBPacketAbilities;
import jpize.tests.minecraftose.main.net.packet.clientbound.CBPacketChatMessage;
import jpize.tests.minecraftose.main.net.packet.clientbound.CBPacketPlaySound;
import jpize.tests.minecraftose.main.net.packet.clientbound.CBPacketTeleportPlayer;
import jpize.tests.minecraftose.main.text.Component;
import jpize.tests.minecraftose.server.Server;
import jpize.tests.minecraftose.server.level.ServerLevel;
import jpize.tests.minecraftose.server.net.PlayerGameConnection;

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
