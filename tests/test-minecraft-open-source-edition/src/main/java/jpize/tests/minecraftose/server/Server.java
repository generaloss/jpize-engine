package jpize.tests.minecraftose.server;

import jpize.net.tcp.TcpServer;
import jpize.tests.minecraftose.main.text.Component;
import jpize.tests.minecraftose.main.time.GameTime;
import jpize.tests.minecraftose.server.command.CommandDispatcher;
import jpize.tests.minecraftose.server.gen.DefaultGenerator;
import jpize.tests.minecraftose.server.gen.FlatGenerator;
import jpize.tests.minecraftose.server.level.LevelManager;
import jpize.tests.minecraftose.server.level.ServerLevel;
import jpize.tests.minecraftose.server.net.ServerConnectionManager;
import jpize.tests.minecraftose.server.player.PlayerList;
import jpize.tests.minecraftose.server.player.ServerPlayer;
import jpize.util.time.TickGenerator;
import jpize.util.time.Tickable;

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
        final long seed = 61128216; //Maths.randomSeed(8);
        getLevelManager().createLevel(getConfiguration().getDefaultLevelName(), String.valueOf(seed), DefaultGenerator.getInstance()); // Create overworld level
        getLevelManager().createLevel("flat-world", "FLAT", FlatGenerator.getInstance()); // Create flat-world level

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
