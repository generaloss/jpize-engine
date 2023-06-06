package pize.net.tcp;

import pize.net.security.KeyAES;
import pize.util.Utils;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class TcpChannel{
    
    private final Socket socket;
    private final DataOutputStream outStream;
    private final Thread receiveThread;
    private boolean closed;
    private KeyAES encodeKey;
    private final TcpDisconnector disconnector;
    
    public TcpChannel(Socket socket, TcpListener listener, TcpDisconnector disconnector) throws IOException{
        this.socket = socket;
        this.disconnector = disconnector;
        
        outStream = new DataOutputStream(socket.getOutputStream());

        receiveThread = new Thread(()->{
            try(final DataInputStream inStream = new DataInputStream(socket.getInputStream())){
                
                while(!Thread.interrupted() && !closed){
                    final int length = inStream.readInt();
                    byte[] bytes = inStream.readNBytes(length);
                    
                    if(encodeKey != null)
                        bytes = encodeKey.decrypt(bytes);
                    
                    listener.received(bytes, this);
                }
            }catch(IOException e){
                setClosed();
            }
        });

        receiveThread.setPriority(Thread.MIN_PRIORITY);
        receiveThread.setDaemon(true);
        receiveThread.start();
    }
    
    public void send(byte[] bytes){
        if(socket.isOutputShutdown())
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
    
    private final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    private final DataOutputStream dataStream = new DataOutputStream(byteStream);
    
    public void send(DataWriter data){
        data.write(dataStream);
        send(byteStream);
        
        byteStream.reset();
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
