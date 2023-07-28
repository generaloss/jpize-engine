package pize.tests.minecraftosp;

import pize.Pize;
import pize.app.AppAdapter;
import pize.audio.sound.Sound;
import pize.files.Resource;
import pize.graphics.gl.Gl;
import pize.graphics.texture.Texture;
import pize.math.Mathc;
import pize.math.Maths;
import pize.math.vecmath.vector.Vec3f;
import pize.physic.Velocity3f;
import pize.tests.minecraftosp.client.ClientGame;
import pize.tests.minecraftosp.client.audio.AudioPlayer;
import pize.tests.minecraftosp.client.block.Blocks;
import pize.tests.minecraftosp.client.control.GameController;
import pize.tests.minecraftosp.client.level.ClientLevel;
import pize.tests.minecraftosp.client.options.Options;
import pize.tests.minecraftosp.client.renderer.GameRenderer;
import pize.tests.minecraftosp.client.renderer.particle.Particle;
import pize.tests.minecraftosp.client.resources.GameResources;
import pize.tests.minecraftosp.client.resources.VanillaAudio;
import pize.tests.minecraftosp.client.resources.VanillaBlocks;
import pize.tests.minecraftosp.client.resources.VanillaMusic;
import pize.tests.minecraftosp.main.Version;
import pize.tests.minecraftosp.main.block.BlockData;
import pize.tests.minecraftosp.main.modification.loader.ModEntryPointType;
import pize.tests.minecraftosp.main.modification.loader.ModLoader;
import pize.tests.minecraftosp.main.net.PlayerProfile;
import pize.tests.minecraftosp.main.time.GameTime;
import pize.tests.minecraftosp.server.IntegratedServer;
import pize.util.Utils;
import pize.util.time.Sync;

public class Minecraft extends AppAdapter{
    
    public static void main(String[] args){
        Pize.create("Minecraft Open Source Project", 1280, 720);
        Pize.run(getInstance());
    }
    
    private static final String sessionToken = "54_54-iWantPizza-54_54";


    private final GameResources gameResources;

    private final Options options;
    
    private final Sync fpsSync;
    private final Version version;
    private final PlayerProfile profile;
    private final GameController gameController;
    
    private final GameRenderer clientRenderer;
    private IntegratedServer integratedServer;
    private final ClientGame clientGame;
    private final AudioPlayer audioPlayer;
    
    private final ModLoader modLoader;


    public Minecraft(){
        // Create Instances //
        Thread.currentThread().setName("Render-Thread");

        // Resources //
        gameResources = new GameResources();
        VanillaBlocks.register(gameResources);
        VanillaAudio.register(gameResources);
        VanillaMusic.register(gameResources);
        gameResources.load();

        // Other //
        version = new Version();
        
        options = new Options(this, SharedConstants.GAME_DIR_PATH);
        fpsSync = new Sync(0);
        fpsSync.enable(false);
        
        gameController = new GameController(this);
        clientRenderer = new GameRenderer(this);
        clientGame = new ClientGame(this);
        audioPlayer = new AudioPlayer(this);
        
        clientRenderer.init();
        new Resource(SharedConstants.GAME_DIR_PATH, true).mkDirs();
        new Resource(SharedConstants.MODS_PATH, true).mkDirs();
        
        Pize.setFixedUpdateTPS(GameTime.TICKS_PER_SECOND);
        options.load();
        profile = new PlayerProfile(getOptions().getPlayerName());
        
        Blocks.init(this);
        
        // Mod Loader //
        modLoader = new ModLoader();
        modLoader.loadMods(SharedConstants.MODS_PATH);
    }
    
    
    @Override
    public void init(){
        // Run local server //
        final String[] address = options.getHost().split(":");
        if(address[0].equals("0.0.0.0")){
            integratedServer = new IntegratedServer(this);
            integratedServer.run();
        }

        // Init mods //
        modLoader.initializeMods(ModEntryPointType.CLIENT);
        modLoader.initializeMods(ModEntryPointType.MAIN);
        
        // Connect to server //
        Utils.delayElapsed(1000);
        clientGame.connect(address[0], Integer.parseInt(address[1]));

        final Sound sound = new Sound("music/game/sweden.ogg");
        sound.play();
    }
    
    @Override
    public void render(){
        fpsSync.sync();
        gameController.update();
        clientGame.update();
        
        Gl.clearColorDepthBuffers();
        clientRenderer.render();
        
        modLoader.invokeMethod(ModEntryPointType.CLIENT, "render");
    }
    
    @Override
    public void fixedUpdate(){
        clientGame.tick();
    }
    
    @Override
    public void resize(int width, int height){
        clientRenderer.resize(width, height);
        getGame().getCamera().resize(width, height);
    }

    @Override
    public void dispose(){
        // Save options
        options.save();

        // Stop server
        if(integratedServer != null)
            integratedServer.stop();
        else
            clientGame.disconnect();

        // Free resources
        clientRenderer.dispose();
        gameResources.dispose();
    }
    
    public final Options getOptions(){
        return options;
    }

    public final Sync getFpsSync(){
        return fpsSync;
    }
    
    public final Version getVersion(){
        return version;
    }
    
    public final PlayerProfile getProfile(){
        return profile;
    }
    
    public final String getSessionToken(){
        return sessionToken;
    }
    
    public final GameRenderer getRenderer(){
        return clientRenderer;
    }
    
    public final IntegratedServer getIntegratedServer(){
        return integratedServer;
    }
    
    public final ClientGame getGame(){
        return clientGame;
    }
    
    public final GameController getController(){
        return gameController;
    }
    
    public final ModLoader getModLoader(){
        return modLoader;
    }

    public GameResources getResources(){
        return gameResources;
    }

    public AudioPlayer getAudioPlayer(){
        return audioPlayer;
    }
    
    
    private static Minecraft instance;
    
    public static synchronized Minecraft getInstance(){
        if(instance == null)
            instance = new Minecraft();
        return instance;
    }


    /**                 SOME HORRIBLE CODE                */

    public final Particle BREAK_PARTICLE = new Particle()
        .init(instance->{
            instance.size = Maths.random(0.02F, 0.15F);
            instance.region.set(Maths.random(0, 0.5), Maths.random(0, 0.5), Maths.random(0.5, 1), Maths.random(0.5, 1));
            instance.rotation = Maths.random(1, 360);
            instance.lifeTimeSeconds = Maths.random(0.5F, 2F);
            instance.velocity.set(Maths.random(-0.04F, 0.04F), Maths.random(-0.02F, 0.1F), Maths.random(-0.04F, 0.04F));
        })
        .texture(new Texture("texture/block/grass_block_side.png"))
        .animate(instance->{
            instance.velocity.y -= Pize.getDt() * 0.35F;
            instance.velocity.mul(0.95);
            collide(instance.position, instance.velocity);
            instance.position.add(instance.velocity);
        });

    public void collide(Vec3f position, Velocity3f velocity){
        final ClientLevel level = clientGame.getLevel();

        double x = velocity.x;
        double y = velocity.y;
        if(BlockData.getID(level.getBlock(position.xf(), position.yf() + Mathc.signum(x), position.zf())) != 0){
            double ny = Maths.frac(position.y) + y;
            if(ny < 0)
                y = 0;
        }
        double z = velocity.z;

        velocity.set(x, y, z);
    }
    
}
