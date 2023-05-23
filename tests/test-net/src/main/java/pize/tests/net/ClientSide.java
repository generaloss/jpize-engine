package pize.tests.net;

import pize.net.security.KeyAES;
import pize.net.security.PublicRSA;
import pize.net.tcp.TcpByteChannel;
import pize.net.tcp.TcpClient;
import pize.net.tcp.TcpListener;
import pize.tests.net.packet.EncodePacket;
import pize.tests.net.packet.MessagePacket;
import pize.tests.net.packet.Utils;
import pize.tests.net.packet.PingPacket;

import java.io.IOException;

public class ClientSide implements TcpListener{
    
    public static void main(String[] args){
        new ClientSide();
    }
    
    
    private final TcpClient client;
    private final KeyAES key;
    
    public ClientSide(){
        key = new KeyAES(256);
        
        client = new TcpClient(this);
        client.connect("localhost", 5454);
        
        while(!Thread.interrupted());
    }
    
    @Override
    public void received(byte[] data, TcpByteChannel sender){
        
        try{
            Utils.receive(data);
            switch(Utils.packetTypeID){
                
                case MessagePacket.PACKET_TYPE_ID -> {
                    final MessagePacket packet = new MessagePacket();
                    packet.read(Utils.stream);
                    System.out.println("   message: " + packet.getMessage());
                }
                
                case PingPacket.PACKET_TYPE_ID -> {
                    final PingPacket packet = new PingPacket();
                    packet.read(Utils.stream);
                    
                    System.out.println("   ping: " + ((System.nanoTime() - packet.getTime()) / 1000000F) + " ms");
                    
                    pize.util.Utils.delayElapsed(1000);
                    final PingPacket ping = new PingPacket(System.nanoTime());
                    ping.write(sender);
                    System.out.println("   1 time: " + String.valueOf(System.currentTimeMillis()).substring(9) );
                }
                
                case EncodePacket.PACKET_TYPE_ID -> {
                    final EncodePacket packet = new EncodePacket();
                    packet.read(Utils.stream);
                    
                    final PublicRSA serverKey = new PublicRSA(packet.getKey());
                    System.out.println("   server's public key received");
                    
                    new EncodePacket(serverKey.encrypt(key.getKey().getEncoded())).write(sender);
                    System.out.println("   send encrypted key");
                    
                    sender.encode(key);
                    System.out.println("   encoded with key (hash): " + key.getKey().hashCode());
                    
                    pize.util.Utils.delayElapsed(100);
                    new PingPacket(System.nanoTime()).write(client.getChannel());
                }
                
                default -> System.out.println("   received: ?Unknown Packet Type? (" + Utils.packetTypeID + ")");
            }
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void connected(TcpByteChannel channel){
        System.out.println("Connected {");
        
        // new PingPacket(System.nanoTime()).write(client.getChannel());
        // System.out.println("   send first packet");
    }
    
    @Override
    public void disconnected(TcpByteChannel channel){
        System.out.println("   Client disconnected\n}");
    }
    
}
