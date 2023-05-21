package megalul.projectvostok.server.player;

import megalul.projectvostok.clientserver.net.PlayerProfile;
import megalul.projectvostok.server.Server;
import megalul.projectvostok.server.world.ServerWorld;

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
    
    
    public OnlinePlayer getOnlinePlayer(String name){
        return list.get(name);
    }
    
    public boolean isPlayerOnline(String name){
        return list.containsKey(name);
    }
    
    public OfflinePlayer getOfflinePlayer(String name){ //: NICHE TAK, HARAM
        return null;
    }
    
    
    public void connectOnlinePlayer(String name){
        OnlinePlayer onlinePlayer = new OnlinePlayer(serverOF, new PlayerProfile(name));
        
        OfflinePlayer offlinePlayer = getOfflinePlayer(name);
        String worldInName = offlinePlayer == null ? serverOF.getConfiguration().getDefaultWorldName() : offlinePlayer.getWorldIn();
        
        onlinePlayer.setWorldIn(worldInName);
        serverOF.getWorldManager().loadWorld(worldInName);
        
        ServerWorld worldIn = serverOF.getWorldManager().getWorld(worldInName);
        worldIn.getPlayersIn().add(onlinePlayer);
        worldIn.getChunkProvider().loadInitChunkForPlayer(onlinePlayer);
        
        
        
        list.put(name, onlinePlayer);
    }
    
    public void disconnectOnlinePlayer(OnlinePlayer player){
        list.remove(player.getProfile().getName());
        
        PlayerIO.save(player);
    }
    
}
