package jpize.net.tcp;

import jpize.util.io.JpizeOutputStream;

@FunctionalInterface
public interface PacketWriter{
    
    void write(JpizeOutputStream dataStream);
    
}
