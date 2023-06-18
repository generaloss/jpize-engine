package pize.net.tcp;

public interface TcpListener{
    
    void received(byte[] bytes, TcpConnection sender);

    void connected(TcpConnection connection);

    void disconnected(TcpConnection connection);

}
