package jpize.net.tcp;

import jpize.net.tcp.packet.IPacket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TcpClient extends TcpDisconnector{

    private TcpConnection connection;
    private final TcpListener listener;
    
    public TcpClient(TcpListener listener){
        this.listener = listener;
    }
    

    public void connect(String address, int port){
        if(connection != null && !connection.isClosed())
            throw new RuntimeException("Already connected");
        
        try{
            final Socket socket = new Socket();
            final InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(address), port);
            socket.connect(socketAddress);

            connection = new TcpConnection(socket, listener, this);
            listener.connected(connection);
        }catch(IOException e){
            System.err.println("TcpClient: " + e.getMessage());
        }
    }
    
    
    public void send(byte[] packet){
        connection.send(packet);
    }
    
    public void send(ByteArrayOutputStream stream){
        connection.send(stream);
    }
    
    public void send(PacketWriter data){
        connection.send(data);
    }

    public void send(IPacket<?> packet){
        connection.send(packet);
    }

    
    synchronized public void disconnect(){
        if(connection == null || connection.isClosed())
            return;

        connection.close();
        listener.disconnected(connection);
    }
    
    @Override
    protected void disconnected(TcpConnection connection){
        listener.disconnected(connection);
    }

    
    public TcpConnection getConnection(){
        return connection;
    }

}
