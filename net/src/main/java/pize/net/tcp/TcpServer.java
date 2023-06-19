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
    private CopyOnWriteArrayList<TcpConnection> connectionList;
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
            
            connectionList = new CopyOnWriteArrayList<>();
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
                    final TcpConnection connection = new TcpConnection(serverSocket.accept(), listener, this);
                    listener.connected(connection);
                    connectionList.add(connection);
                    
                    Thread.yield();
                }
            }catch(IOException e){
                System.err.println("TcpServer (connect client error): " + e.getMessage());
            }
        }, "TcpServer-Thread");
        
        connectorThread.setPriority(Thread.MIN_PRIORITY);
        connectorThread.setDaemon(true);
        connectorThread.start();
    }
    
    
    public void broadcast(byte[] packet){
        for(TcpConnection channel: connectionList)
            channel.send(packet);
    }
    
    @Override
    synchronized public void disconnected(TcpConnection connection){
        listener.disconnected(connection);
        connectionList.remove(connection);
        connection.close();
    }
    

    public Collection<TcpConnection> getConnections(){
        return connectionList;
    }
    
    
    public boolean isClosed(){
        return closed;
    }

    @Override
    public void close(){
        if(closed)
            return;
        
        for(TcpConnection connection: connectionList)
            connection.close();
        connectionList.clear();
        
        closed = true;
        Utils.close(serverSocket);
    }
    
}
