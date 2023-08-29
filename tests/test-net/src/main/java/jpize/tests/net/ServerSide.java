package jpize.tests.net;

import jpize.net.security.KeyAES;
import jpize.net.security.KeyRSA;
import jpize.net.tcp.TcpConnection;
import jpize.net.tcp.TcpListener;
import jpize.net.tcp.TcpServer;
import jpize.net.tcp.packet.PacketInfo;
import jpize.net.tcp.packet.Packets;
import jpize.tests.net.packet.EncodePacket;
import jpize.tests.net.packet.MessagePacket;
import jpize.tests.net.packet.PingPacket;

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
    public void received(byte[] bytes, TcpConnection sender){
        try{
            final PacketInfo packetInfo = Packets.getPacketInfo(bytes);
            if(packetInfo == null){
                System.out.println("   received not packet");
                return;
            }
            
            switch(packetInfo.getPacketID()){
                case MessagePacket.PACKET_TYPE_ID -> {
                    final MessagePacket packet = packetInfo.readPacket(new MessagePacket());
                    System.out.println("   message: " + packet.getMessage());
                }
                
                case PingPacket.PACKET_TYPE_ID -> {
                    final PingPacket packet = packetInfo.readPacket(new PingPacket());
                    System.out.println("   client->server ping: " + (System.nanoTime() - packet.getTime()) / 1000000F);
                    packet.write(sender);
                }
                
                case EncodePacket.PACKET_TYPE_ID -> {
                    final EncodePacket packet = packetInfo.readPacket(new EncodePacket());
                    
                    final KeyAES clientKey = new KeyAES(key.decrypt(packet.getKey()));
                    System.out.println("   client's key received");
                    
                    sender.encode(clientKey);
                    System.out.println("   encoded with key (hash): " + clientKey.getKey().hashCode());
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void connected(TcpConnection connection){
        System.out.println("Client connected {");
        new EncodePacket(key.getPublic().getKey().getEncoded()).write(connection);
        System.out.println("   send public key");
    }
    
    @Override
    public void disconnected(TcpConnection connection){
        System.out.println("   Client disconnected\n}");
    }
    
}
