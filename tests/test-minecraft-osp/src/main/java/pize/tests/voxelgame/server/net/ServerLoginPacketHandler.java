package pize.tests.voxelgame.server.net;

import pize.net.security.KeyAES;
import pize.net.tcp.TcpConnection;
import pize.net.tcp.packet.PacketHandler;
import pize.tests.voxelgame.main.net.PlayerProfile;
import pize.tests.voxelgame.main.net.packet.*;
import pize.tests.voxelgame.server.Server;

public class ServerLoginPacketHandler implements PacketHandler{
    
    private final Server server;
    private final TcpConnection connection;
    
    private String profileName;
    private int versionID;
    
    public ServerLoginPacketHandler(Server server, TcpConnection connection){
        this.server = server;
        this.connection = connection;
    }
    
    
    public void handleLogin(SBPacketLogin packet){
        profileName = packet.profileName;
        versionID = packet.clientVersionID;
        
        if(versionID != server.getConfiguration().getVersion().getID()){
            new CBPacketDisconnect("Server not available on your game version").write(connection);
            return;
        }
        
        if(PlayerProfile.isNameInvalid(profileName)){
            new CBPacketDisconnect("Invalid player name").write(connection);
            return;
        }
        
        if(server.getPlayerList().isPlayerOnline(profileName)){
            new CBPacketDisconnect("Player with your nickname already plays on the server").write(connection);
            return;
        }
        
        new CBPacketEncryptStart(server.getConnectionManager().getRsaKey().getPublic()).write(connection);
    }
    
    public void handleEncryptEnd(SBPacketEncryptEnd packet){
        final KeyAES decryptedClientKey = new KeyAES(server.getConnectionManager().getRsaKey().decrypt(packet.encryptedClientKey));
        connection.encode(decryptedClientKey);
    }
    
    public void handleAuth(SBPacketAuth packet){
        if(!"54_54-iWantPizza-54_54".equals(packet.accountSessionToken)){
            new CBPacketDisconnect("Invalid session").write(connection);
            return;
        }
        
        server.getPlayerList().addNewPlayer(profileName, connection);
    }
    
}
