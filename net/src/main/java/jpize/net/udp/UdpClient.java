package jpize.net.udp;

import java.net.*;

public class UdpClient{

    private Thread receiverThread;
    private final UdpListener listener;
    private UdpConnection connection;

    public UdpClient(UdpListener listener){
        this.listener = listener;
    }

    public synchronized UdpClient connect(String address, int port){
        if(connection != null && !connection.isClosed())
            throw new RuntimeException("Already enabled");

        try{
            final DatagramSocket socket = new DatagramSocket();
            socket.connect(InetAddress.getByName(address), port);
            connection = new UdpConnection(socket, listener);
            
        }catch(Exception e){
            throw new RuntimeException("UdpClient startup error: " + e.getMessage());
        }

        return this;
    }

    public void send(byte[] data, SocketAddress address){
        connection.send(new DatagramPacket(data, data.length, address));
    }

    public void send(byte[] data, String address, int port){
        send(data, new InetSocketAddress(address, port));
    }

    public void disconnect(){
        if(connection.isClosed())
            return;

        receiverThread.interrupt();
        connection.close();
    }

    public UdpConnection getConnection(){
        return connection;
    }

}
