package pize.net.tcp;

import pize.util.Utils;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public class TcpServer extends TcpDisconnector implements Closeable{

    private ServerSocket serverSocket;
    private CopyOnWriteArrayList<TcpChannel> channels;
    private final TcpListener listener;
    private boolean closed;

    public TcpServer(TcpListener listener){
        this.listener = listener;
        closed = true;
    }

    
    public void run(String address, int port){
        if(!closed)
            throw new RuntimeException("Server already running");

        try{
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));
            
            channels = new CopyOnWriteArrayList<>();
            waitConnections();
            closed = false;
            
        }catch(IOException e){
            System.err.println("TcpServer (run error): " + e.getMessage());
        }
    }
    
    private void waitConnections(){
        final Thread connectorThread = new Thread(()->{
            try{
                while(!Thread.interrupted() && !closed){
                    final TcpChannel channel = new TcpChannel(serverSocket.accept(), listener, this);
                    listener.connected(channel);
                    channels.add(channel);
                    
                    Thread.yield();
                }
            }catch(IOException e){
                System.err.println("TcpServer (connect client error): " + e.getMessage());
            }
        });
        
        connectorThread.setPriority(Thread.MIN_PRIORITY);
        connectorThread.setDaemon(true);
        connectorThread.start();
    }
    
    
    public void broadcast(byte[] packet){
        for(TcpChannel channel: channels)
            channel.send(packet);
    }
    
    @Override
    synchronized public void disconnected(TcpChannel channel){
        listener.disconnected(channel);
        channels.remove(channel);
        channel.close();
    }
    

    public Collection<TcpChannel> getAllChannels(){
        return channels;
    }
    
    
    public boolean isClosed(){
        return closed;
    }

    @Override
    public void close(){
        if(closed)
            return;
        
        for(TcpChannel connection: channels)
            connection.close();
        channels.clear();
        
        closed = true;
        Utils.close(serverSocket);
    }
    
}
