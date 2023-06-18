package pize.tests.voxelgame.server.player;

import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.TcpConnection;
import pize.net.tcp.packet.IPacket;
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
    private final Map<String, ServerPlayer> playerMap;
    
    public PlayerList(Server server){
        this.server = server;
        playerMap = new HashMap<>();
    }
    
    public Server getServer(){
        return server;
    }
    
    
    public Collection<ServerPlayer> getPlayers(){
        return playerMap.values();
    }
    
    public boolean isPlayerOnline(String name){
        return playerMap.containsKey(name);
    }
    
    public ServerPlayer getPlayer(String name){
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
        final ServerPlayer serverPlayer = new ServerPlayer(level, name, connection);
        server.getConnectionManager().setPacketHandler(connection, serverPlayer.getConnectionAdapter());
        serverPlayer.teleport(level, spawnPosition);
        
        playerMap.put(name, serverPlayer);
        
        // Send packets to player
        final PlayerConnectionAdapter connectionAdapter = serverPlayer.getConnectionAdapter();
        
        new CBPacketSpawnInfo(level.getName(), spawnPosition).write(connection); // spawn init info
        
        for(ServerPlayer anotherPlayer: playerMap.values())
            if(anotherPlayer != serverPlayer)
                connectionAdapter.sendPacket(new CBPacketSpawnPlayer(anotherPlayer)); // all players info
        
        // Load chunks for player
        level.addEntity(serverPlayer);
        level.getChunkManager().loadInitChunkForPlayer(serverPlayer);

        // Send to all player-connection-event packet
        broadcastToAllExceptPlayer(new CBPacketSpawnPlayer(serverPlayer), serverPlayer);
    }
    
    
    public void disconnectPlayer(ServerPlayer player){
        broadcastToAllExceptPlayer(new CBPacketRemoveEntity(player), player); // Remove player entity on client
        player.getLevel().removeEntity(player); // Remove entity on server
        PlayerIO.save(player);                  // Save
        
        playerMap.remove(player.getName());
    }
    
    
    public OfflinePlayer getOfflinePlayer(String name){ //: NICHE TAK, HARAM
        return null;
    }
    
    
    public void broadcastToAll(IPacket<?> packet){
        for(ServerPlayer player: playerMap.values())
            player.sendPacket(packet);
    }

    public void broadcastToAllExceptPlayer(IPacket<?> packet, ServerPlayer except){
        for(ServerPlayer player: playerMap.values())
            if(player != except)
                player.sendPacket(packet);
    }
    
}
