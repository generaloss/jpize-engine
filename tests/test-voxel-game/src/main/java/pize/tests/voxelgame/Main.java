package pize.tests.voxelgame;

import pize.tests.voxelgame.client.ClientGameRenderer;
import pize.tests.voxelgame.client.GameController;
import pize.tests.voxelgame.client.NetClientGame;
import pize.tests.voxelgame.client.control.FirstPersonPlayerCameraTarget;
import pize.tests.voxelgame.client.control.GameCamera;
import pize.tests.voxelgame.client.control.RayCast;
import pize.tests.voxelgame.client.entity.ClientPlayer;
import pize.tests.voxelgame.client.options.Options;
import pize.tests.voxelgame.clientserver.Version;
import pize.tests.voxelgame.clientserver.net.PlayerProfile;
import pize.tests.voxelgame.server.LocalServer;
import pize.Pize;
import pize.activity.ActivityListener;
import pize.files.Resource;
import pize.graphics.gl.Gl;
import pize.math.Maths;
import pize.util.Utils;
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
    
    public final String playerName = "Makcum-" + Maths.randomSeed(3);
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
        
        Utils.delayElapsed(500);
        
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
