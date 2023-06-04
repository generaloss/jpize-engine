package megalul.projectvostok;

import megalul.projectvostok.client.ClientGameRenderer;
import megalul.projectvostok.client.GameController;
import megalul.projectvostok.client.NetClientGame;
import megalul.projectvostok.client.control.FirstPersonPlayerCameraTarget;
import megalul.projectvostok.client.control.GameCamera;
import megalul.projectvostok.client.control.RayCast;
import megalul.projectvostok.client.entity.ClientPlayer;
import megalul.projectvostok.client.options.Options;
import megalul.projectvostok.clientserver.Version;
import megalul.projectvostok.clientserver.net.PlayerProfile;
import megalul.projectvostok.server.LocalServer;
import pize.Pize;
import pize.activity.ActivityListener;
import pize.files.Resource;
import pize.graphics.gl.Gl;
import pize.util.time.Sync;

public class Main implements ActivityListener{
    
    public static void main(String[] args){
        Pize.create("Project Vostok", 1280, 720);
        Pize.run(new Main());
    }

    private final Options options;
    private final ClientPlayer clientPlayer;
    private final GameCamera camera;
    private final RayCast rayCast;
    private final Sync fpsSync;
    private final Version version;
    public SessionStatus status;
    private final PlayerProfile profile;
    
    public final String playerName = "Makcum-57";
    public final String sessionToken = "54_54-iWantPizza-54_54";
    private final ClientGameRenderer clientRenderer;
    private final LocalServer localServer;
    private final NetClientGame netClientGame;
    private final GameController gameController;
    

    public Main(){
        version = new Version();
        status = SessionStatus.MENU;
        profile = new PlayerProfile(playerName);
        
        options = new Options(this, SharedConstants.GAME_DIR_PATH);
        camera = new GameCamera(0.1, 1000, 110);
        fpsSync = new Sync(0);
        
        clientPlayer = new ClientPlayer();
        
        rayCast = new RayCast(this, 2000);
        
        localServer = new LocalServer();
        localServer.run();
        
        clientRenderer = new ClientGameRenderer(this);
        netClientGame = new NetClientGame(this);
        netClientGame.connect(localServer.getConfiguration().getAddress(), localServer.getConfiguration().getPort());
        gameController = new GameController(this);
    }
    
    @Override
    public void init(){
        clientRenderer.init();
        new Resource(SharedConstants.GAME_DIR_PATH).mkDirs();
        options.load();
        
        camera.setTarget(new FirstPersonPlayerCameraTarget(clientPlayer));
        clientPlayer.getPosition().y = 16 + 128 + 1;
        
        status = SessionStatus.MULTIPLAYER;
    }
    
    @Override
    public void render(){
        gameController.update();
        camera.update();
        rayCast.update();
        netClientGame.getWorld().getChunkManager().updateMeshes();
        
        Gl.clearCDBuffers();
        clientRenderer.render();
    }
    
    
    @Override
    public void resize(int width, int height){
        clientRenderer.resize(width, height);
        camera.resize(width, height);
    }

    @Override
    public void dispose(){
        clientRenderer.dispose();
        options.save();
    }
    
    public final Options getOptions(){
        return options;
    }

    public final GameCamera getCamera(){
        return camera;
    }

    public final Sync getFpsSync(){
        return fpsSync;
    }
    
    public final ClientPlayer getClientPlayer(){
        return clientPlayer;
    }
    
    public final RayCast getRayCast(){
        return rayCast;
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
    
    public final NetClientGame getNet(){
        return netClientGame;
    }
    
    public final GameController getController(){
        return gameController;
    }
    
}
