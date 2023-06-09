package pize.tests.voxelgame.server;

import pize.tests.voxelgame.server.net.ServerPacketHandler;
import pize.net.tcp.TcpServer;

public class LocalServer extends Server{
    
    private final TcpServer tcpServer;
    
    public LocalServer(){
        getConfiguration().loadDefaults();
        
        tcpServer = new TcpServer(new ServerPacketHandler(this));
    }
    
    public void run(){
        System.out.println("Run server");
        tcpServer.run(getConfiguration().getAddress(), getConfiguration().getPort());
    }
    
}
