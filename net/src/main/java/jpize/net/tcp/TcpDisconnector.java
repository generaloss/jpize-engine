package jpize.net.tcp;

abstract class TcpDisconnector{
    
    protected abstract void disconnected(TcpConnection connection);
    
}
