package pize.tests.minecraftosp.server.net;

import pize.net.tcp.TcpConnection;
import pize.net.tcp.packet.IPacket;
import pize.tests.minecraftosp.client.block.BlockProperties;
import pize.tests.minecraftosp.main.command.source.CommandSourcePlayer;
import pize.tests.minecraftosp.main.net.packet.*;
import pize.tests.minecraftosp.server.level.ServerLevel;
import pize.tests.minecraftosp.server.player.ServerPlayer;
import pize.tests.minecraftosp.main.block.BlockData;
import pize.tests.minecraftosp.main.audio.BlockSoundPack;
import pize.tests.minecraftosp.main.block.BlockSetType;
import pize.tests.minecraftosp.main.chunk.storage.ChunkPos;
import pize.tests.minecraftosp.main.text.Component;
import pize.tests.minecraftosp.main.text.TextColor;
import pize.tests.minecraftosp.server.Server;

public class PlayerGameConnection implements ServerPlayerPacketHandler{
    
    private final ServerPlayer player;
    private final Server server;
    private final TcpConnection connection;
    private final CommandSourcePlayer commandSource;
    
    public PlayerGameConnection(ServerPlayer player, TcpConnection connection){
        this.player = player;
        this.server = player.getServer();
        this.connection = connection;
        this.commandSource = new CommandSourcePlayer(player);
    }
    
    
    public ServerPlayer getPlayer(){
        return player;
    }

    public TcpConnection getConnection(){
        return connection;
    }
    
    public void sendPacket(IPacket<?> packet){
        packet.write(connection);
    }
    
    
    @Override
    public void handleChunkRequest(SBPacketChunkRequest packet){
        final ServerLevel level = player.getLevel();
        
        level.getChunkManager().requestedChunk(
            player,
            new ChunkPos(packet.chunkX, packet.chunkZ)
        );
    }
    
    @Override
    public void handlePlayerBlockSet(SBPacketPlayerBlockSet packet){
        final BlockProperties oldBlock = player.getLevel().getBlockProps(packet.x, packet.y, packet.z);

        // Set Block on the Server //
        final boolean result = player.getLevel().setBlock(packet.x, packet.y, packet.z, packet.state);
        if(!result)
            return;
        
        final BlockProperties block = BlockData.getProps(packet.state);
        final BlockSetType setType = BlockSetType.from(oldBlock.isEmpty(), block.isEmpty());

        // Send Set Block packet //
        server.getPlayerList().broadcastPacketExcept(new CBPacketBlockUpdate(packet.x, packet.y, packet.z, packet.state), player);

        // Sound //
        final BlockSoundPack soundPack;
        if(setType.ordinal() > 0)
            soundPack = block.getSoundPack();
        else
            soundPack = oldBlock.getSoundPack();

        if(soundPack != null)
            player.getLevel().playSound(soundPack.randomDestroySound(), 1, 1, packet.x + 0.5F, packet.y + 0.5F, packet.z + 0.5F);
    }
    
    @Override
    public void handleMove(SBPacketMove packet){
        player.getPosition().set(packet.position);
        player.getRotation().set(packet.rotation);
        player.getVelocity().set(packet.velocity);
        
        server.getPlayerList().broadcastPacketLevelExcept(player.getLevel(), new CBPacketEntityMove(player), player);
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
            player.sendToChat(new Component().color(TextColor.DARK_GREEN).text("<" + player.getName() + "> ").reset().text(packet.message));
    }
    
}
