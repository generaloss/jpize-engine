package pize.tests.voxelgame.clientserver.net;

import megalul.projectvostok.clientserver.net.packet.*;
import pize.tests.voxelgame.clientserver.net.packet.*;

public interface PacketHandler{
    
    void onAuth(PacketAuth packet);
    
    void onDisconnect(PacketDisconnect packet);
    
    void onEncryptEnd(PacketEncryptEnd packet);
    
    void onEncryptStart(PacketEncryptStart packet);
    
    void onLogin(PacketLogin packet);
    
    void onPing(PacketPing packet);
    
    void onRenderDistance(PacketRenderDistance packet);
    
}
