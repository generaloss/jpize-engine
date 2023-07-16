package pize.tests.voxelgame;

import pize.Pize;
import pize.app.AppAdapter;
import pize.files.Resource;
import pize.graphics.gl.Gl;
import pize.graphics.texture.Texture;
import pize.math.Mathc;
import pize.math.Maths;
import pize.math.vecmath.vector.Vec3f;
import pize.physic.Velocity3f;
import pize.tests.voxelgame.client.ClientGame;
import pize.tests.voxelgame.client.block.BlockState;
import pize.tests.voxelgame.client.block.Blocks;
import pize.tests.voxelgame.client.control.GameController;
import pize.tests.voxelgame.client.level.ClientLevel;
import pize.tests.voxelgame.client.options.Options;
import pize.tests.voxelgame.client.renderer.ClientGameRenderer;
import pize.tests.voxelgame.client.renderer.particle.Particle;
import pize.tests.voxelgame.main.Version;
import pize.tests.voxelgame.main.time.GameTime;
import pize.tests.voxelgame.main.modification.loader.ModEntryPointType;
import pize.tests.voxelgame.main.modification.loader.ModLoader;
import pize.tests.voxelgame.main.net.PlayerProfile;
import pize.tests.voxelgame.server.IntegratedServer;
import pize.util.time.Sync;

public class VoxelGame extends AppAdapter{
    
    public static void main(String[] args){
        Pize.create("Voxel Game", 1280, 720);
        Pize.run(getInstance());
    }
    
    private static final String sessionToken = "54_54-iWantPizza-54_54";
    
    
    private final Options options;
    
    private final Sync fpsSync;
    private final Version version;
    private final PlayerProfile profile;
    private final GameController gameController;
    
    private final ClientGameRenderer clientRenderer;
    private IntegratedServer integratedServer;
    private final ClientGame clientGame;
    
    private final ModLoader modLoader;
    
    
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
            instance.velocity.y -= Pize.getDt() * 0.35;
            instance.velocity.mul(0.95);
            collide(instance.position, instance.velocity);
            instance.position.add(instance.velocity);
        });
    
    public void collide(Vec3f position, Velocity3f velocity){
        final ClientLevel level = clientGame.getLevel();
        
        double x = velocity.x;
        //if(BlockState.getID(level.getBlock(position.xf() + Mathc.signum(x), position.yf(), position.zf())) != 0){
        //    double nx = Maths.frac(position.x) + x;
        //    if(nx > 1)
        //        x = 1;
        //    else if(nx < 0)
        //        x = 0;
        //}
        
        double y = velocity.y;
        if(BlockState.getID(level.getBlock(position.xf(), position.yf() + Mathc.signum(x), position.zf())) != 0){
            double ny = Maths.frac(position.y) + y;
            //if(ny > 1)
            //    y = 1;
            //else
            if(ny < 0)
                y = 0;
        }
        
        double z = velocity.z;
        //if(BlockState.getID(level.getBlock(position.xf(), position.yf(), position.zf() + Mathc.signum(x))) != 0){
        //    double nz = Maths.frac(position.z) + z;
        //    if(nz > 1)
        //        z = 1;
        //    else if(nz < 0)
        //        z = 0;
        //}
        
        velocity.set(x, y, z);
    }
    

    public VoxelGame(){
        /** Create Instances **/
        Thread.currentThread().setName("Render-Thread");
        
        version = new Version();
        
        options = new Options(this, SharedConstants.GAME_DIR_PATH);
        fpsSync = new Sync(0);
        
        gameController = new GameController(this);
        clientRenderer = new ClientGameRenderer(this);
        clientGame = new ClientGame(this);
        
        clientRenderer.init();
        new Resource(SharedConstants.GAME_DIR_PATH, true).mkDirs();
        new Resource(SharedConstants.MODS_PATH, true).mkDirs();
        
        Pize.setFixedUpdateTPS(GameTime.TICKS_IN_SECOND);
        options.load();
        profile = new PlayerProfile(getOptions().getPlayerName());
        
        Blocks.init();
        
        /** ModLoader **/

        modLoader = new ModLoader();
        modLoader.loadMods(SharedConstants.MODS_PATH);
    }
    
    
    @Override
    public void init(){
        // Run local server
        final String[] address = options.getHost().split(":");
        if(address[0].equals("0.0.0.0")){
            integratedServer = new IntegratedServer(this);
            integratedServer.run();
        }
        
        // Connect to server
        clientGame.connect(address[0], Integer.parseInt(address[1]));
        
        // Init mods
        modLoader.initializeMods(ModEntryPointType.CLIENT);
        modLoader.initializeMods(ModEntryPointType.MAIN);
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
    
    public final ClientGameRenderer getRenderer(){
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
    
    
    private static VoxelGame instance;
    
    public static synchronized VoxelGame getInstance(){
        if(instance == null)
            instance = new VoxelGame();
        return instance;
    }
    
}
