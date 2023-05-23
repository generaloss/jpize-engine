package pize.net.tcp;

public interface TcpListener{
    
    void received(byte[] data, TcpByteChannel sender);

    void connected(TcpByteChannel channel);

    void disconnected(TcpByteChannel channel);

}
