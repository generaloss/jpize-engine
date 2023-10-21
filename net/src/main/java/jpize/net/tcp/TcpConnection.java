package jpize.net.tcp;

import jpize.net.security.KeyAES;
import jpize.net.tcp.packet.IPacket;
import jpize.util.io.JpizeOutputStream;
import jpize.util.Utils;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class TcpConnection{
    
    private final Socket socket;
    private final DataOutputStream outStream;
    private final Thread receiveThread;
    private boolean closed;
    private KeyAES encodeKey;
    private final TcpDisconnector disconnector;
    
    public TcpConnection(Socket socket, TcpListener listener, TcpDisconnector disconnector) throws IOException{
        this.socket = socket;
        this.disconnector = disconnector;
        
        outStream = new DataOutputStream(socket.getOutputStream());

        receiveThread = new Thread(()->{
            try(final DataInputStream inStream = new DataInputStream(socket.getInputStream())){
                
                while(!Thread.interrupted() && !closed){
                    final int length = inStream.readInt();
                    if(length < 0)
                        continue;
                    
                    byte[] bytes = inStream.readNBytes(length);
                    
                    if(encodeKey != null){
                        bytes = encodeKey.decrypt(bytes);
                        if(bytes == null)
                            continue;
                    }
                    
                    listener.received(bytes, this);
                }
            }catch(IOException e){
                setClosed();
            }
        }, "TcpConnection-Thread");

        receiveThread.setPriority(Thread.MIN_PRIORITY);
        receiveThread.setDaemon(true);
        receiveThread.start();
    }
    
    public synchronized void send(byte[] bytes){
        if(closed)
            return;

        try{
            if(encodeKey != null)
                bytes = encodeKey.encrypt(bytes);
            
            outStream.writeInt(bytes.length);
            outStream.write(bytes);
        }catch(IOException ignored){ }
    }
    
    public void send(ByteArrayOutputStream stream){
        send(stream.toByteArray());
    }
    
    public synchronized void send(PacketWriter data){
        final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        final JpizeOutputStream dataStream = new JpizeOutputStream(byteStream);
        
        data.write(dataStream);
        send(byteStream);
    }

    public void send(IPacket packet){
        send(dataStream->{
            try{
                dataStream.writeShort(packet.getPacketID());
                packet.write(dataStream);
                // System.out.println("Write packet " + packetID + " (size: " + dataStream.size() + ")");
            }catch(IOException e){
                e.printStackTrace();
            }
        });
    }
    
    
    public void encode(KeyAES encodeKey){
        this.encodeKey = encodeKey;
    }
    
    
    public Socket getSocket(){
        return socket;
    }
    
    public void setTcpNoDelay(boolean on){
        try{
            socket.setTcpNoDelay(on);
        }catch(SocketException ignored){ }
    }
    
    
    public boolean isClosed(){
        return closed;
    }

    public void close(){
        if(closed)
            return;
        
        setClosed();
        receiveThread.interrupt();
        Utils.close(socket);
    }
    
    private void setClosed(){
        closed = true;
        disconnector.disconnected(this);
    }

}