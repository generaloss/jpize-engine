package pize.tests.net;

import pize.net.security.KeyAES;
import pize.net.security.KeyRSA;
import pize.net.tcp.TcpByteChannel;
import pize.net.tcp.TcpListener;
import pize.net.tcp.TcpServer;
import pize.tests.net.packet.EncodePacket;
import pize.tests.net.packet.MessagePacket;
import pize.tests.net.packet.Utils;
import pize.tests.net.packet.PingPacket;

import java.io.IOException;

public class ServerSide implements TcpListener{
    
    public static void main(String[] args){
        new ServerSide();
    }
    
    
    private final TcpServer server;
    private final KeyRSA key;
    
    public ServerSide(){
        key = new KeyRSA(2048);
        
        server = new TcpServer(this);
        server.run("localhost", 5454);
        
        while(!Thread.interrupted());
        server.close();
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
                    System.out.println("   client->server ping: " + (System.nanoTime() - packet.getTime()) / 1000000F);
                    
                    System.out.println("   2 time: " + String.valueOf(System.currentTimeMillis()).substring(9) );
                    
                    packet.write(sender);
                }
                
                case EncodePacket.PACKET_TYPE_ID -> {
                    final EncodePacket packet = new EncodePacket();
                    packet.read(Utils.stream);
                    
                    KeyAES clientKey = new KeyAES(key.decrypt(packet.getKey()));
                    System.out.println("   client's key received");
                    
                    sender.encode(clientKey);
                    System.out.println("   encoded with key (hash): " + clientKey.getKey().hashCode());
                }
                
                default -> System.out.println("   received: ?Unknown Packet Type? (" + Utils.packetTypeID + ")");
            }
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
        // server.disconnect(sender);
    }
    
    @Override
    public void connected(TcpByteChannel channel){
        System.out.println("Client connected {");
        
        new EncodePacket(key.getPublic().getKey().getEncoded()).write(channel);
        System.out.println("   send public key");
    }
    
    @Override
    public void disconnected(TcpByteChannel channel){
        System.out.println("   Client disconnected\n}");
    }
    
}
