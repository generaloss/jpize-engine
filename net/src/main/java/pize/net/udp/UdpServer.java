package pize.net.udp;

import java.net.*;

public class UdpServer{

    private Thread receiverThread;
    private final UdpListener listener;
    private UdpChannel connection;

    public UdpServer(UdpListener listener){
        this.listener = listener;
    }

    public synchronized UdpServer start(String ip, int port){
        if(connection != null && !connection.isClosed())
            throw new RuntimeException("Already enabled");

        try(final DatagramSocket socket = new DatagramSocket(port, InetAddress.getByName(ip))){
            connection = new UdpChannel(socket);

            receiverThread = new Thread(()->{
                while(!Thread.interrupted()){
                    if(connection.available() != 0)
                        listener.received(connection.nextPacket());

                    Thread.yield();
                }
            });

            receiverThread.setDaemon(true);
            receiverThread.setPriority(Thread.MIN_PRIORITY);
            receiverThread.start();

        }catch(Exception e){
            throw new RuntimeException("UdpServer startup error: " + e.getMessage());
        }

        return this;
    }

    public void send(byte[] data, SocketAddress address){
        connection.send(new DatagramPacket(data, data.length, address));
    }

    public void send(byte[] data, String ip, int port){
        send(data, new InetSocketAddress(ip, port));
    }

    public void close(){
        if(connection.isClosed())
            return;

        receiverThread.interrupt();
        connection.close();
    }

    public UdpChannel getConnection(){
        return connection;
    }

}
