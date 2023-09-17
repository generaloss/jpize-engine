package jpize.tests.minecraftose.client.net;

import jpize.Jpize;
import jpize.net.tcp.TcpConnection;
import jpize.net.tcp.TcpListener;
import jpize.net.tcp.packet.PacketDispatcher;
import jpize.tests.minecraftose.client.ClientGame;
import jpize.tests.minecraftose.main.net.packet.clientbound.*;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientConnectionManager implements TcpListener{

    public static int rx;
    public static int rxCounter;

    private final ClientGame game;
    private final PacketDispatcher dispatcher;
    private final ConcurrentLinkedQueue<byte[]> receivedQueue;
    private final ClientConnection handler;

    public ClientConnectionManager(ClientGame game){
        this.game = game;
        this.handler = new ClientConnection(game);
        this.dispatcher = new PacketDispatcher();
        this.receivedQueue = new ConcurrentLinkedQueue<>();
        registerPackets();

        // Handle packets
        final Thread thread = new Thread(() -> {
            while(!Thread.interrupted()){
                Thread.yield();
                handlePackets();
            }
        });
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.setDaemon(true);
        thread.start();
    }
    
    public ClientGame getGame(){
        return game;
    }


    private void registerPackets(){
        dispatcher.register(CBPacketAbilities      .PACKET_ID, CBPacketAbilities      .class);
        dispatcher.register(CBPacketBlockUpdate    .PACKET_ID, CBPacketBlockUpdate    .class);
        dispatcher.register(CBPacketChatMessage    .PACKET_ID, CBPacketChatMessage    .class);
        dispatcher.register(CBPacketChunk          .PACKET_ID, CBPacketChunk          .class);
        dispatcher.register(CBPacketDisconnect     .PACKET_ID, CBPacketDisconnect     .class);
        dispatcher.register(CBPacketEncryptStart   .PACKET_ID, CBPacketEncryptStart   .class);
        dispatcher.register(CBPacketEntityMove     .PACKET_ID, CBPacketEntityMove     .class);
        dispatcher.register(CBPacketLightUpdate    .PACKET_ID, CBPacketLightUpdate    .class);
        dispatcher.register(CBPacketPlayerSneaking .PACKET_ID, CBPacketPlayerSneaking .class);
        dispatcher.register(CBPacketPlaySound      .PACKET_ID, CBPacketPlaySound      .class);
        dispatcher.register(CBPacketPong           .PACKET_ID, CBPacketPong           .class);
        dispatcher.register(CBPacketRemoveEntity   .PACKET_ID, CBPacketRemoveEntity   .class);
        dispatcher.register(CBPacketSpawnEntity    .PACKET_ID, CBPacketSpawnEntity    .class);
        dispatcher.register(CBPacketSpawnInfo      .PACKET_ID, CBPacketSpawnInfo      .class);
        dispatcher.register(CBPacketSpawnPlayer    .PACKET_ID, CBPacketSpawnPlayer    .class);
        dispatcher.register(CBPacketTeleportPlayer .PACKET_ID, CBPacketTeleportPlayer .class);
        dispatcher.register(CBPacketTime           .PACKET_ID, CBPacketTime           .class);
    }


    public void handlePackets(){
        while(!receivedQueue.isEmpty()){
            final byte[] bytes = receivedQueue.poll();

            final boolean handled = dispatcher.handlePacket(bytes, handler);
            if(handled)
                rxCounter++;
        }
    }
    
    
    @Override
    public void received(byte[] bytes, TcpConnection sender){
        receivedQueue.add(bytes);
    }
    
    @Override
    public void connected(TcpConnection connection){
        handler.setConnection(connection);
    }
    
    @Override
    public void disconnected(TcpConnection connection){
        Jpize.exit();
    }
    
}
