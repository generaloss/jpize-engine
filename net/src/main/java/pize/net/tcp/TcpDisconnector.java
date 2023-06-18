package pize.net.tcp;

abstract class TcpDisconnector{
    
    protected abstract void disconnected(TcpConnection connection);
    
}
