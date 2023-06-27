package pize.tests.voxelgame.server.net;

import pize.net.tcp.TcpConnection;
import pize.net.tcp.packet.IPacket;
import pize.tests.voxelgame.base.chunk.storage.ChunkPos;
import pize.tests.voxelgame.base.net.packet.*;
import pize.tests.voxelgame.base.text.Component;
import pize.tests.voxelgame.base.text.TextColor;
import pize.tests.voxelgame.server.Server;
import pize.tests.voxelgame.server.command.source.CommandSourcePlayer;
import pize.tests.voxelgame.server.level.ServerLevel;
import pize.tests.voxelgame.server.player.ServerPlayer;

public class PlayerConnectionAdapter implements ServerPlayerPacketHandler{
    
    private final ServerPlayer player;
    private final Server server;
    private final TcpConnection connection;
    private final CommandSourcePlayer commandSource;
    
    public PlayerConnectionAdapter(ServerPlayer player, TcpConnection connection){
        this.player = player;
        this.server = player.getServer();
        this.connection = connection;
        this.commandSource = new CommandSourcePlayer(player);
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
        
        if(message.startsWith("/"))
            server.getCommandDispatcher().executeCommand(message.substring(1), commandSource);
        else
            server.getPlayerList().broadcastMessage(new Component().color(TextColor.DARK_GREEN).text("<" + player.getName() + "> ").reset().text(packet.message));
    }
    
}
