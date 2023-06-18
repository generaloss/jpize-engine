package pize.tests.voxelgame.client;

import pize.math.vecmath.vector.Vec3f;
import pize.net.security.KeyAES;
import pize.net.tcp.TcpClient;
import pize.net.tcp.packet.IPacket;
import pize.tests.voxelgame.Main;
import pize.tests.voxelgame.client.chat.Chat;
import pize.tests.voxelgame.client.control.GameCamera;
import pize.tests.voxelgame.client.control.RayCast;
import pize.tests.voxelgame.client.entity.LocalPlayer;
import pize.tests.voxelgame.client.level.ClientLevel;
import pize.tests.voxelgame.client.net.ClientPacketHandler;
import pize.tests.voxelgame.clientserver.net.packet.SBPacketLogin;
import pize.tests.voxelgame.clientserver.net.packet.SBPacketMove;

public class ClientGame{
    
    public static int tx;
    private static int txCounter;
    
    private final Main session;
    private final TcpClient client;
    private final Chat chat;
    private final KeyAES encryptKey;
    private final RayCast rayCast;
    
    private ClientLevel world;
    private LocalPlayer player;
    private GameCamera camera;
    
    private int tickCount;
    
    
    public ClientGame(Main session){
        this.session = session;
        
        client = new TcpClient(new ClientPacketHandler(this));
        encryptKey = new KeyAES(256);
        
        rayCast = new RayCast(session, 2000);
        chat = new Chat(this);
    }
    
    public Main getSession(){
        return session;
    }
    
    
    public void connect(String address, int port){
        System.out.println("[Client]: Connect to " + address + ":" + port);
        client.connect(address, port);
        sendPacket( new SBPacketLogin(session.getVersion().getID(), session.getProfile().getName()) );
    }
    
    public void tick(){
        tickCount++;
        
        if(tickCount % 75 == 0){
            tx = txCounter;
            txCounter = 0;
            ClientPacketHandler.rx = ClientPacketHandler.rxCounter;
            ClientPacketHandler.rxCounter = 0;
        }
        
        if(world == null || player == null)
            return;
        
        sendPacket(new SBPacketMove(player));
    }
    
    public void update(){
        if(camera == null)
            return;
        
        world.getChunkManager().updateMeshes();
        rayCast.update();
        camera.update();

        player.tick();
        world.tick();
    }
    
    public void createNetClientWorld(String worldName){
        world = new ClientLevel(session, worldName);
        rayCast.setWorld(world);
    }
    
    public void spawnPlayer(Vec3f position){
        if(world == null)
            return;
        
        player = new LocalPlayer(world, session.getProfile().getName());
        player.getPosition().set(position);
        
        camera = new GameCamera(player, 0.1, 1000, session.getOptions().getFOV());
        camera.setDistance(session.getOptions().getRenderDistance());
        
        session.getController().getPlayerController().setTargetPlayer(player);
    }
    
    
    public void sendPacket(IPacket<?> packet){
        packet.write(client.getConnection());
        txCounter++;
    }
    
    public void disconnect(){
        client.disconnect();
    }
    
    
    public final KeyAES getEncryptKey(){
        return encryptKey;
    }
    
    public final ClientLevel getLevel(){
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
    
    public final Chat getChat(){
        return chat;
    }
    
}
