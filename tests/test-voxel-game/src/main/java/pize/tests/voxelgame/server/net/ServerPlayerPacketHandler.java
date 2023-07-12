package pize.tests.voxelgame.server.net;

import pize.net.tcp.packet.PacketHandler;
import pize.tests.voxelgame.main.net.packet.*;

public interface ServerPlayerPacketHandler extends PacketHandler{
    
    void handleChunkRequest(SBPacketChunkRequest packet);
    
    void handlePlayerBlockSet(SBPacketPlayerBlockSet packet);
    
    void handleMove(SBPacketMove packet);
    
    void handleRenderDistance(SBPacketRenderDistance packet);
    
    void handleSneaking(SBPacketPlayerSneaking packet);
    
    void handleChatMessage(SBPacketChatMessage packet);
    
}
