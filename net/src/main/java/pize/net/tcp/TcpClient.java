package pize.net.tcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TcpClient{

    private TcpByteChannel channel;
    private final TcpListener<byte[]> listener;
    
    public TcpClient(TcpListener<byte[]> listener){
        this.listener = listener;
    }
    

    public void connect(String address, int port){
        if(channel != null && !channel.isClosed())
            throw new RuntimeException("Already connected");
        
        try{
            final Socket socket = new Socket();
            InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(address), port);
            socket.connect(socketAddress);

            channel = new TcpByteChannel(socket);
            receivePackets();
            listener.connected(channel);
        }catch(IOException e){
            System.err.println("TcpClient: " + e.getMessage());
        }
    }
    
    
    private void receivePackets(){
        final Thread receiveThread = new Thread(()->{
            while(!Thread.interrupted() && !channel.isClosed()){
                Thread.yield();
                
                if(channel.isAvailable())
                    listener.received(channel.nextPacket(), channel);
            }
            listener.disconnected(channel);
        });
        
        receiveThread.setDaemon(true);
        receiveThread.setPriority(Thread.MIN_PRIORITY);
        receiveThread.start();
    }
    
    
    public void send(byte[] packet){
        channel.send(packet);
    }
    
    public void disconnect(){
        if(channel == null || channel.isClosed())
            return;

        channel.close();
        listener.disconnected(channel);
    }

    
    public TcpByteChannel getChannel(){
        return channel;
    }

}
