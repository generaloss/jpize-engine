package jpize.net.tcp;

import jpize.net.security.KeyAES;
import jpize.net.tcp.packet.IPacket;
import jpize.util.io.JpizeOutputStream;
import jpize.util.Utils;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class TcpConnection{
    
    private final Socket socket;
    private final DataOutputStream outStream;
    private final Thread receiveThread;
    private boolean closed;
    private KeyAES encodeKey;
    private final Disconnector disconnector;
    
    protected TcpConnection(Socket socket, TcpListener listener, Disconnector disconnector) throws IOException{
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
            }catch(IOException ignored){
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
    
    public void send(PacketWriter data){
        final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        final JpizeOutputStream dataStream = new JpizeOutputStream(byteStream);
        
        data.write(dataStream);
        send(byteStream);
    }

    public void send(IPacket<?> packet){
        send(dataStream -> {
            try{
                dataStream.writeInt(packet.getPacketID());
                packet.write(dataStream);
            }catch(IOException e){
                e.printStackTrace();
            }
        });
    }


    public Socket getSocket(){
        return socket;
    }

    public boolean isClosed(){
        return closed;
    }

    public boolean isConnected(){
        return socket.isConnected();
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
        disconnector.disconnect(this);
    }
    
    
    public void encode(KeyAES encodeKey){
        this.encodeKey = encodeKey;
    }

    public void setTcpNoDelay(boolean on){
        try{ socket.setTcpNoDelay(on); }catch(SocketException e){ e.printStackTrace(); }
    }

    public void setSoTimeout(int timeout){
        try{ socket.setSoTimeout(timeout); }catch(SocketException e){ e.printStackTrace(); }
    }

    public void setKeepAlive(boolean on){
        try{ socket.setKeepAlive(on); }catch(SocketException e){ e.printStackTrace(); }
    }

    public void setSendBufferSize(int size){
        try{ socket.setSendBufferSize(size); }catch(SocketException e){ e.printStackTrace(); }
    }

    public void setReceiveBufferSize(int size){
        try{ socket.setReceiveBufferSize(size); }catch(SocketException e){ e.printStackTrace(); }
    }

    public void setTrafficClass(int trafficClass){
        try{ socket.setTrafficClass(trafficClass); }catch(SocketException e){ e.printStackTrace(); }
    }

    public void setReuseAddress(boolean on){
        try{ socket.setReuseAddress(on); }catch(SocketException e){ e.printStackTrace(); }
    }

    public void setOOBInline(boolean on){
        try{ socket.setOOBInline(on); }catch(SocketException e){ e.printStackTrace(); }
    }

    public void setSoLinger(boolean on, int linger){
        try{ socket.setSoLinger(on, linger); }catch(SocketException e){ e.printStackTrace(); }
    }


    public int getPort(){
        return socket.getPort();
    }

    public int getLocalPort(){
        return socket.getLocalPort();
    }

    public InetAddress getAddress(){
        return socket.getInetAddress();
    }

    public InetAddress getLocalAddress(){
        return socket.getLocalAddress();
    }

    public boolean getTcpNoDelay(){
        try{ return socket.getTcpNoDelay(); }catch(SocketException e){ throw new RuntimeException(e); }
    }

    public int getSoTimeout(){
        try{ return socket.getSoTimeout(); }catch(SocketException e){ throw new RuntimeException(e); }
    }

    public boolean getKeepAlive(){
        try{ return socket.getKeepAlive(); }catch(SocketException e){ throw new RuntimeException(e); }
    }

    public int getSendBufferSize(){
        try{ return socket.getSendBufferSize(); }catch(SocketException e){ throw new RuntimeException(e); }
    }

    public int getReceiveBufferSize(){
        try{ return socket.getReceiveBufferSize(); }catch(SocketException e){ throw new RuntimeException(e); }
    }

    public int getTrafficClass(){
        try{ return socket.getTrafficClass(); }catch(SocketException e){ throw new RuntimeException(e); }
    }

    public boolean getReuseAddress(){
        try{ return socket.getReuseAddress(); }catch(SocketException e){ throw new RuntimeException(e); }
    }

    public boolean getOOBInline(){
        try{ return socket.getOOBInline(); }catch(SocketException e){ throw new RuntimeException(e); }
    }

    public int getSoLinger(){
        try{ return socket.getSoLinger(); }catch(SocketException e){ throw new RuntimeException(e); }
    }


    protected interface Disconnector{
        void disconnect(TcpConnection connection);
    }

}
