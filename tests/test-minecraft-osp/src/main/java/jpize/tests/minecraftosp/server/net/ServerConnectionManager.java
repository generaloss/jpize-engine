package jpize.tests.minecraftosp.server.net;

import jpize.net.security.KeyRSA;
import jpize.net.tcp.TcpConnection;
import jpize.net.tcp.TcpListener;
import jpize.net.tcp.packet.PacketHandler;
import jpize.net.tcp.packet.PacketInfo;
import jpize.net.tcp.packet.Packets;
import jpize.tests.minecraftosp.main.net.packet.*;
import jpize.tests.minecraftosp.server.player.ServerPlayer;
import jpize.tests.minecraftosp.main.text.Component;
import jpize.tests.minecraftosp.main.text.TextColor;
import jpize.tests.minecraftosp.server.Server;

import java.util.HashMap;
import java.util.Map;

public class ServerConnectionManager implements TcpListener{
    
    private final Server server;
    private final Map<TcpConnection, PacketHandler> packetHandlerMap;
    private final KeyRSA rsaKey;
    
    public ServerConnectionManager(Server server){
        this.server = server;
        this.packetHandlerMap = new HashMap<>();
        this.rsaKey = new KeyRSA(1024);
    }
    
    public Server getServer(){
        return server;
    }
    
    public KeyRSA getRsaKey(){
        return rsaKey;
    }
    
    public void setPacketHandler(TcpConnection connection, PacketHandler packetHandler){
        packetHandlerMap.put(connection, packetHandler);
    }
    
    
    @Override
    public synchronized void received(byte[] bytes, TcpConnection sender){
        final PacketInfo packetInfo = Packets.getPacketInfo(bytes);
        if(packetInfo == null)
            return;
        
        final PacketHandler packetHandler = packetHandlerMap.get(sender);
        
        switch(packetInfo.getPacketID()){
            // Login
            case SBPacketAuth.PACKET_ID ->
                packetInfo.readPacket(new SBPacketAuth()) .handle((PlayerLoginConnection) packetHandler);
            
            case SBPacketEncryptEnd.PACKET_ID ->
                packetInfo.readPacket(new SBPacketEncryptEnd()) .handle((PlayerLoginConnection) packetHandler);
            
            case SBPacketLogin.PACKET_ID ->
                packetInfo.readPacket(new SBPacketLogin()) .handle((PlayerLoginConnection) packetHandler);
            
            // Game
            case SBPacketChunkRequest.PACKET_ID ->
                packetInfo.readPacket(new SBPacketChunkRequest()) .handle((PlayerGameConnection) packetHandler);
                
            case SBPacketPlayerBlockSet.PACKET_ID ->
                packetInfo.readPacket(new SBPacketPlayerBlockSet()) .handle((PlayerGameConnection) packetHandler);
            
            case SBPacketMove.PACKET_ID ->
                packetInfo.readPacket(new SBPacketMove()) .handle((PlayerGameConnection) packetHandler);
            
            case SBPacketRenderDistance.PACKET_ID ->
                packetInfo.readPacket(new SBPacketRenderDistance()) .handle((PlayerGameConnection) packetHandler);
            
            case SBPacketPlayerSneaking.PACKET_ID ->
                packetInfo.readPacket(new SBPacketPlayerSneaking()) .handle((PlayerGameConnection) packetHandler);
            
            case SBPacketChatMessage.PACKET_ID ->
                packetInfo.readPacket(new SBPacketChatMessage()) .handle((PlayerGameConnection) packetHandler);
            
            // Ping
            case SBPacketPing.PACKET_ID -> {
                final SBPacketPing packet = packetInfo.readPacket(new SBPacketPing());
                new CBPacketPong(packet.timeNanos).write(sender);
            }
        }
    }
    
    @Override
    public void connected(TcpConnection connection){
        setPacketHandler(connection, new PlayerLoginConnection(server, connection));
    }
    
    @Override
    public void disconnected(TcpConnection connection){
        final PacketHandler packetHandler = packetHandlerMap.get(connection);
        if(packetHandler instanceof PlayerGameConnection connectionAdapter){
            final ServerPlayer player = connectionAdapter.getPlayer();
            server.getPlayerList().disconnectPlayer(player);
            
            player.sendToChat(new Component().color(TextColor.YELLOW).text("Player " + player.getName() + " leave the game"));
        }
        
        packetHandlerMap.remove(connection);
    }
    
}
