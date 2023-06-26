package pize.tests.voxelgame;

import pize.Pize;
import pize.app.AppAdapter;
import pize.files.Resource;
import pize.graphics.gl.Gl;
import pize.tests.voxelgame.base.modification.loader.ModEntryPointType;
import pize.tests.voxelgame.client.ClientGame;
import pize.tests.voxelgame.client.control.GameController;
import pize.tests.voxelgame.client.options.Options;
import pize.tests.voxelgame.client.renderer.ClientGameRenderer;
import pize.tests.voxelgame.base.Version;
import pize.tests.voxelgame.base.modification.loader.ModLoader;
import pize.tests.voxelgame.base.net.PlayerProfile;
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
        
        Pize.setUpdateTPS(20);
        options.load();
        profile = new PlayerProfile(getOptions().getPlayerName());
        
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
        gameController.update();
        clientGame.update();
        
        Gl.clearColorDepthBuffers();
        clientRenderer.render();
        
        modLoader.invokeMethod(ModEntryPointType.CLIENT, "render");
    }
    
    @Override
    public void update(){
        clientGame.tick();
    }
    
    @Override
    public void resize(int width, int height){
        clientRenderer.resize(width, height);
        getGame().getCamera().resize(width, height);
    }

    @Override
    public void dispose(){
        clientRenderer.dispose();
        options.save();
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
    
    
    private static VoxelGame instance;
    
    public static synchronized VoxelGame getInstance(){
        if(instance == null)
            instance = new VoxelGame();
        return instance;
    }
    
}
