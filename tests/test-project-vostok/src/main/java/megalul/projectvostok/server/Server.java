package megalul.projectvostok.server;

import megalul.projectvostok.server.player.PlayerList;
import megalul.projectvostok.server.world.ServerWorld;
import megalul.projectvostok.server.world.WorldManager;

public abstract class Server{

    private final ServerConfiguration configuration;
    private final PlayerList playerList;
    private final WorldManager worldManager;
    
    public Server(){
        configuration = new ServerConfiguration();
        playerList = new PlayerList(this);
        worldManager = new WorldManager(this);
        
        worldManager.loadWorld(configuration.getDefaultWorldName());
    }
    
    
    public ServerWorld getDefaultWorld(){
        return worldManager.getWorld(configuration.getDefaultWorldName());
    }
    
    
    public ServerConfiguration getConfiguration(){
        return configuration;
    }
    
    public PlayerList getPlayerList(){
        return playerList;
    }
    
    public WorldManager getWorldManager(){
        return worldManager;
    }
    
}
