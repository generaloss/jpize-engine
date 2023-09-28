package jpize.tests.mcose.server.net;

import jpize.net.security.KeyAES;
import jpize.net.tcp.TcpConnection;
import jpize.net.tcp.packet.PacketHandler;
import jpize.tests.mcose.main.net.PlayerProfile;
import jpize.tests.mcose.main.net.packet.clientbound.CBPacketDisconnect;
import jpize.tests.mcose.main.net.packet.clientbound.CBPacketEncryptStart;
import jpize.tests.mcose.main.net.packet.serverbound.SBPacketAuth;
import jpize.tests.mcose.main.net.packet.serverbound.SBPacketEncryptEnd;
import jpize.tests.mcose.main.net.packet.serverbound.SBPacketLogin;
import jpize.tests.mcose.server.Server;

public class PlayerLoginConnection implements PacketHandler{
    
    private final Server server;
    private final TcpConnection connection;
    
    private String profileName;
    private int versionID;
    
    public PlayerLoginConnection(Server server, TcpConnection connection){
        this.server = server;
        this.connection = connection;
    }
    
    
    public void login(SBPacketLogin packet){
        profileName = packet.profileName;
        versionID = packet.clientVersionID;
        
        if(versionID != server.getConfiguration().getVersion().getID()){
            connection.send(new CBPacketDisconnect("Server not available on your game version"));
            return;
        }
        
        if(PlayerProfile.isNameInvalid(profileName)){
            connection.send(new CBPacketDisconnect("Invalid player name"));
            return;
        }
        
        if(server.getPlayerList().isPlayerOnline(profileName)){
            connection.send(new CBPacketDisconnect("Player with your nickname already plays on the server"));
            return;
        }

        connection.send(new CBPacketEncryptStart(server.getConnectionManager().getRsaKey().getPublic()));
    }
    
    public void encryptEnd(SBPacketEncryptEnd packet){
        final KeyAES decryptedClientKey = new KeyAES(server.getConnectionManager().getRsaKey().decrypt(packet.encryptedClientKey));
        connection.encode(decryptedClientKey);
    }
    
    public void auth(SBPacketAuth packet){
        if(!"54_54-iWantPizza-54_54".equals(packet.accountSessionToken)){
            connection.send(new CBPacketDisconnect("Invalid session"));
            return;
        }
        
        server.getPlayerList().addNewPlayer(profileName, connection);
    }
    
}
