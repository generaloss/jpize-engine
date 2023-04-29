package pize.net.udp;

import pize.net.NetListener;

import java.net.*;

public class UdpClient{

    private Thread receiverThread;
    private final NetListener<DatagramPacket> listener;
    private UdpConnection connection;

    public UdpClient(NetListener<DatagramPacket> listener){
        this.listener = listener;
    }

    public synchronized UdpClient connect(String ip, int port){
        if(connection != null && !connection.isClosed())
            throw new RuntimeException("Already enabled");

        try{
            DatagramSocket socket = new DatagramSocket();
            socket.connect(InetAddress.getByName(ip), port);
            connection = new UdpConnection(socket);

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
            throw new RuntimeException("UdpClient startup error: " + e.getMessage());
        }

        return this;
    }

    public void send(byte[] data, SocketAddress address){
        connection.send(new DatagramPacket(data, data.length, address));
    }

    public void send(byte[] data, String ip, int port){
        send(data, new InetSocketAddress(ip, port));
    }

    public void disconnect(){
        if(connection.isClosed())
            return;

        receiverThread.interrupt();
        connection.close();
    }

    public UdpConnection getConnection(){
        return connection;
    }

}
