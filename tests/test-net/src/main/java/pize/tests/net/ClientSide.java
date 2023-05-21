package pize.tests.net;

import pize.net.NetChannel;
import pize.net.tcp.TcpByteChannel;
import pize.net.tcp.TcpClient;
import pize.net.tcp.TcpListener;
import pize.util.Utils;

import java.io.IOException;

public class ClientSide implements TcpListener<byte[]>{
    
    public static void main(String[] args){
        new ClientSide();
    }
    
    
    private final TcpClient client;
    
    public ClientSide(){
        client = new TcpClient(this);
        client.connect("localhost", 5454);
        
        while(!Thread.interrupted());
    }
    
    @Override
    public void received(byte[] data, NetChannel<byte[]> sender){
        try{
            PacketUtils.receive(data);
            switch(PacketUtils.packetTypeID){
                
                case MessagePacket.PACKET_TYPE_ID -> {
                    final MessagePacket packet = new MessagePacket();
                    packet.read(PacketUtils.stream);
                    System.out.println("   message: " + packet.getMessage());
                }
                
                case PingPacket.PACKET_TYPE_ID -> {
                    final PingPacket packet = new PingPacket();
                    packet.read(PacketUtils.stream);
                    
                    System.out.println("   ping: " + ((System.nanoTime() - packet.getTime()) / 1000000F) + " ms");
                    
                    Utils.delayElapsed(1000);
                    final PingPacket ping = new PingPacket(System.nanoTime());
                    ping.write((TcpByteChannel) sender);
                    System.out.println("TIME TX: " + System.nanoTime());
                }
                
                default -> System.out.println("   received: ?Unknown Packet Type?");
            }
            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void connected(NetChannel<byte[]> channel){
        System.out.println("Connected {");
        
        new PingPacket(System.nanoTime()).write(client.getChannel());
        System.out.println("   send first packet");
    }
    
    @Override
    public void disconnected(NetChannel<byte[]> channel){
        System.out.println("? Disconnected");
    }
    
}
