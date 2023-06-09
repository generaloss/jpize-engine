package pize.net.tcp;

import pize.net.tcp.packet.PacketOutputStream;

@FunctionalInterface
public interface PacketWriter{
    
    void write(PacketOutputStream dataStream);
    
}
