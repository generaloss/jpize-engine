package pize.net.tcp;

import pize.net.NetChannel;
import pize.net.security.KeyAES;
import pize.util.Utils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedDeque;

public class TcpByteChannel extends NetChannel<byte[]>{
    
    private final Socket socket;
    private final Thread receiveThread;
    private final DataOutputStream outStream;
    private final ConcurrentLinkedDeque<byte[]> receivedQueue;
    private boolean closed;
    private KeyAES encodeKey = null;

    public TcpByteChannel(Socket socket) throws IOException{
        this.socket = socket;

        receivedQueue = new ConcurrentLinkedDeque<>();
        outStream = new DataOutputStream(socket.getOutputStream());

        receiveThread = new Thread(()->{
            try{
                final DataInputStream inStream = new DataInputStream(socket.getInputStream());
                
                while(!Thread.interrupted() && !closed){
                    Thread.yield();
                    
                    if(socket.isInputShutdown() || socket.isOutputShutdown() || socket.isClosed()){
                        System.out.println("in: " + socket.isInputShutdown() + ", out: " + socket.isOutputShutdown() + ", socket: " + socket.isClosed());
                        close();
                    }
                    
                    int length = inStream.readInt();
                    if(length == -1){
                        System.out.println("len: -1");
                        close();
                    }
                    else{
                        byte[] bytes = new byte[length];
                        // System.out.println("   reading " + length + " bytes");
                        inStream.readFully(bytes);
                        
                        if(encodeKey != null)
                            bytes = encodeKey.decrypt(bytes);
                        
                        receivedQueue.add(bytes);
                    }
                }
            }catch(IOException e){
                // System.err.println("Socket closed");
                setClosed();
            }
        });

        receiveThread.setPriority(Thread.MIN_PRIORITY);
        receiveThread.setDaemon(true);
        receiveThread.start();
    }
    
    @Override
    public int available(){
        return receivedQueue.size();
    }
    
    
    @Override
    public void send(byte[] packet){
        if(closed)
            return;

        try{
            if(encodeKey != null)
                packet = encodeKey.encrypt(packet);
            
            outStream.writeInt(packet.length);
            outStream.write(packet);
        }catch(IOException e){
            System.err.println("TcpByteChannel (send error): " + e.getMessage());
        }
    }
    
    
    public void send(ByteArrayOutputStream stream){
        if(closed)
            return;
        
        send(stream.toByteArray());
    }
    
    
    @Override
    public byte[] nextPacket(){
        return receivedQueue.poll();
    }
    
    @Override
    public void encode(KeyAES encodeKey){
        this.encodeKey = encodeKey;
    }
    
    @Override
    public boolean isClosed(){
        return closed;
    }

    @Override
    public void close(){
        if(closed)
            return;
        
        setClosed();
        receiveThread.interrupt();
        Utils.close(socket);
    }
    
    private void setClosed(){
        closed = true;
        // System.out.println("   Closed channel");
    }

}
