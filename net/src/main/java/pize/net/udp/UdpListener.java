package pize.net.udp;

import java.net.DatagramPacket;

public interface UdpListener{
    
    void received(DatagramPacket packet);
    
}
