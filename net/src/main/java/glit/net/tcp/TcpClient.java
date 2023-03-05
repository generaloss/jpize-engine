package glit.net.tcp;

import glit.net.NetListener;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TcpClient{

    private TcpConnection connection;
    private Thread receiverThread;
    private final NetListener<TcpPacket> listener;

    public TcpClient(NetListener<TcpPacket> listener){
        this.listener = listener;
    }

    public synchronized TcpClient connect(String ip, int port){
        if(connection != null && !connection.isClosed())
            throw new RuntimeException("Already connected");

        try{
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(InetAddress.getByName(ip), port));

            connection = new TcpConnection(socket);

            receiverThread = new Thread(()->{
                while(!Thread.interrupted()){
                    if(connection.available() != 0)
                        listener.received(connection.next());

                    Thread.yield();
                }
            });

            receiverThread.setDaemon(true);
            receiverThread.setPriority(Thread.MIN_PRIORITY);
            receiverThread.start();

        }catch(Exception e){
            throw new RuntimeException("TcpClient connection failed: " + e.getMessage());
        }

        return this;
    }

    public void send(TcpPacket packet){
        connection.send(packet);
    }

    public void disconnect(){
        if(connection != null && connection.isClosed())
            return;

        receiverThread.interrupt();
        connection.close();
    }

    public TcpConnection getConnection(){
        return connection;
    }

}
