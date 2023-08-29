package jpize.net.udp;

import java.net.*;

public class UdpClient{

    private Thread receiverThread;
    private final UdpListener listener;
    private UdpChannel connection;

    public UdpClient(UdpListener listener){
        this.listener = listener;
    }

    public synchronized UdpClient connect(String ip, int port){
        if(connection != null && !connection.isClosed())
            throw new RuntimeException("Already enabled");

        try{
            final DatagramSocket socket = new DatagramSocket();
            socket.connect(InetAddress.getByName(ip), port);
            connection = new UdpChannel(socket, listener);
            
        }catch(Exception e){
            throw new RuntimeException("UdpClient startup error: " + e.getMessage());
        }

        return this;
    }

    public void send(byte[] data, SocketAddress address){
        connection.send(new DatagramPacket(data, data.length, address));
    }

    public void send(byte[] data, String ip, int port){
        send(data, new InetSocketAddress(ip, port));
    }

    public void disconnect(){
        if(connection.isClosed())
            return;

        receiverThread.interrupt();
        connection.close();
    }

    public UdpChannel getConnection(){
        return connection;
    }

}
