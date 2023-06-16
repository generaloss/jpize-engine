package pize.tests.voxelgame.client.net;

import pize.tests.voxelgame.client.ClientGame;
import pize.tests.voxelgame.client.entity.LocalPlayer;
import pize.tests.voxelgame.client.entity.RemotePlayer;
import pize.tests.voxelgame.clientserver.entity.Entity;
import pize.tests.voxelgame.clientserver.net.PlayerProfile;
import pize.net.tcp.TcpChannel;
import pize.net.tcp.TcpListener;
import pize.net.tcp.packet.PacketInfo;
import pize.net.tcp.packet.Packets;
import pize.tests.voxelgame.clientserver.net.packet.*;

public class ClientPacketHandler implements TcpListener{ //} implements NetListener<byte[]>{
    
    private final ClientGame gameOF;
    
    public ClientPacketHandler(ClientGame gameOF){
        this.gameOF = gameOF;
    }
    
    public ClientGame getGameOf(){
        return gameOF;
    }
    
    
    @Override
    public synchronized void received(byte[] bytes, TcpChannel sender){
        final PlayerProfile profile = gameOF.getSessionOf().getProfile();
        
        final PacketInfo packetInfo = Packets.getPacketInfo(bytes);
        if(packetInfo == null)
            return;
        switch(packetInfo.getPacketID()){

            case PacketEntityMove.PACKET_ID ->{
                final PacketEntityMove packet = packetInfo.readPacket(new PacketEntityMove());

                final Entity targetEntity = gameOF.getWorld().getEntity(packet.playerName);
                targetEntity.getPosition().set(packet.position);
                targetEntity.getRotation().set(packet.rotation);
                targetEntity.getMotion().set(packet.motion);
            }

            case PacketSpawnPlayer.PACKET_ID ->{
                final PacketSpawnPlayer packet = packetInfo.readPacket(new PacketSpawnPlayer());

                final RemotePlayer remotePlayer = new RemotePlayer(gameOF.getWorld(), packet.playerName);
                remotePlayer.getPosition().set(packet.position);
                remotePlayer.getRotation().set(packet.rotation);

                gameOF.getWorld().addEntity(remotePlayer);
                System.out.println("воооооооооооооо чел тут уже (должен) былы итыт крч ну то что инитиал циалаллизирует и вот да");
            }

            case PacketPlayerSpawnInfo.PACKET_ID ->{
                final PacketPlayerSpawnInfo packet = packetInfo.readPacket(new PacketPlayerSpawnInfo());
                
                gameOF.createNetClientWorld(packet.worldName);
                gameOF.spawnPlayer(packet.position);
                gameOF.getWorld().getChunkManager().start();
            }
            
            case PacketBlockUpdate.PACKET_ID ->{
                final PacketBlockUpdate packet = packetInfo.readPacket(new PacketBlockUpdate());
                gameOF.getWorld().setBlock(packet.x, packet.y, packet.z, packet.state, true);
            }
            
            case PacketChunk.PACKET_ID ->{
                final PacketChunk packet = packetInfo.readPacket(new PacketChunk());
                gameOF.getWorld().getChunkManager().receivedChunk(packet);
            }
            
            case PacketDisconnect.PACKET_ID ->{
                final PacketDisconnect packet = packetInfo.readPacket(new PacketDisconnect());
                
                gameOF.disconnect();
                System.out.println("[CLIENT]: соединение прервано: " + packet.reasonComponent);
            }
            
            case PacketEncryptStart.PACKET_ID ->{
                final PacketEncryptStart packet = packetInfo.readPacket(new PacketEncryptStart());
                
                byte[] encryptedClientKey = packet.publicServerKey.encrypt(gameOF.getEncryptKey().getKey().getEncoded());
                new PacketEncryptEnd(encryptedClientKey).write(sender);
                
                sender.encode(gameOF.getEncryptKey());// * шифрование *
                sender.setTcpNoDelay(false);
                
                new PacketAuth(gameOF.getSessionOf().getProfile().getName(), gameOF.getSessionOf().getSessionToken()).write(sender);
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
