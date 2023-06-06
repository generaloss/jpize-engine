package pize.net.tcp;

public interface TcpListener{
    
    void received(byte[] bytes, TcpChannel sender);

    void connected(TcpChannel channel);

    void disconnected(TcpChannel channel);

}
