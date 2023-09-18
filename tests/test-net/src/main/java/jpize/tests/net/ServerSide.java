package jpize.tests.net;

import jpize.net.security.KeyAES;
import jpize.net.security.KeyRSA;
import jpize.net.tcp.TcpConnection;
import jpize.net.tcp.TcpListener;
import jpize.net.tcp.TcpServer;
import jpize.net.tcp.packet.PacketDispatcher;
import jpize.tests.net.handler.MyPacketHandler;
import jpize.tests.net.packet.EncodePacket;
import jpize.tests.net.packet.MessagePacket;
import jpize.tests.net.packet.PingPacket;

public class ServerSide implements TcpListener{
    
    public static void main(String[] args){
        new ServerSide();
    }

    private final KeyRSA key;
    private final MyPacketHandler handler;
    private final PacketDispatcher dispatcher;
    private TcpConnection sender;
    
    public ServerSide(){
        // Create handler with methods for handle packets (EncodePacket, MessagePacket, PingPacket)
        handler = new MyPacketHandler(){
            public void encode(EncodePacket packet){
                final KeyAES clientKey = new KeyAES(key.decrypt(packet.getKey()));
                System.out.println("   client's key received");

                sender.encode(clientKey);
                System.out.println("   encoded with key (hash): " + clientKey.getKey().hashCode());
            }

            public void message(MessagePacket packet){
                System.out.println("   message: " + packet.getMessage());
            }

            public void ping(PingPacket packet){
                System.out.println("   client->server ping: " + (System.nanoTime() - packet.getTime()) / 1000000F);
                sender.send(packet);
            }
        };

        // Register packets
        dispatcher = new PacketDispatcher();
        dispatcher.register(EncodePacket .PACKET_ID, EncodePacket .class);
        dispatcher.register(MessagePacket.PACKET_ID, MessagePacket.class);
        dispatcher.register(PingPacket   .PACKET_ID, PingPacket   .class);

        // Key for encoding client key
        key = new KeyRSA(2048);

        // Run Server
        TcpServer server = new TcpServer(this);
        server.run("localhost", 5454);
        
        while(!Thread.interrupted());
        server.close();
    }

    @Override
    public void received(byte[] bytes, TcpConnection sender){
        // Set sender (Haram, not halal)
        this.sender = sender;

        // Handle packet with handler
        dispatcher.handlePacket(bytes, handler);
    }

    @Override
    public void connected(TcpConnection connection){
        System.out.println("Client connected {");
        connection.send(new EncodePacket(key.getPublic().getKey().getEncoded()));
        System.out.println("   send public key");
    }

    @Override
    public void disconnected(TcpConnection connection){
        System.out.println("   Client disconnected\n}");
    }
    
}
