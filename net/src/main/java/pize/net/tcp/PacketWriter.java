package pize.net.tcp;

import pize.util.io.PizeOutputStream;

@FunctionalInterface
public interface PacketWriter{
    
    void write(PizeOutputStream dataStream);
    
}
