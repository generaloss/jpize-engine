package pize.net;

import pize.net.security.KeyAES;

import java.io.Closeable;

public abstract class NetChannel<P> implements Closeable{
    
    public abstract int available();
    
    public boolean isAvailable(){
        return available() != 0 && !isClosed();
    }
    
    public abstract P nextPacket();
    
    public abstract void send(P p);
    
    public abstract void encode(KeyAES encryptKey);
    
    public abstract boolean isClosed();
    
    public abstract void close();

}
