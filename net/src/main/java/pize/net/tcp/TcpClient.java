package pize.net.tcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TcpClient extends TcpDisconnector{

    private TcpChannel channel;
    private final TcpListener listener;
    
    public TcpClient(TcpListener listener){
        this.listener = listener;
    }
    

    public void connect(String address, int port){
        if(channel != null && !channel.isClosed())
            throw new RuntimeException("Already connected");
        
        try{
            final Socket socket = new Socket();
            final InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(address), port);
            socket.connect(socketAddress);

            channel = new TcpChannel(socket, listener, this);
            listener.connected(channel);
        }catch(IOException e){
            System.err.println("TcpClient: " + e.getMessage());
        }
    }
    
    
    public void send(byte[] packet){
        channel.send(packet);
    }
    
    synchronized public void disconnect(){
        if(channel == null || channel.isClosed())
            return;

        channel.close();
        listener.disconnected(channel);
    }
    
    @Override
    protected void disconnected(TcpChannel channel){
        listener.disconnected(channel);
    }

    
    public TcpChannel getChannel(){
        return channel;
    }

}
