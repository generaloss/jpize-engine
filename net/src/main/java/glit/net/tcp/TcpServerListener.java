package glit.net.tcp;

import glit.net.NetConnection;

public interface TcpServerListener<P>{

    void received(TcpPacket packet, NetConnection<P> sender);

    void connected(NetConnection<P> connection);

    void disconnected(NetConnection<P> connection);

}
