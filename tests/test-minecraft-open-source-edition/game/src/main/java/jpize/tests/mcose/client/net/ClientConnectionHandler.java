package jpize.tests.mcose.client.net;

import jpize.Jpize;
import jpize.net.tcp.TcpConnection;
import jpize.net.tcp.TcpListener;
import jpize.net.tcp.packet.IPacket;
import jpize.net.tcp.packet.PacketDispatcher;
import jpize.tests.mcose.client.ClientGame;
import jpize.tests.mcose.main.net.packet.clientbound.*;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientConnectionHandler implements TcpListener{

    private final ClientGame game;
    private TcpConnection connection;
    private final PacketDispatcher dispatcher;
    private final ConcurrentLinkedQueue<byte[]> receivedQueue;
    private final ClientPacketHandler handler;

    private int txCounter, rxCounter, tx, rx;

    public ClientConnectionHandler(ClientGame game){
        this.game = game;
        this.handler = new ClientPacketHandler(this);
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


    public TcpConnection getConnection(){
        return connection;
    }

    public void sendPacket(IPacket<?> packet){
        connection.send(packet);
        txCounter++;
    }


    @Override
    public void received(byte[] bytes, TcpConnection sender){
        receivedQueue.add(bytes);
        rxCounter++;
    }
    
    @Override
    public void connected(TcpConnection connection){
        this.connection = connection;
    }
    
    @Override
    public void disconnected(TcpConnection connection){
        Jpize.exit();
    }


    public void handlePackets(){
        while(!receivedQueue.isEmpty()){
            final byte[] bytes = receivedQueue.poll();

            final boolean handled = dispatcher.handlePacket(bytes, handler);
            if(handled)
                rxCounter++;
        }
    }


    public void countTxRx(){
        tx = txCounter;
        txCounter = 0;
        tx = txCounter;
        rxCounter = 0;
    }

    public int getTX(){
        return tx;
    }

    public int getRX(){
        return rx;
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
    
}
