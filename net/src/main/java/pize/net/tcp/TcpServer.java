package pize.net.tcp;

import pize.util.Utils;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public class TcpServer implements Closeable{

    private ServerSocket serverSocket;
    private CopyOnWriteArrayList<TcpByteChannel> channels;
    private final TcpListener listener;
    private boolean closed;

    public TcpServer(TcpListener listener){
        this.listener = listener;
        closed = true;
    }

    public synchronized void run(String address, int port){
        if(!closed)
            throw new RuntimeException("Server already running");

        try{
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));
            serverSocket.setPerformancePreferences(0, 2, 1);
            closed = false;

            channels = new CopyOnWriteArrayList<>();
            waitConnections();
            receivePackets();
        }catch(IOException e){
            System.err.println("TcpServer (run error): " + e.getMessage());
        }
    }
    
    private void waitConnections(){
        final Thread connectorThread = new Thread(()->{
            try{
                while(!Thread.interrupted() && !closed){
                    TcpByteChannel channel = new TcpByteChannel(serverSocket.accept());
                    channels.add(channel);
                    listener.connected(channel);
                    
                    Thread.yield();
                }
            }catch(IOException e){
                System.err.println("TcpServer (connect client error): " + e.getMessage());
            }
        });
        
        connectorThread.setDaemon(true);
        connectorThread.setPriority(Thread.MIN_PRIORITY);
        connectorThread.start();
    }

    private void receivePackets(){
        final Thread receiveThread = new Thread(()->{
            while(!Thread.interrupted() && !closed){
                for(int i = 0; i < channels.size(); i++){
                    final TcpByteChannel channel = channels.get(i);
                    if(channel.isAvailable())
                        listener.received(channel.nextPacket(), channel);
                    
                    else if(channel.isClosed())
                        disconnect(channel);
                }
                
                Thread.yield();
            }
        });
        
        receiveThread.setDaemon(true);
        receiveThread.setPriority(Thread.MIN_PRIORITY);
        receiveThread.start();
    }
    
    
    public void broadcast(byte[] packet){
        for(TcpByteChannel channel: channels)
            channel.send(packet);
    }
    
    public void disconnect(TcpByteChannel channel){
        channels.remove(channel);
        listener.disconnected(channel);
        channel.close();
    }
    

    public Collection<TcpByteChannel> getAllChannels(){
        return channels;
    }
    
    
    public boolean isClosed(){
        return closed;
    }

    @Override
    public void close(){
        if(closed)
            return;
        
        for(TcpByteChannel connection: channels)
            connection.close();
        channels.clear();
        closed = true;

        Utils.close(serverSocket);
    }

}
