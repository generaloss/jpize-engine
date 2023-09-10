package jpize.tests.minecraftosp.client;

import jpize.Jpize;
import jpize.math.vecmath.vector.Vec3f;
import jpize.net.security.KeyAES;
import jpize.net.tcp.TcpClient;
import jpize.net.tcp.packet.IPacket;
import jpize.tests.minecraftosp.Minecraft;
import jpize.tests.minecraftosp.client.chat.Chat;
import jpize.tests.minecraftosp.client.net.ClientConnection;
import jpize.tests.minecraftosp.client.time.ClientGameTime;
import jpize.tests.minecraftosp.main.Tickable;
import jpize.tests.minecraftosp.client.control.camera.GameCamera;
import jpize.tests.minecraftosp.client.control.BlockRayCast;
import jpize.tests.minecraftosp.client.entity.LocalPlayer;
import jpize.tests.minecraftosp.client.level.ClientLevel;
import jpize.tests.minecraftosp.client.renderer.particle.Particle;
import jpize.tests.minecraftosp.client.renderer.particle.ParticleBatch;
import jpize.tests.minecraftosp.main.time.GameTime;
import jpize.tests.minecraftosp.main.net.packet.SBPacketLogin;
import jpize.tests.minecraftosp.main.net.packet.SBPacketMove;

public class ClientGame implements Tickable{
    
    public static int tx;
    private static int txCounter;
    
    private final Minecraft session;
    private final TcpClient client;
    private final Chat chat;
    private final KeyAES encryptKey;
    private final BlockRayCast blockRayCast;
    private final ClientGameTime time;
    
    private ClientLevel level;
    private LocalPlayer player;
    private GameCamera camera;
    
    
    public ClientGame(Minecraft session){
        this.session = session;
        
        client = new TcpClient(new ClientConnection(this));
        encryptKey = new KeyAES(256);
        
        blockRayCast = new BlockRayCast(session, 2000);
        chat = new Chat(this);
        time = new ClientGameTime(this);
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

        // Send player position
        if(player.isPositionChanged() || player.isRotationChanged())
            sendPacket(new SBPacketMove(player));

        //: HARAM
        if(time.getTicks() % GameTime.TICKS_IN_SECOND == 0){
            tx = txCounter;
            txCounter = 0;
            ClientConnection.rx = ClientConnection.rxCounter;
            ClientConnection.rxCounter = 0;
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
            Jpize.execSync(() ->{
                level.getConfiguration().setName(worldName);
                level.getChunkManager().reset();
            });
        else{
            level = new ClientLevel(this, worldName);
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
            Jpize.execSync(()->{
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
    
    public final ClientGameTime getTime(){
        return time;
    }
    
}
