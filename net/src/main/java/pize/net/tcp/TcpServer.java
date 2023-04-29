package pize.net.tcp;

import pize.util.MTArrayList;
import pize.util.Utils;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Iterator;

public class TcpServer implements Closeable{

    private ServerSocket serverSocket;
    private MTArrayList<TcpConnection> connections;
    private Thread connectorThread, receiverThread;
    private final TcpServerListener<TcpPacket> listener;
    private boolean closed;

    public TcpServer(TcpServerListener<TcpPacket> listener){
        this.listener = listener;

        closed = true;
    }

    public synchronized TcpServer start(String ip, int port){
        if(!closed)
            throw new RuntimeException("Already enabled");

        try{
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(InetAddress.getByName(ip), port));
            serverSocket.setPerformancePreferences(0, 2, 1);
            closed = false;

            connections = new MTArrayList<>();

            connectorThread = new Thread(()->{
                try{
                    while(!Thread.interrupted()){
                        TcpConnection connection = new TcpConnection(serverSocket.accept());
                        connections.add(connection);
                        listener.connected(connection);
                    }
                }catch(IOException ignored){
                }
            });
            connectorThread.setDaemon(true);
            connectorThread.setPriority(Thread.MIN_PRIORITY);
            connectorThread.start();

            receiverThread = new Thread(()->{
                while(!Thread.interrupted()){
                    for(int i = 0; i < connections.size(); i++){
                        TcpConnection connection = connections.get(i);
                        if(connection.isClosed()){
                            connections.remove(connection);
                            listener.disconnected(connection);
                            continue;
                        }
                        if(connection.available() != 0)
                            listener.received(connection.next(), connection);
                    }
                    Thread.yield();
                }
            });

            receiverThread.setDaemon(true);
            receiverThread.setPriority(Thread.MIN_PRIORITY);
            receiverThread.start();

        }catch(Exception e){
            throw new RuntimeException("TcpServer startup error: " + e.getMessage());
        }

        return this;
    }

    public void broadcast(TcpPacket packet){
        for(TcpConnection connection: connections)
            connection.send(packet);
    }

    public Iterator<TcpConnection> getConnections(){
        return connections.iterator();
    }

    @Override
    public void close(){
        if(closed)
            return;

        connectorThread.interrupt();
        receiverThread.interrupt();

        for(TcpConnection connection: connections)
            connection.close();
        connections.clear();

        Utils.close(serverSocket);
    }

}
