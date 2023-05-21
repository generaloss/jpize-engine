package pize.tests.net;

import pize.net.NetChannel;
import pize.net.security.KeyRSA;
import pize.net.tcp.TcpByteChannel;
import pize.net.tcp.TcpListener;
import pize.net.tcp.TcpServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerSide implements TcpListener<byte[]>{
    
    public static void main(String[] args){
        new ServerSide();
    }
    
    
    private final TcpServer server;
    private final List<ClientConnection> connections;
    private final KeyRSA serverKey;
    
    public ServerSide(){
        server = new TcpServer(this);
        server.run("localhost", 5454);
        
        connections = new ArrayList<>();
        serverKey = new KeyRSA(1024);
        
        while(!Thread.interrupted());
        server.close();
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
                    System.out.println("   client->server ping: " + (System.nanoTime() - packet.getTime()) / 1000000F);
                    
                    packet.write((TcpByteChannel) sender);
                }
                
                default -> System.out.println("   received: ?Unknown Packet Type?");
            }
            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void connected(NetChannel<byte[]> channel){
        System.out.println("Client connected {");
        
        ClientConnection connection = new ClientConnection((TcpByteChannel) channel);
        connections.add(connection);
    }
    
    @Override
    public void disconnected(NetChannel<byte[]> channel){
        System.out.println("} Client disconnected\n");
    }
    
}
