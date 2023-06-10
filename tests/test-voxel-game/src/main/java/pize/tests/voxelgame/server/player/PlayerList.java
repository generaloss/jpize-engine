package pize.tests.voxelgame.server.player;

import pize.tests.voxelgame.clientserver.net.PlayerProfile;
import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.server.world.ServerWorld;
import pize.net.tcp.TcpChannel;
import pize.net.tcp.packet.IPacket;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PlayerList{
    
    private final Server serverOF;
    private final Map<String, OnlinePlayer> list;
    
    public PlayerList(Server serverOF){
        this.serverOF = serverOF;
        list = new HashMap<>();
    }
    
    public Server getServerOf(){
        return serverOF;
    }
    
    
    public Collection<OnlinePlayer> getOnlinePlayers(){
        return list.values();
    }
    
    public OnlinePlayer getOnlinePlayer(String name){
        return list.get(name);
    }
    
    public boolean isPlayerOnline(String name){
        return list.containsKey(name);
    }
    
    public OfflinePlayer getOfflinePlayer(String name){ //: NICHE TAK, HARAM
        return null;
    }
    
    
    public void connectOnlinePlayer(String name, TcpChannel netChannel){
        OnlinePlayer onlinePlayer = new OnlinePlayer(serverOF, new PlayerProfile(name), netChannel);
        list.put(name, onlinePlayer);
        
        OfflinePlayer offlinePlayer = getOfflinePlayer(name);
        String worldInName = offlinePlayer == null ? serverOF.getConfiguration().getDefaultWorldName() : offlinePlayer.getWorldName();
        
        onlinePlayer.setWorldIn(worldInName);
        serverOF.getWorldManager().loadWorld(worldInName);
        
        ServerWorld worldIn = serverOF.getWorldManager().getWorld(worldInName);
        worldIn.getPlayersIn().add(onlinePlayer);
        worldIn.getChunkProvider().loadInitChunkForPlayer(onlinePlayer);
    }
    
    public void disconnectOnlinePlayer(OnlinePlayer player){
        list.remove(player.getProfile().getName());
        
        PlayerIO.save(player);
    }
    
    
    public void broadcastPacket(IPacket packet){
        for(OnlinePlayer player: list.values())
            player.sendPacket(packet);
    }
    
}
