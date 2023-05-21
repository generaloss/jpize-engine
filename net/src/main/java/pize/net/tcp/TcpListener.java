package pize.net.tcp;

import pize.net.NetChannel;

public interface TcpListener<P>{
    
    void received(P data, NetChannel<P> sender);

    void connected(NetChannel<P> channel);

    void disconnected(NetChannel<P> channel);

}
