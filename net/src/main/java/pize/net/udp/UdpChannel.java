package pize.net.udp;

import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;

public class UdpChannel{

    private final DatagramSocket socket;
    private final Thread thread;
    private boolean closed;

    public UdpChannel(DatagramSocket socket, UdpListener listener){
        this.socket = socket;

        closed = false;

        thread = new Thread(()->{
            try{
                while(!Thread.interrupted()){
                    final DatagramPacket sizePacket = new DatagramPacket(new byte[4], 4);
                    socket.receive(sizePacket);

                    int length = ByteBuffer.wrap(sizePacket.getData()).getInt();
                    
                    final DatagramPacket packet = new DatagramPacket(new byte[length], length);
                    socket.receive(packet);
                    
                    listener.received(packet);

                    Thread.yield();
                }
            }catch(Exception ignored){ }
        });

        thread.setPriority(Thread.MIN_PRIORITY);
        thread.setDaemon(true);
        thread.start();
    }

    public void send(DatagramPacket packet){
        if(closed)
            return;

        try{
            final byte[] length = BufferUtils.createByteBuffer(4).putInt(packet.getLength()).array();
            final DatagramPacket sizePacket = new DatagramPacket(length, 4, packet.getSocketAddress());
            socket.send(sizePacket);

            socket.send(packet);
        }catch(IOException ignored){ }
    }

    public boolean isClosed(){
        return closed;
    }

    public void close(){
        thread.interrupt();
        socket.close();
        closed = true;
    }

}
