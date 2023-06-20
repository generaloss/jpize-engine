package pize.tests.voxelgame;

import pize.Pize;
import pize.app.AppAdapter;
import pize.files.Resource;
import pize.graphics.gl.Gl;
import pize.tests.voxelgame.client.ClientGame;
import pize.tests.voxelgame.client.control.GameController;
import pize.tests.voxelgame.client.options.Options;
import pize.tests.voxelgame.client.renderer.ClientGameRenderer;
import pize.tests.voxelgame.clientserver.Version;
import pize.tests.voxelgame.clientserver.net.PlayerProfile;
import pize.tests.voxelgame.server.LocalServer;
import pize.util.time.Sync;

public class Main extends AppAdapter{
    
    public static void main(String[] args){
        Pize.create("Voxel Game", 1280, 720);
        Pize.run(new Main());
    }
    
    private static final String sessionToken = "54_54-iWantPizza-54_54";

    private final Options options;
    
    private final Sync fpsSync;
    private final Version version;
    private final PlayerProfile profile;
    private final GameController gameController;
    
    private final ClientGameRenderer clientRenderer;
    private LocalServer localServer;
    private final ClientGame clientGame;
    

    public Main(){
        Thread.currentThread().setName("Render-Thread");
        
        version = new Version();
        
        options = new Options(this, SharedConstants.GAME_DIR_PATH);
        fpsSync = new Sync(0);
        
        gameController = new GameController(this);
        
        clientRenderer = new ClientGameRenderer(this);
        clientGame = new ClientGame(this);
        
        clientRenderer.init();
        new Resource(SharedConstants.GAME_DIR_PATH).mkDirs();
        
        Pize.setUpdateTPS(20);
        options.load();
        
        /** Game **/
        // Create Profile
        profile = new PlayerProfile(getOptions().getPlayerName());
        
        // Run local server
        final String[] address = options.getHost().split(":");
        if(address[0].equals("0.0.0.0")){
            localServer = new LocalServer();
            localServer.run();
        }
        
        // Connect to server
        clientGame.connect(address[0], Integer.parseInt(address[1]));
    }
    
    @Override
    public void render(){
        gameController.update();
        clientGame.update();
        
        Gl.clearColorDepthBuffers();
        clientRenderer.render();
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
    
    public final LocalServer getLocalServer(){
        return localServer;
    }
    
    public final ClientGame getGame(){
        return clientGame;
    }
    
    public final GameController getController(){
        return gameController;
    }
    
}
