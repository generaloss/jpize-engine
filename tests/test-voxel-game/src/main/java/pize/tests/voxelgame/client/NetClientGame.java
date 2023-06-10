package pize.tests.voxelgame.client;

import pize.math.vecmath.vector.Vec3f;
import pize.net.security.KeyAES;
import pize.net.tcp.TcpClient;
import pize.net.tcp.packet.IPacket;
import pize.tests.voxelgame.Main;
import pize.tests.voxelgame.client.control.FirstPersonPlayerCameraTarget;
import pize.tests.voxelgame.client.entity.ClientPlayer;
import pize.tests.voxelgame.client.net.ClientPacketHandler;
import pize.tests.voxelgame.client.world.ClientWorld;
import pize.tests.voxelgame.clientserver.net.packet.PacketLogin;
import pize.tests.voxelgame.clientserver.net.packet.PacketMove;
import pize.util.time.TickGenerator;

public class NetClientGame{
    
    private final Main sessionOF;
    private final TcpClient client;
    private final KeyAES encryptKey;
    private ClientWorld world;
    private ClientPlayer player;
    
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
    }
    
    private void tick(){
        if(player != null && player.checkPosition())
            sendPacket(new PacketMove(sessionOF.getProfile().getName(), player.getPosition()));
    }
    
    public void update(){
        if(world == null || player == null)
            return;
        
        world.getChunkManager().updateMeshes();
        sessionOF.getRayCast().update();
    }
    
    public void createNetClientWorld(String worldName){
        world = new ClientWorld(sessionOF, worldName);
    }
    
    public void spawnPlayer(Vec3f position){
        player = new ClientPlayer();
        player.getPosition().set(position);
        sessionOF.getCamera().setTarget(new FirstPersonPlayerCameraTarget(player));
        sessionOF.getController().getPlayerController().setTargetPlayer(player);
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
        return world;
    }
    
    public final ClientPlayer getPlayer(){
        return player;
    }
    
}
