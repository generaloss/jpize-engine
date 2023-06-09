package pize.tests.voxelgame.client.net;

import pize.tests.voxelgame.client.NetClientGame;
import pize.tests.voxelgame.clientserver.net.PlayerProfile;
import megalul.projectvostok.clientserver.net.packet.*;
import pize.net.tcp.TcpChannel;
import pize.net.tcp.TcpListener;
import pize.net.tcp.packet.PacketInfo;
import pize.net.tcp.packet.Packets;
import pize.tests.voxelgame.clientserver.net.packet.*;

public class ClientPacketHandler implements TcpListener{ //} implements NetListener<byte[]>{
    
    private final NetClientGame gameOF;
    
    public ClientPacketHandler(NetClientGame gameOF){
        this.gameOF = gameOF;
    }
    
    public NetClientGame getGameOf(){
        return gameOF;
    }
    
    
    @Override
    public synchronized void received(byte[] bytes, TcpChannel sender){
        final PlayerProfile profile = gameOF.getSessionOf().getProfile();
        
        final PacketInfo packetInfo = Packets.getPacketInfo(bytes);
        if(packetInfo == null)
            return;
        switch(packetInfo.getPacketID()){
            
            case PacketBlockUpdate.PACKET_ID ->{
                final PacketBlockUpdate packet = packetInfo.readPacket(new PacketBlockUpdate());
                gameOF.getWorld().setBlock(packet.x, packet.y, packet.z, packet.state, true);
            }
            
            case PacketChunk.PACKET_ID ->{
                final PacketChunk packet = packetInfo.readPacket(new PacketChunk());
                gameOF.getWorld().getChunkManager().loadChunk(packet);
            }
            
            case PacketDisconnect.PACKET_ID ->{
                final PacketDisconnect packet = packetInfo.readPacket(new PacketDisconnect());
                
                gameOF.disconnect();
                System.out.println("[CLIENT]: соединение прервано: " + packet.reasonComponent);
            }
            
            case PacketEncryptStart.PACKET_ID ->{
                final PacketEncryptStart packet = packetInfo.readPacket(new PacketEncryptStart());
                
                byte[] encryptedClientKey = packet.publicServerKey.encrypt(gameOF.getEncryptKey().getKey().getEncoded());
                new PacketEncryptEnd(profile.getName(), encryptedClientKey).write(sender);
                
                sender.encode(gameOF.getEncryptKey());// * шифрование *
                sender.setTcpNoDelay(false);
                
                new PacketAuth(profile.getName(), gameOF.getSessionOf().getSessionToken()).write(sender);
                
                getGameOf().getWorld().getChunkManager().start();
            }
            
            case PacketPing.PACKET_ID -> {
                PacketPing packet = packetInfo.readPacket(new PacketPing());
                System.out.println("[CLIENT]: ping " + (System.nanoTime() - packet.timeMillis) / 1000000F + " ms");
            }
            
        }
    }
    
    @Override
    public void connected(TcpChannel channel){
    
    }
    
    @Override
    public void disconnected(TcpChannel channel){
    
    }
    
}
