package jpize.tests.minecraftose.server.net;

import jpize.net.security.KeyRSA;
import jpize.net.tcp.TcpConnection;
import jpize.net.tcp.TcpListener;
import jpize.net.tcp.packet.PacketDispatcher;
import jpize.net.tcp.packet.PacketHandler;
import jpize.tests.minecraftose.main.net.packet.serverbound.*;
import jpize.tests.minecraftose.main.text.Component;
import jpize.tests.minecraftose.main.text.TextColor;
import jpize.tests.minecraftose.server.Server;
import jpize.tests.minecraftose.server.player.ServerPlayer;

import java.util.HashMap;
import java.util.Map;

public class ServerConnectionManager implements TcpListener{
    
    private final Server server;
    private final Map<TcpConnection, PacketHandler> handlerMap;
    private final PacketDispatcher dispatcher;
    private final KeyRSA rsaKey;
    
    public ServerConnectionManager(Server server){
        this.server = server;
        this.handlerMap = new HashMap<>();
        this.dispatcher = new PacketDispatcher();
        this.rsaKey = new KeyRSA(1024);
        registerPackets();
    }
    
    public Server getServer(){
        return server;
    }
    
    public KeyRSA getRsaKey(){
        return rsaKey;
    }


    private void registerPackets(){
        dispatcher.register(SBPacketAuth           .PACKET_ID, SBPacketAuth           .class);
        dispatcher.register(SBPacketChatMessage    .PACKET_ID, SBPacketChatMessage    .class);
        dispatcher.register(SBPacketChunkRequest   .PACKET_ID, SBPacketChunkRequest   .class);
        dispatcher.register(SBPacketEncryptEnd     .PACKET_ID, SBPacketEncryptEnd     .class);
        dispatcher.register(SBPacketLogin          .PACKET_ID, SBPacketLogin          .class);
        dispatcher.register(SBPacketMove           .PACKET_ID, SBPacketMove           .class);
        dispatcher.register(SBPacketPing           .PACKET_ID, SBPacketPing           .class);
        dispatcher.register(SBPacketPlayerBlockSet .PACKET_ID, SBPacketPlayerBlockSet .class);
        dispatcher.register(SBPacketPlayerSneaking .PACKET_ID, SBPacketPlayerSneaking .class);
        dispatcher.register(SBPacketRenderDistance .PACKET_ID, SBPacketRenderDistance .class);
    }
    
    
    @Override
    public synchronized void received(byte[] bytes, TcpConnection sender){
        final PacketHandler packetHandler = handlerMap.get(sender);
        if(packetHandler == null)
            return;

        dispatcher.handlePacket(bytes, packetHandler);
    }
    
    @Override
    public void connected(TcpConnection connection){
        setHandlerForConnection(connection, new PlayerLoginConnection(server, connection));
    }

    public void setHandlerForConnection(TcpConnection connection, PacketHandler handler){
        handlerMap.put(connection, handler);
    }
    
    @Override
    public void disconnected(TcpConnection connection){
        final PacketHandler packetHandler = handlerMap.get(connection);

        if(packetHandler instanceof PlayerGameConnection connectionAdapter){
            final ServerPlayer player = connectionAdapter.getPlayer();
            server.getPlayerList().disconnectPlayer(player);
            
            player.sendToChat(new Component().color(TextColor.YELLOW).text("Player " + player.getName() + " leave the game"));
        }
        
        handlerMap.remove(connection);
    }

}
