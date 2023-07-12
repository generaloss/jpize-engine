package pize.tests.voxelgame.client.net;

import pize.Pize;
import pize.net.tcp.TcpConnection;
import pize.net.tcp.TcpListener;
import pize.net.tcp.packet.PacketHandler;
import pize.net.tcp.packet.PacketInfo;
import pize.net.tcp.packet.Packets;
import pize.tests.voxelgame.client.ClientGame;
import pize.tests.voxelgame.client.entity.LocalPlayer;
import pize.tests.voxelgame.client.entity.RemotePlayer;
import pize.tests.voxelgame.main.chat.MessageSourceServer;
import pize.tests.voxelgame.main.entity.Entity;
import pize.tests.voxelgame.main.net.packet.*;
import pize.tests.voxelgame.main.text.Component;

public class ClientPacketHandler implements TcpListener, PacketHandler{
    
    private final ClientGame game;
    
    public ClientPacketHandler(ClientGame game){
        this.game = game;
    }
    
    public ClientGame getGame(){
        return game;
    }
    
    
    public static int rx;
    public static int rxCounter;
    
    
    @Override
    public void received(byte[] bytes, TcpConnection sender){
        final PacketInfo packetInfo = Packets.getPacketInfo(bytes);
        if(packetInfo == null)
            return;
        rxCounter++;
        switch(packetInfo.getPacketID()){
            
            // Login
            case CBPacketDisconnect.PACKET_ID ->{
                final CBPacketDisconnect packet = packetInfo.readPacket(new CBPacketDisconnect());
                
                game.disconnect();
                System.out.println("[Client]: Connection closed: " + packet.reasonComponent);
            }
            
            case CBPacketEncryptStart.PACKET_ID ->{
                final CBPacketEncryptStart packet = packetInfo.readPacket(new CBPacketEncryptStart());
                
                byte[] encryptedClientKey = packet.publicServerKey.encrypt(game.getEncryptKey().getKey().getEncoded());
                new SBPacketEncryptEnd(encryptedClientKey).write(sender);
                
                sender.encode(game.getEncryptKey());// * шифрование *
                
                new SBPacketAuth(game.getSession().getSessionToken()).write(sender);
            }
            
            // Game
            case CBPacketSpawnInfo.PACKET_ID ->{
                final CBPacketSpawnInfo packet = packetInfo.readPacket(new CBPacketSpawnInfo());
                
                game.createClientLevel(packet.levelName);
                game.spawnPlayer(packet.position);
                game.getLevel().getChunkManager().startLoadChunks();
                
                game.sendPacket(new SBPacketRenderDistance(game.getSession().getOptions().getRenderDistance()));
            }
            
            case CBPacketTime.PACKET_ID -> {
                final CBPacketTime packet = packetInfo.readPacket(new CBPacketTime());
                
                getGame().getTime().setTicks(packet.gameTimeTicks);
            }
            
            case CBPacketAbilities.PACKET_ID -> {
                final CBPacketAbilities packet = packetInfo.readPacket(new CBPacketAbilities());
                
                getGame().getPlayer().setFlyEnabled(packet.flyEnabled);
            }
            
            case CBPacketTeleportPlayer.PACKET_ID ->{
                final CBPacketTeleportPlayer packet = packetInfo.readPacket(new CBPacketTeleportPlayer());
                
                final LocalPlayer localPlayer = game.getPlayer();
                if(localPlayer == null)
                    break;
                
                // Load another level
                if(!packet.levelName.equals(localPlayer.getLevel().getConfiguration().getName())){
                    game.createClientLevel(packet.levelName);
                    game.getLevel().getChunkManager().startLoadChunks();
                    localPlayer.setLevel(game.getLevel());
                }
                
                localPlayer.getPosition().set(packet.position);
                localPlayer.getRotation().set(packet.rotation);
            }
            
            case CBPacketChatMessage.PACKET_ID ->{
                final CBPacketChatMessage packet = packetInfo.readPacket(new CBPacketChatMessage());
                game.getChat().putMessage(packet);
            }
            
            case CBPacketPlayerSneaking.PACKET_ID ->{
                final CBPacketPlayerSneaking packet = packetInfo.readPacket(new CBPacketPlayerSneaking());
                
                final Entity targetEntity = game.getLevel().getEntity(packet.playerUUID);
                if(targetEntity instanceof RemotePlayer player)
                    player.setSneaking(packet.sneaking);
            }
            
            case CBPacketEntityMove.PACKET_ID ->{
                final CBPacketEntityMove packet = packetInfo.readPacket(new CBPacketEntityMove());

                Entity targetEntity = game.getLevel().getEntity(packet.uuid);
                if(targetEntity == null && game.getPlayer().getUUID() == packet.uuid)
                    targetEntity = game.getPlayer();
                
                if(targetEntity != null){
                    targetEntity.getPosition().set(packet.position);
                    targetEntity.getRotation().set(packet.rotation);
                    targetEntity.getMotion().set(packet.motion);
                }
            }

            case CBPacketSpawnEntity.PACKET_ID ->{
                final CBPacketSpawnEntity packet = packetInfo.readPacket(new CBPacketSpawnEntity());

                final Entity entity = packet.type.createEntity(game.getLevel());
                entity.getPosition().set(packet.position);
                entity.getRotation().set(packet.rotation);
                entity.setUUID(packet.uuid);

                game.getLevel().addEntity(entity);
            }
            
            case CBPacketRemoveEntity.PACKET_ID ->{
                final CBPacketRemoveEntity packet = packetInfo.readPacket(new CBPacketRemoveEntity());
                
                game.getLevel().removeEntity(packet.uuid);
            }
            
            case CBPacketSpawnPlayer.PACKET_ID ->{
                final CBPacketSpawnPlayer packet = packetInfo.readPacket(new CBPacketSpawnPlayer());
                
                final RemotePlayer remotePlayer = new RemotePlayer(game.getLevel(), packet.playerName);
                remotePlayer.getPosition().set(packet.position);
                remotePlayer.getRotation().set(packet.rotation);
                remotePlayer.setUUID(packet.uuid);
                
                game.getLevel().addEntity(remotePlayer);
            }
            
            case CBPacketBlockUpdate.PACKET_ID ->{
                final CBPacketBlockUpdate packet = packetInfo.readPacket(new CBPacketBlockUpdate());
                game.getLevel().setBlock(packet.x, packet.y, packet.z, packet.state);
            }
            
            case CBPacketChunk.PACKET_ID ->{
                final CBPacketChunk packet = packetInfo.readPacket(new CBPacketChunk());
                game.getLevel().getChunkManager().receivedChunk(packet);
            }
            
            // Ping
            case CBPacketPong.PACKET_ID -> {
                final CBPacketPong packet = packetInfo.readPacket(new CBPacketPong());
                
                final String message = "Ping - " + (System.nanoTime() - packet.timeMillis) / 1000000F + " ms";
                game.getChat().putMessage(new MessageSourceServer(), new Component().text(message));
                System.out.println("[Client]: " + message);
            }
            
        }
    }
    
    @Override
    public void connected(TcpConnection connection){ }
    
    @Override
    public void disconnected(TcpConnection connection){
        Pize.exit();
    }
    
}
