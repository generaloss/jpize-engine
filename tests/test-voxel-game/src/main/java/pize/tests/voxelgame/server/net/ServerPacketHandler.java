package pize.tests.voxelgame.server.net;

import pize.tests.voxelgame.clientserver.net.PlayerProfile;
import megalul.projectvostok.clientserver.net.packet.*;
import pize.tests.voxelgame.clientserver.net.packet.*;
import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.server.chunk.ServerChunk;
import pize.tests.voxelgame.server.player.PlayerList;
import pize.net.security.KeyAES;
import pize.net.security.KeyRSA;
import pize.net.tcp.TcpChannel;
import pize.net.tcp.TcpListener;
import pize.net.tcp.packet.PacketInfo;
import pize.net.tcp.packet.Packets;

public class ServerPacketHandler implements TcpListener{
    
    private final Server serverOF;
    private final KeyRSA encryptInitKey;
    
    public ServerPacketHandler(Server serverOF){
        this.serverOF = serverOF;
        
        encryptInitKey = new KeyRSA(1024);
    }
    
    public Server getServerOf(){
        return serverOF;
    }
    
    
    @Override
    public synchronized void received(byte[] bytes, TcpChannel sender){
        final PlayerList playerList = serverOF.getPlayerList();
        
        final PacketInfo packetInfo = Packets.getPacketInfo(bytes);
        if(packetInfo == null)
            return;
        switch(packetInfo.getPacketID()){
            
            case PacketChunkRequest.PACKET_ID ->{
                final PacketChunkRequest packet = packetInfo.readPacket(new PacketChunkRequest());
                
                final ServerChunk chunk = getServerOf().getWorldManager().getWorld(
                    getServerOf().getPlayerList().getOnlinePlayer(packet.playerName).getWorldName()
                ).getChunkProvider().getChunk(packet.chunkX, packet.chunkZ);
                
                if(chunk == null)
                    return;
                
                chunk.getStorage().getPacket().write(sender);
            }
            
            case PacketPlayerBlockSet.PACKET_ID ->{
                final PacketPlayerBlockSet packet = packetInfo.readPacket(new PacketPlayerBlockSet());
                serverOF.getWorldManager().getWorld(
                    serverOF.getPlayerList().getOnlinePlayer(packet.playerName).getWorldName()
                ).setBlock(packet.x, packet.y, packet.z, packet.state, false);
            }
            
            case PacketAuth.PACKET_ID ->{
                final PacketAuth packet = packetInfo.readPacket(new PacketAuth());
                
                if(!"54_54-iWantPizza-54_54".equals(packet.accountSessionToken)){
                    new PacketDisconnect("Недействительная сессия").write(sender);
                    return;
                }
                
                System.out.println("[SERVER]: Player '" + packet.profileName + "' is licensed");
                
                //     * выключить режим ожидания аутентификации для данного клиента *
            }
            
            case PacketEncryptEnd.PACKET_ID ->{
                final PacketEncryptEnd packet = packetInfo.readPacket(new PacketEncryptEnd());
                
                KeyAES decryptedClientKey = new KeyAES(encryptInitKey.decrypt(packet.encryptedClientKey));
                sender.encode(decryptedClientKey);// * шифрование *
                
                // * включить режим ожидания аутентификации для данного клиента *
                
                System.out.println("[SERVER]: Player '" + packet.profileName + "' encrypted");
            }
            
            case PacketLogin.PACKET_ID ->{
                final PacketLogin packet = packetInfo.readPacket(new PacketLogin());
                
                if(packet.clientVersionID != serverOF.getConfiguration().getVersion().getID()){
                    new PacketDisconnect("Сервер не доступен на данной версии").write(sender);
                    return;
                }
                
                if(!PlayerProfile.isNameValid(packet.profileName)){
                    new PacketDisconnect("Недопустимое имя").write(sender);
                    return;
                }
                
                if(playerList.isPlayerOnline(packet.profileName)){
                    new PacketDisconnect("Этот игрок уже играет на сервере").write(sender);
                    return;
                }
                
                playerList.connectOnlinePlayer(packet.profileName, sender);
                
                new PacketEncryptStart(encryptInitKey.getPublic()).write(sender);
                
                System.out.println("[SERVER]: Player '" + packet.profileName + "' logged (version ID: " + packet.clientVersionID + ")");
            }
            
            case PacketMove.PACKET_ID ->{
                final PacketMove packet = packetInfo.readPacket(new PacketMove());
                playerList.getOnlinePlayer(packet.playerName).getPosition().set(packet.position);
            }
            
            case PacketPing.PACKET_ID -> packetInfo.readPacket(new PacketPing()).write(sender);
            
            case PacketRenderDistance.PACKET_ID ->{
                final PacketRenderDistance packet = packetInfo.readPacket(new PacketRenderDistance());
                playerList.getOnlinePlayer(packet.playerName).setRenderDistance(packet.renderDistance);
            }
            
        }
    }
    
    @Override
    public void connected(TcpChannel channel){ }
    
    @Override
    public void disconnected(TcpChannel channel){ }
    
}
