package pize.tests.voxelgame.client;

import pize.math.vecmath.vector.Vec3f;
import pize.net.security.KeyAES;
import pize.net.tcp.TcpClient;
import pize.net.tcp.packet.IPacket;
import pize.tests.voxelgame.Main;
import pize.tests.voxelgame.client.control.GameCamera;
import pize.tests.voxelgame.client.control.RayCast;
import pize.tests.voxelgame.client.entity.LocalPlayer;
import pize.tests.voxelgame.client.net.ClientPacketHandler;
import pize.tests.voxelgame.client.world.ClientWorld;
import pize.tests.voxelgame.clientserver.net.packet.PacketLogin;
import pize.tests.voxelgame.clientserver.net.packet.PacketMove;

public class ClientGame{
    
    private final Main sessionOF;
    private final TcpClient client;
    private final KeyAES encryptKey;
    private ClientWorld world;
    private final RayCast rayCast;
    private LocalPlayer player;
    private GameCamera camera;
    
    
    public ClientGame(Main sessionOF){
        this.sessionOF = sessionOF;
        
        client = new TcpClient(new ClientPacketHandler(this));
        encryptKey = new KeyAES(256);
        
        rayCast = new RayCast(sessionOF, 2000);
    }
    
    public Main getSessionOf(){
        return sessionOF;
    }
    
    
    public void connect(String address, int port){
        System.out.println("[CLIENT]: Connect");
        client.connect(address, port);
        sendPacket( new PacketLogin(sessionOF.getVersion().getID(), sessionOF.getProfile().getName()) );
    }
    
    public void tick(){
        if(world == null || player == null)
            return;
        
        if(player.checkPositionChange())
            sendPacket(new PacketMove(player.getPosition()));
    }
    
    public void update(){
        if(camera == null)
            return;
        
        world.getChunkManager().updateMeshes();
        rayCast.update();
        player.update();
        camera.update();
    }
    
    public void createNetClientWorld(String worldName){
        world = new ClientWorld(sessionOF, worldName);
        rayCast.setWorld(world);
    }
    
    public void spawnPlayer(Vec3f position){
        if(world == null)
            return;
        
        player = new LocalPlayer(world);
        player.getPosition().set(position);
        
        camera = new GameCamera(player, 0.1, 1000, sessionOF.getOptions().getFOV());
        camera.setDistance(sessionOF.getOptions().getRenderDistance());
        
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
    
    public final LocalPlayer getPlayer(){
        return player;
    }
    
    public final GameCamera getCamera(){
        return camera;
    }
    
    public final RayCast getRayCast(){
        return rayCast;
    }
    
}
