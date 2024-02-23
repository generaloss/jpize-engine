package jpize.net.udp;

import java.io.IOException;
import java.net.*;

public class UdpServer{
    
    private final UdpListener listener;
    private UdpConnection connection;

    public UdpServer(UdpListener listener){
        this.listener = listener;
    }


    public void run(int port){
        if(connection != null && !connection.isClosed())
            throw new RuntimeException("UDP Server already running");

        try{
            final DatagramSocket socket = new DatagramSocket(port);
            connection = new UdpConnection(socket, listener);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void run(String address, int port){
        if(connection != null && !connection.isClosed())
            throw new RuntimeException("UDP Server already running");

        try{
            final DatagramSocket socket = new DatagramSocket(port, InetAddress.getByName(address));
            connection = new UdpConnection(socket, listener);
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public void send(byte[] data, SocketAddress address){
        connection.send(new DatagramPacket(data, data.length, address));
    }

    public void send(byte[] data, String address, int port){
        send(data, new InetSocketAddress(address, port));
    }

    public void close(){
        if(!connection.isClosed())
            connection.close();
    }

    public UdpConnection getConnection(){
        return connection;
    }

}
