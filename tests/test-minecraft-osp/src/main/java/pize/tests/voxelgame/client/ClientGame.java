package pize.tests.voxelgame.client;

import pize.Pize;
import pize.math.vecmath.vector.Vec3f;
import pize.net.security.KeyAES;
import pize.net.tcp.TcpClient;
import pize.net.tcp.packet.IPacket;
import pize.tests.voxelgame.Minecraft;
import pize.tests.voxelgame.client.chat.Chat;
import pize.tests.voxelgame.client.control.camera.GameCamera;
import pize.tests.voxelgame.client.control.BlockRayCast;
import pize.tests.voxelgame.client.entity.LocalPlayer;
import pize.tests.voxelgame.client.level.ClientLevel;
import pize.tests.voxelgame.client.renderer.particle.Particle;
import pize.tests.voxelgame.client.renderer.particle.ParticleBatch;
import pize.tests.voxelgame.client.net.ClientPacketHandler;
import pize.tests.voxelgame.main.Tickable;
import pize.tests.voxelgame.main.time.GameTime;
import pize.tests.voxelgame.main.net.packet.SBPacketLogin;
import pize.tests.voxelgame.main.net.packet.SBPacketMove;

public class ClientGame implements Tickable{
    
    public static int tx;
    private static int txCounter;
    
    private final Minecraft session;
    private final TcpClient client;
    private final Chat chat;
    private final KeyAES encryptKey;
    private final BlockRayCast blockRayCast;
    private final GameTime time;
    
    private ClientLevel level;
    private LocalPlayer player;
    private GameCamera camera;
    
    
    public ClientGame(Minecraft session){
        this.session = session;
        
        client = new TcpClient(new ClientPacketHandler(this));
        encryptKey = new KeyAES(256);
        
        blockRayCast = new BlockRayCast(session, 2000);
        chat = new Chat(this);
        time = new GameTime();
    }
    
    public Minecraft getSession(){
        return session;
    }
    
    
    public void connect(String address, int port){
        System.out.println("[Client]: Connect to " + address + ":" + port);
        client.connect(address, port);
        client.getConnection().setTcpNoDelay(true);
        sendPacket( new SBPacketLogin(session.getVersion().getID(), session.getProfile().getName()) );
    }

    @Override
    public void tick(){
        if(level == null || player == null)
            return;
        
        time.tick();
        player.tick();
        level.tick();
        
        if(player.isPositionChanged() || player.isRotationChanged())
            sendPacket(new SBPacketMove(player));
        
        if(time.getTicks() % GameTime.TICKS_IN_SECOND == 0){ //: HARAM
            tx = txCounter;
            txCounter = 0;
            ClientPacketHandler.rx = ClientPacketHandler.rxCounter;
            ClientPacketHandler.rxCounter = 0;
        }
    }
    
    public void update(){
        if(camera == null)
            return;
        
        player.updateInterpolation();
        blockRayCast.update();
        camera.update();
    }
    
    public void createClientLevel(String worldName){
        if(level != null)
            Pize.execSync(() ->{
                level.getConfiguration().setName(worldName);
                level.getChunkManager().reset();
            });
        else{
            level = new ClientLevel(session, worldName);
            blockRayCast.setLevel(level);
        }
    }
    
    public void spawnPlayer(Vec3f position){
        if(level == null)
            return;
        
        player = new LocalPlayer(level, session.getProfile().getName());
        player.getPosition().set(position);
        
        camera = new GameCamera(this, 0.1, 5000, session.getOptions().getFieldOfView());
        camera.setDistance(session.getOptions().getRenderDistance());
        
        session.getController().getPlayerController().setTargetPlayer(player);
    }
    
    
    public void sendPacket(IPacket<?> packet){
        packet.write(client.getConnection());
        txCounter++;
    }
    
    public void disconnect(){
        client.disconnect();
        
        if(level != null)
            Pize.execSync(()->{
                System.out.println(10000);
                level.getChunkManager().dispose();
            });
    }
    
    
    public void spawnParticle(Particle particle, Vec3f position){
        final ParticleBatch particleBatch = session.getRenderer().getWorldRenderer().getParticleBatch();
        particleBatch.spawnParticle(particle, position);
    }
    
    
    public final KeyAES getEncryptKey(){
        return encryptKey;
    }
    
    public final ClientLevel getLevel(){
        return level;
    }
    
    public final LocalPlayer getPlayer(){
        return player;
    }
    
    public final GameCamera getCamera(){
        return camera;
    }
    
    public final BlockRayCast getBlockRayCast(){
        return blockRayCast;
    }
    
    public final Chat getChat(){
        return chat;
    }
    
    public final GameTime getTime(){
        return time;
    }
    
}
