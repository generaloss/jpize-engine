package pize.tests.voxelgame.server;

import pize.net.tcp.TcpServer;
import pize.tests.voxelgame.server.level.ServerLevel;
import pize.util.time.TickGenerator;

public class LocalServer extends Server{
    
    private final TcpServer tcpServer;
    
    public LocalServer(){
        getConfiguration().loadDefaults(); // Load server configuration
        
        tcpServer = new TcpServer(getConnectionManager());
    }
    
    public void run(){
        System.out.println("[Server]: Server listening on " + getConfiguration().getAddress() + ":" + getConfiguration().getPort());
        
        getLevelManager().loadLevel(getConfiguration().getDefaultLevelName()); // Load default level
        tcpServer.run(getConfiguration().getAddress(), getConfiguration().getPort()); // Run TCP server

        new TickGenerator(20){
            public void run(){
                tick();
            }
        } .startAsync();
    }

    private void tick(){
        for(ServerLevel world : getLevelManager().getLoadedLevels())
            world.tick();
    }
    
}
