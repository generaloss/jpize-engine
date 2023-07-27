package pize.tests.minecraftosp.server.net;

import pize.net.tcp.packet.PacketHandler;
import pize.tests.minecraftosp.main.net.packet.*;

public interface ServerPlayerPacketHandler extends PacketHandler{
    
    void handleChunkRequest(SBPacketChunkRequest packet);
    
    void handlePlayerBlockSet(SBPacketPlayerBlockSet packet);
    
    void handleMove(SBPacketMove packet);
    
    void handleRenderDistance(SBPacketRenderDistance packet);
    
    void handleSneaking(SBPacketPlayerSneaking packet);
    
    void handleChatMessage(SBPacketChatMessage packet);
    
}
