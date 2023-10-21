package jpize.tests.mcose.client;

import jpize.Jpize;
import jpize.files.Resource;
import jpize.gl.Gl;
import jpize.graphics.texture.Texture;
import jpize.io.context.JpizeApplication;
import jpize.math.Mathc;
import jpize.math.Maths;
import jpize.math.vecmath.vector.Vec3f;
import jpize.physic.utils.Velocity3f;
import jpize.tests.mcose.Main;
import jpize.tests.mcose.client.audio.MusicGroup;
import jpize.tests.mcose.client.audio.MusicPlayer;
import jpize.tests.mcose.client.audio.SoundPlayer;
import jpize.tests.mcose.client.block.Blocks;
import jpize.tests.mcose.client.control.GameController;
import jpize.tests.mcose.client.level.ClientLevel;
import jpize.tests.mcose.client.options.Options;
import jpize.tests.mcose.client.renderer.GameRenderer;
import jpize.tests.mcose.client.renderer.particle.Particle;
import jpize.tests.mcose.client.resources.GameResources;
import jpize.tests.mcose.client.resources.VanillaAudio;
import jpize.tests.mcose.client.resources.VanillaBlocks;
import jpize.tests.mcose.client.resources.VanillaMusic;
import jpize.tests.mcose.main.SharedConstants;
import jpize.tests.mcose.main.Version;
import jpize.tests.mcose.main.block.BlockData;
import jpize.tests.mcose.main.modification.loader.ModEntryPointType;
import jpize.tests.mcose.main.modification.loader.ModLoader;
import jpize.tests.mcose.main.net.PlayerProfile;
import jpize.tests.mcose.main.registry.Registry;
import jpize.tests.mcose.main.time.GameTime;
import jpize.tests.mcose.server.IntegratedServer;
import jpize.util.Utils;
import jpize.util.time.Sync;

public class Minecraft extends JpizeApplication{

    private GameResources gameResources;
    private Options options;

    private Sync fpsSync;
    private Version version;
    private GameController gameController;
    private GameRenderer clientRenderer;
    private IntegratedServer integratedServer;
    private ClientGame clientGame;
    private SoundPlayer soundPlayer;
    private MusicPlayer musicPlayer;

    private ModLoader modLoader;

    @Override
    public void init(){
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
        soundPlayer = new SoundPlayer(this);
        musicPlayer = new MusicPlayer(this);

        clientRenderer.init();
        new Resource(SharedConstants.GAME_DIR_PATH, true).mkDirs();
        new Resource(SharedConstants.MODS_PATH, true).mkDirs();

        Jpize.startFixedUpdate(GameTime.TICKS_PER_SECOND);
        options.load();

        // Mod Loader //
        modLoader = new ModLoader();
        modLoader.loadMods(SharedConstants.MODS_PATH);

        // Run local server //
        final String[] address = options.getHost().split(":");
        if(address[0].equals("0.0.0.0")){
            integratedServer = new IntegratedServer(this);
            integratedServer.run();
        }

        // Init mods //
        modLoader.initializeMods(ModEntryPointType.CLIENT);
        modLoader.initializeMods(ModEntryPointType.MAIN);

        // Load blocks
        Blocks.register();
        Registry.Block.loadBlocks(this);

        // Connect to server //
        Utils.delayElapsed(1000);
        clientGame.connect(address[0], Integer.parseInt(address[1]));

        // Music
        musicPlayer.setGroup(MusicGroup.GAME);
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
        soundPlayer.dispose();
        musicPlayer.dispose();
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
        return Main.profile;
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

    public SoundPlayer getSoundPlayer(){
        return soundPlayer;
    }

    public String getSessionToken(){
        return Main.sessionToken;
    }


    /**                 SOME HORRIBLE CODE                */

    public final Particle BREAK_PARTICLE = new Particle()
        .init(instance->{
            instance.size = Maths.random(0.02F, 0.15F);
            instance.region.set(Maths.random(0, 0.5), Maths.random(0, 0.5), Maths.random(0.5, 1), Maths.random(0.5, 1));
            instance.rotation = Maths.random(1, 360);
            instance.lifeTimeSeconds = Maths.random(0.5F, 2F);
            instance.velocity.set(Maths.random(-0.04F, 0.04F), Maths.random(-0.02F, 0.1F), Maths.random(-0.04F, 0.04F));
            instance.color.set3(0.6, 0.6, 0.6);
        })
        .texture(new Texture("texture/block/grass_block_side.png"))
        .animate(instance->{
            instance.velocity.y -= Jpize.getDt() * 0.35F;
            instance.velocity.mul(0.95);
            collide(instance.position, instance.velocity);
            instance.position.add(instance.velocity);
        });

    public void collide(Vec3f position, Velocity3f velocity){
        final ClientLevel level = clientGame.getLevel();

        double x = velocity.x;
        double y = velocity.y;
        if(BlockData.getID(level.getBlockState(position.xFloor(), position.yFloor() + Mathc.signum(x), position.zFloor())) != 0){
            double ny = Maths.frac(position.y) + y;
            if(ny < 0)
                y = 0;
        }
        double z = velocity.z;

        velocity.set(x, y, z);
    }
    
}