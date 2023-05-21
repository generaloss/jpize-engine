package megalul.projectvostok.server;

import megalul.projectvostok.server.net.ServerPacketHandler;
import pize.net.tcp.TcpServer;

public class LocalServer extends Server{
    
    private final TcpServer tcpServer;
    
    public LocalServer(){
        getConfiguration().loadDefaults();
        
        tcpServer = new TcpServer(new ServerPacketHandler(this));
    }
    
    public void run(){
        tcpServer.run(getConfiguration().getAddress(), getConfiguration().getPort());
    }
    
}
