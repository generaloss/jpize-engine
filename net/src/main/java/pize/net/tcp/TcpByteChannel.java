package pize.net.tcp;

import pize.net.NetChannel;
import pize.net.security.KeyAES;
import pize.util.Utils;

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
    private KeyAES encodingKey = null;

    public TcpByteChannel(Socket socket) throws IOException{
        this.socket = socket;

        receivedQueue = new ConcurrentLinkedDeque<>();
        outStream = new DataOutputStream(socket.getOutputStream());

        receiveThread = new Thread(()->{
            try{
                final DataInputStream inStream = new DataInputStream(socket.getInputStream());
                
                while(!Thread.interrupted() && !closed){
                    Thread.yield();
                    
                    if(inStream.available() == 0)
                        continue;
                    
                    if(socket.isInputShutdown() || socket.isOutputShutdown() || socket.isClosed()){
                        System.out.println("in: " + socket.isInputShutdown() + ", out: " + socket.isOutputShutdown() + ", s: " + socket.isClosed());
                        close();
                    }
                    
                    int length = inStream.readInt();
                    if(length == -1){
                        System.out.println("len: -1");
                        close();
                    }
                    else if(length != 0 && inStream.available() != 0){
                        byte[] bytes = new byte[length];
                        
                        if(encodingKey != null)
                            bytes = encodingKey.decrypt(bytes);
                        
                        System.out.println("reading " + length + " bytes");
                        inStream.readFully(bytes);
                        System.out.println("read");
                        receivedQueue.add(bytes);
                        System.out.println("TIME RX: " + System.nanoTime());
                    }
                }
            }catch(IOException e){ // Socket closed
                System.err.println("Socket closed");
                setClosed();
            }
        });

        receiveThread.setPriority(Thread.MIN_PRIORITY);
        receiveThread.setDaemon(true);
        receiveThread.start();
    }

    @Override
    public void send(byte[] packet){
        if(closed)
            return;

        try{
            if(encodingKey != null)
                packet = encodingKey.encrypt(packet);
            
            outStream.writeInt(packet.length);
            outStream.write(packet);
        }catch(IOException e){
            System.err.println("TcpByteChannel (send error): " + e.getMessage());
        }
    }
    
    public DataOutputStream getOutputStream(){
        return outStream;
    }
    
    public Socket getSocket(){
        return socket;
    }
    
    public void encode(KeyAES encodingKey){
        this.encodingKey = encodingKey;
    }
    
    @Override
    public int available(){
        return receivedQueue.size();
    }

    @Override
    public byte[] nextPacket(){
        return receivedQueue.poll();
    }
    
    private void setClosed(){
        closed = true;
        System.out.println("Closed some channel");
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

}
