package pize.tests.voxelgame.server;

import pize.tests.voxelgame.server.level.LevelManager;
import pize.tests.voxelgame.server.net.ServerConnectionManager;
import pize.tests.voxelgame.server.player.PlayerList;

public abstract class Server{

    private final ServerConfiguration configuration;
    private final PlayerList playerList;
    private final LevelManager levelManager;
    private final ServerConnectionManager connectionManager;
    
    public Server(){
        configuration = new ServerConfiguration();
        playerList = new PlayerList(this);
        levelManager = new LevelManager(this);
        connectionManager = new ServerConnectionManager(this);
    }
    
    
    public ServerConfiguration getConfiguration(){
        return configuration;
    }
    
    public PlayerList getPlayerList(){
        return playerList;
    }
    
    public LevelManager getLevelManager(){
        return levelManager;
    }
    
    public ServerConnectionManager getConnectionManager(){
        return connectionManager;
    }
    
}
