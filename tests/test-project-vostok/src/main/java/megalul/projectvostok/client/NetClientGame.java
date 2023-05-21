package megalul.projectvostok.client;

import megalul.projectvostok.Main;
import megalul.projectvostok.client.net.ClientPacketHandler;
import megalul.projectvostok.client.world.ClientWorld;
import pize.net.security.KeyAES;
import pize.net.tcp.TcpClient;

public class NetClientGame{
    
    private final Main sessionOF;
    private final TcpClient client;
    private final KeyAES encryptKey;
    private ClientWorld clientWorld;
    
    public NetClientGame(Main sessionOF){
        this.sessionOF = sessionOF;
        
        client = new TcpClient(new ClientPacketHandler(this));
        
        encryptKey = new KeyAES(256);
    }
    
    public Main getSessionOf(){
        return sessionOF;
    }
    
    
    public void connect(String address, int port){
        client.connect(address, port);
        // client.send(new PacketLogin(sessionOF.getVersion().getID(), sessionOF.getProfile().getName()));
        
        clientWorld = new ClientWorld(sessionOF);
    }
    
    public void sendPacket(byte[] packet){
        client.send(packet);
    }
    
    public void disconnect(){
        client.disconnect();
    }
    
    
    public KeyAES getEncryptKey(){
        return encryptKey;
    }
    
    public ClientWorld getWorld(){
        return clientWorld;
    }
    
}
