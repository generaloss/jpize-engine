package jpize.net.tcp;

import jpize.net.tcp.packet.IPacket;
import jpize.util.Utils;
import jpize.util.io.JpizeOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public class TcpServer implements TcpConnection.Disconnector, Closeable{

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
            throw new RuntimeException("TCP Server already running");

        try{
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));
            connectionList = new CopyOnWriteArrayList<>();
            waitConnections();
            closed = false;
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void run(int port){
        if(!closed)
            throw new RuntimeException("TCP Server already running");

        try{
            serverSocket = new ServerSocket(port);
            connectionList = new CopyOnWriteArrayList<>();
            waitConnections();
            closed = false;
        }catch(Exception e){
            e.printStackTrace();
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
    
    
    public void broadcast(TcpConnection exclude, byte[] bytes){
        for(TcpConnection connection: connectionList)
            if(connection != exclude)
                connection.send(bytes);
    }

    public void broadcast(byte[] bytes){
        for(TcpConnection connection: connectionList)
            connection.send(bytes);
    }

    public void broadcast(ByteArrayOutputStream stream){
        broadcast(stream.toByteArray());
    }

    public void broadcast(TcpConnection exclude, ByteArrayOutputStream stream){
        broadcast(exclude, stream.toByteArray());
    }

    public synchronized void broadcast(PacketWriter data){
        final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        final JpizeOutputStream dataStream = new JpizeOutputStream(byteStream);

        data.write(dataStream);
        broadcast(byteStream);
    }

    public synchronized void broadcast(TcpConnection exclude, PacketWriter data){
        final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        final JpizeOutputStream dataStream = new JpizeOutputStream(byteStream);

        data.write(dataStream);
        broadcast(exclude, byteStream);
    }

    public void broadcast(IPacket<?> packet){
        broadcast(dataStream -> {
            try{
                dataStream.writeInt(packet.getPacketID());
                packet.write(dataStream);
            }catch(IOException e){
                e.printStackTrace();
            }
        });
    }

    public void broadcast(TcpConnection exclude, IPacket<?> packet){
        broadcast(exclude, dataStream -> {
            try{
                dataStream.writeInt(packet.getPacketID());
                packet.write(dataStream);
            }catch(IOException e){
                e.printStackTrace();
            }
        });
    }

    @Override
    public void disconnect(TcpConnection connection){
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
