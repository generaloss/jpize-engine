package pize.tests.voxelgame.client;

import pize.tests.voxelgame.Main;
import pize.tests.voxelgame.client.entity.ClientPlayer;
import pize.tests.voxelgame.client.net.ClientPacketHandler;
import pize.tests.voxelgame.client.world.ClientWorld;
import pize.tests.voxelgame.clientserver.net.packet.PacketLogin;
import pize.tests.voxelgame.clientserver.net.packet.PacketMove;
import pize.net.security.KeyAES;
import pize.net.tcp.TcpClient;
import pize.net.tcp.packet.IPacket;
import pize.util.time.TickGenerator;

public class NetClientGame{
    
    private final Main sessionOF;
    private final TcpClient client;
    private final KeyAES encryptKey;
    private ClientWorld clientWorld;
    
    public NetClientGame(Main sessionOF){
        this.sessionOF = sessionOF;
        
        client = new TcpClient(new ClientPacketHandler(this));
        
        encryptKey = new KeyAES(256);
        
        new TickGenerator(20){
            public void run(){
                tick();
            }
        }.startAsync();
    }
    
    public Main getSessionOf(){
        return sessionOF;
    }
    
    
    public void connect(String address, int port){
        System.out.println("[CLIENT]: Connect");
        client.connect(address, port);
        sendPacket( new PacketLogin(sessionOF.getVersion().getID(), sessionOF.getProfile().getName()) );
        
        clientWorld = new ClientWorld(sessionOF);
    }
    
    private void tick(){
        final ClientPlayer player = sessionOF.getClientPlayer();
        if(player.checkPosition())
            sendPacket(new PacketMove(sessionOF.getProfile().getName(), player.getPosition()));
    }
    
    
    public void sendPacket(IPacket packet){
        packet.write(client.getChannel());
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
