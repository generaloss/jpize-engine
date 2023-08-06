package pize.tests.minecraftosp.server;

import pize.net.tcp.TcpServer;
import pize.tests.minecraftosp.server.gen.DefaultGenerator;
import pize.tests.minecraftosp.server.command.CommandDispatcher;
import pize.tests.minecraftosp.server.level.LevelManager;
import pize.tests.minecraftosp.server.level.ServerLevel;
import pize.tests.minecraftosp.server.net.ServerConnectionManager;
import pize.tests.minecraftosp.server.player.PlayerList;
import pize.tests.minecraftosp.server.player.ServerPlayer;
import pize.tests.minecraftosp.main.text.Component;
import pize.tests.minecraftosp.main.time.GameTime;
import pize.util.time.TickGenerator;
import pize.util.time.Tickable;

import java.util.Collection;

public abstract class Server implements Tickable{
    
    private final ServerConfiguration configuration;
    private final TcpServer tcpServer;
    private final ServerConnectionManager connectionManager;
    private final PlayerList playerList;
    private final LevelManager levelManager;
    private final CommandDispatcher commandDispatcher;
    private final ServerGameTime gameTime;
    
    public Server(){
        configuration = new ServerConfiguration();
        
        connectionManager = new ServerConnectionManager(this);
        tcpServer = new TcpServer(connectionManager);
        
        playerList = new PlayerList(this);
        levelManager = new LevelManager(this);
        commandDispatcher = new CommandDispatcher(this);
        gameTime = new ServerGameTime(this);
    }
    
    
    public void run(){
        getLevelManager().createLevel(getConfiguration().getDefaultLevelName(), 43337, DefaultGenerator.getInstance()); // Create default level
        tcpServer.run(getConfiguration().getAddress(), getConfiguration().getPort()); // Run TCP server
        
        new TickGenerator(GameTime.TICKS_PER_SECOND).startAsync(this);
    }

    public void stop(){
        final Collection<ServerPlayer> players = getPlayerList().getPlayers();
        for(ServerPlayer player: players)
            player.disconnect();
    }

    
    @Override
    public void tick(){
        gameTime.tick();
        
        for(ServerLevel level : getLevelManager().getLoadedLevels())
            level.tick();
    }


    public void broadcast(Component component){
        playerList.broadcastServerMessage(component);
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
    
    public CommandDispatcher getCommandDispatcher(){
        return commandDispatcher;
    }
    
    public ServerGameTime getGameTime(){
        return gameTime;
    }
    
}
