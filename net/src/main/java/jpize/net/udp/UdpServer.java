package jpize.net.udp;

import java.net.*;

public class UdpServer{
    
    private final UdpListener listener;
    private UdpChannel connection;

    public UdpServer(UdpListener listener){
        this.listener = listener;
    }

    public synchronized UdpServer start(String ip, int port){
        if(connection != null && !connection.isClosed())
            throw new RuntimeException("Already enabled");

        try{
            final DatagramSocket socket = new DatagramSocket(port, InetAddress.getByName(ip));
            connection = new UdpChannel(socket, listener);
        }catch(Exception e){
            throw new RuntimeException("UdpServer startup error: " + e.getMessage());
        }

        return this;
    }

    public void send(byte[] data, SocketAddress address){
        connection.send(new DatagramPacket(data, data.length, address));
    }

    public void send(byte[] data, String ip, int port){
        send(data, new InetSocketAddress(ip, port));
    }

    public void close(){
        if(!connection.isClosed())
            connection.close();
    }

    public UdpChannel getConnection(){
        return connection;
    }

}
