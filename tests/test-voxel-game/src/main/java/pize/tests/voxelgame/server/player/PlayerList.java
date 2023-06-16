package pize.tests.voxelgame.server.player;

import pize.tests.voxelgame.clientserver.net.PlayerProfile;
import pize.tests.voxelgame.clientserver.net.packet.PacketSpawnPlayer;
import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.server.world.ServerWorld;
import pize.net.tcp.TcpChannel;
import pize.net.tcp.packet.IPacket;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PlayerList{
    
    private final Server serverOF;
    private final Map<String, OnlinePlayer> playerNameMap;
    private final Map<TcpChannel, OnlinePlayer> playerChannelMap;
    
    public PlayerList(Server serverOF){
        this.serverOF = serverOF;
        playerNameMap = new HashMap<>();
        playerChannelMap = new HashMap<>();
    }
    
    public Server getServerOf(){
        return serverOF;
    }
    
    
    public Collection<OnlinePlayer> getOnlinePlayers(){
        return playerNameMap.values();
    }
    
    public OnlinePlayer getOnlinePlayer(String name){
        return playerNameMap.get(name);
    }
    
    public OnlinePlayer getOnlinePlayer(TcpChannel channel){
        return playerChannelMap.get(channel);
    }
    
    public boolean isPlayerOnline(String name){
        return playerNameMap.containsKey(name);
    }
    
    
    public void connectOnlinePlayer(String name, TcpChannel channel){
        final OnlinePlayer onlinePlayer = addOnlinePlayer(name, channel);
        
        final OfflinePlayer offlinePlayer = getOfflinePlayer(name);
        final String worldInName = offlinePlayer == null ? serverOF.getConfiguration().getDefaultWorldName() : offlinePlayer.getWorldName();
        
        onlinePlayer.setWorldIn(worldInName);
        serverOF.getWorldManager().loadWorld(worldInName);
        
        final ServerWorld worldIn = serverOF.getWorldManager().getWorld(worldInName);
        worldIn.getPlayersIn().add(onlinePlayer);
        worldIn.getChunkManager().loadInitChunkForPlayer(onlinePlayer);

        broadcastPacket(new PacketSpawnPlayer(onlinePlayer), onlinePlayer);
        System.out.println("[SERVER]: кароче чел присоеденисля к серверу и надо челам на сервере по такому поводу отправить весточку");
    }
    
    public void disconnectOnlinePlayer(TcpChannel channel){
        OnlinePlayer onlinePlayer = getOnlinePlayer(channel);
        PlayerIO.save(onlinePlayer);
        
        removeOnlinePlayer(onlinePlayer);
    }
    
    
    private OnlinePlayer addOnlinePlayer(String name, TcpChannel channel){
        final OnlinePlayer onlinePlayer = new OnlinePlayer(serverOF, new PlayerProfile(name), channel);
        
        playerNameMap.put(name, onlinePlayer);
        playerChannelMap.put(channel, onlinePlayer);
        
        return onlinePlayer;
    }
    
    private void removeOnlinePlayer(OnlinePlayer player){
        playerNameMap.remove(player.getProfile().getName());
        playerChannelMap.remove(player.getChannel());
    }
    
    
    public OfflinePlayer getOfflinePlayer(String name){ //: NICHE TAK, HARAM
        return null;
    }
    
    
    public void broadcastPacket(IPacket packet){
        for(OnlinePlayer player: playerNameMap.values())
            player.sendPacket(packet);
    }

    public void broadcastPacket(IPacket packet, OnlinePlayer except){
        for(OnlinePlayer player: playerNameMap.values())
            if(player != except)
                player.sendPacket(packet);
    }
    
}
