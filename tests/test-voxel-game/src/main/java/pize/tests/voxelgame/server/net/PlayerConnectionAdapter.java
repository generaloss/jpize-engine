package pize.tests.voxelgame.server.net;

import pize.net.tcp.TcpConnection;
import pize.net.tcp.packet.IPacket;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkPos;
import pize.tests.voxelgame.clientserver.net.packet.*;
import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.server.level.ServerLevel;
import pize.tests.voxelgame.server.player.ServerPlayer;

public class PlayerConnectionAdapter implements ServerPlayerPacketHandler{
    
    private final ServerPlayer player;
    private final Server server;
    private final TcpConnection connection;
    
    public PlayerConnectionAdapter(ServerPlayer player, TcpConnection connection){
        this.player = player;
        this.server = player.getServer();
        this.connection = connection;
    }
    
    
    public ServerPlayer getPlayer(){
        return player;
    }
    
    public void sendPacket(IPacket<?> packet){
        packet.write(connection);
    }
    
    
    @Override
    public void handleChunkRequest(SBPacketChunkRequest packet){
        final ServerLevel level = (ServerLevel) player.getLevel();
        
        level.getChunkManager().requestedChunk(
            player,
            new ChunkPos(packet.chunkX, packet.chunkZ)
        );
    }
    
    @Override
    public void handlePlayerBlockSet(SBPacketPlayerBlockSet packet){
        player.getLevel().setBlock(packet.x, packet.y, packet.z, packet.state);
        
        server.getPlayerList().broadcastPacketExcept(new CBPacketBlockUpdate(packet.x, packet.y, packet.z, packet.state), player);
    }
    
    @Override
    public void handleMove(SBPacketMove packet){
        player.getPosition().set(packet.position);
        player.getRotation().set(packet.rotation);
        player.getMotion().set(packet.motion);
        
        server.getPlayerList().broadcastPacketExcept(new CBPacketEntityMove(player), player);
    }
    
    @Override
    public void handleRenderDistance(SBPacketRenderDistance packet){
        player.setRenderDistance(packet.renderDistance);
    }
    
    @Override
    public void handleSneaking(SBPacketPlayerSneaking packet){
        player.setSneaking(packet.sneaking);
        
        server.getPlayerList().broadcastPacketExcept(new CBPacketPlayerSneaking(player), player);
    }
    
    @Override
    public void handleChatMessage(SBPacketChatMessage packet){
        String message = packet.message;
        
        if(message.startsWith("/")){
            message = message.substring(1);
            
            final String command = message.split(" ")[0];
            
            final String[] args;
            if(message.length() != command.length())
                args = message.substring(command.length() + 1).split(" ");
            else
                args = new String[0];
            
            System.out.println("[Server}: Player " + player.getName() + " execute command: " + message);
            server.executeCommand(command, args, player);
        }else
            server.getPlayerList().broadcastMessage("<" + player.getName() + "> " + packet.message);
    }
    
}
