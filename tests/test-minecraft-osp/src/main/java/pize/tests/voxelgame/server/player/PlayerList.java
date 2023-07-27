package pize.tests.voxelgame.server.player;

import pize.math.vecmath.vector.Vec3f;
import pize.net.tcp.TcpConnection;
import pize.net.tcp.packet.IPacket;
import pize.tests.voxelgame.main.net.packet.*;
import pize.tests.voxelgame.main.text.Component;
import pize.tests.voxelgame.main.text.TextColor;
import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.server.level.ServerLevel;
import pize.tests.voxelgame.server.net.PlayerGameConnection;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerList{
    
    private final Server server;
    private final Map<String, ServerPlayer> playerMap;
    
    public PlayerList(Server server){
        this.server = server;
        playerMap = new ConcurrentHashMap<>();
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
        final ServerPlayer serverPlayer = new ServerPlayer(level, connection, name);
        server.getConnectionManager().setPacketHandler(connection, serverPlayer.getConnectionAdapter());
        serverPlayer.teleport(level, spawnPosition);
        
        playerMap.put(name, serverPlayer);
        
        // Send packets to player
        final PlayerGameConnection connectionAdapter = serverPlayer.getConnectionAdapter();
        
        new CBPacketSpawnInfo(level.getConfiguration().getName(), spawnPosition).write(connection); // spawn init info
        new CBPacketAbilities(false).write(connection); // abilities
        
        for(ServerPlayer anotherPlayer: playerMap.values())
            if(anotherPlayer != serverPlayer)
                connectionAdapter.sendPacket(new CBPacketSpawnPlayer(anotherPlayer)); // all players info
        
        // Load chunks for player
        level.addEntity(serverPlayer);
        level.getChunkManager().loadInitChunkForPlayer(serverPlayer);

        // Send to all player-connection-event packet
        broadcastPacketExcept(new CBPacketSpawnPlayer(serverPlayer), serverPlayer);
        
        serverPlayer.sendToChat(new Component().color(TextColor.YELLOW).text("Player " + name + " joined the game"));
    }
    
    
    public void disconnectPlayer(ServerPlayer player){
        broadcastPacketExcept(new CBPacketRemoveEntity(player), player); // Remove player entity on client
        player.getLevel().removeEntity(player); // Remove entity on server
        PlayerIO.save(player);                  // Save
        
        playerMap.remove(player.getName());
    }
    
    
    public OfflinePlayer getOfflinePlayer(String name){ //: NICHE TAK, HARAM
        return null;
    }
    
    
    public void broadcastPacket(IPacket<?> packet){
        for(ServerPlayer player: playerMap.values())
            player.sendPacket(packet);
    }

    public void broadcastPacketExcept(IPacket<?> packet, ServerPlayer except){
        for(ServerPlayer player: playerMap.values())
            if(player != except)
                player.sendPacket(packet);
    }

    public void broadcastPacketLevel(ServerLevel level, IPacket<?> packet){
        for(ServerPlayer player: playerMap.values())
            if(player.getLevel() == level)
                player.sendPacket(packet);
    }

    public void broadcastPacketLevelExcept(ServerLevel level, IPacket<?> packet, ServerPlayer except){
        for(ServerPlayer player: playerMap.values())
            if(player.getLevel() == level && player != except)
                player.sendPacket(packet);
    }
    
    
    public void broadcastServerMessage(Component message){
        broadcastPacket(new CBPacketChatMessage(message.toFlatList()));
        System.out.println(message);
    }
    
    public void broadcastPlayerMessage(ServerPlayer player, Component message){
        broadcastPacket(new CBPacketChatMessage(player.getName(), message.toFlatList()));
        System.out.println(message);
    }
    
}
