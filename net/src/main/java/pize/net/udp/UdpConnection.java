package pize.net.udp;

import pize.net.NetConnection;
import pize.util.MTArrayList;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;

public class UdpConnection implements NetConnection<DatagramPacket>{

    private final DatagramSocket socket;
    private final MTArrayList<DatagramPacket> received;
    private final Thread thread;
    private boolean closed;

    public UdpConnection(DatagramSocket socket){
        this.socket = socket;

        closed = false;
        received = new MTArrayList<>();

        thread = new Thread(()->{
            try{
                while(!Thread.interrupted()){

                    DatagramPacket sizePacket = new DatagramPacket(new byte[4], 4);
                    socket.receive(sizePacket);

                    int length = ByteBuffer.wrap(sizePacket.getData()).getInt();

                    DatagramPacket packet = new DatagramPacket(new byte[length], length);
                    socket.receive(packet);

                    received.add(packet);

                    Thread.yield();
                }
            }catch(Exception ignored){
            }
        });

        thread.setPriority(Thread.MIN_PRIORITY);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void send(DatagramPacket packet){
        if(closed)
            return;

        try{
            byte[] length = ByteBuffer.allocate(4).putInt(packet.getLength()).array();
            DatagramPacket sizePacket = new DatagramPacket(length, 4, packet.getSocketAddress());
            socket.send(sizePacket);

            socket.send(packet);
        }catch(IOException ignored){
        }
    }

    @Override
    public int available(){
        return received.size();
    }

    @Override
    public DatagramPacket next(){
        DatagramPacket packet = received.getLast();
        received.remove(packet);
        return packet;
    }

    @Override
    public boolean isClosed(){
        return closed;
    }

    @Override
    public void close(){
        thread.interrupt();
        socket.close();
        closed = true;
    }

}
