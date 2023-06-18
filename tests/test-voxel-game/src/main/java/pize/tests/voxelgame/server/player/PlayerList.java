package pize.tests.voxelgame.server.player;

import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.TcpConnection;
import pize.net.tcp.packet.IPacket;
import pize.tests.voxelgame.clientserver.net.packet.CBPacketChatMessage;
import pize.tests.voxelgame.clientserver.net.packet.CBPacketRemoveEntity;
import pize.tests.voxelgame.clientserver.net.packet.CBPacketSpawnInfo;
import pize.tests.voxelgame.clientserver.net.packet.CBPacketSpawnPlayer;
import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.server.level.ServerLevel;
import pize.tests.voxelgame.server.net.PlayerConnectionAdapter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PlayerList{
    
    private final Server server;
    private final Map<String, Entity> playerMap;
    
    public PlayerList(Server server){
        this.server = server;
        playerMap = new HashMap<>();
    }
    
    public Server getServer(){
        return server;
    }
    
    
    public Collection<Entity> getPlayers(){
        return playerMap.values();
    }
    
    public boolean isPlayerOnline(String name){
        return playerMap.containsKey(name);
    }
    
    public Entity getPlayer(String name){
        return playerMap.get(name);
    }
    
    
    public void addNewPlayer(String name, TcpConnection connection){
        // Get level & Spawn position
        final ServerLevel level;
        final Vec3f spawnPosition;
        
        final OfflinePlayer offlinePlayer = getOfflinePlayer(name);
        if(offlinePlayer != null){
            
            final String levelName = offlinePlayer.getLevelName();
            server.getLevelManager().loadLevel(levelName);
            level = server.getLevelManager().getLevel(levelName);
            spawnPosition = offlinePlayer.getPosition();
        }else{
            
            final String levelName = server.getConfiguration().getDefaultLevelName();
            server.getLevelManager().loadLevel(levelName);
            level = server.getLevelManager().getLevel(levelName);
            spawnPosition = level.getSpawnPosition();
        }
        
        // Add ServerPlayer to list
        final Entity entity = new Entity(level, connection, name);
        server.getConnectionManager().setPacketHandler(connection, entity.getConnectionAdapter());
        entity.teleport(level, spawnPosition);
        
        playerMap.put(name, entity);
        
        // Send packets to player
        final PlayerConnectionAdapter connectionAdapter = entity.getConnectionAdapter();
        
        new CBPacketSpawnInfo(level.getName(), spawnPosition).write(connection); // spawn init info
        
        for(Entity anotherPlayer: playerMap.values())
            if(anotherPlayer != entity)
                connectionAdapter.sendPacket(new CBPacketSpawnPlayer(anotherPlayer)); // all players info
        
        // Load chunks for player
        level.addEntity(entity);
        level.getChunkManager().loadInitChunkForPlayer(entity);

        // Send to all player-connection-event packet
        broadcastToAllExceptPlayer(new CBPacketSpawnPlayer(entity), entity);
        
        broadcastMessage("Player " + name + " joined the game");
    }
    
    
    public void disconnectPlayer(Entity player){
        broadcastToAllExceptPlayer(new CBPacketRemoveEntity(player), player); // Remove player entity on client
        player.getLevel().removeEntity(player); // Remove entity on server
        PlayerIO.save(player);                  // Save
        
        playerMap.remove(player.getName());
    }
    
    
    public OfflinePlayer getOfflinePlayer(String name){ //: NICHE TAK, HARAM
        return null;
    }
    
    
    public void broadcastToAll(IPacket<?> packet){
        for(Entity player: playerMap.values())
            player.sendPacket(packet);
    }

    public void broadcastToAllExceptPlayer(IPacket<?> packet, Entity except){
        for(Entity player: playerMap.values())
            if(player != except)
                player.sendPacket(packet);
    }
    
    public void broadcastMessage(String message){
        broadcastToAll(new CBPacketChatMessage(message));
        System.out.println(message);
    }
    
}
