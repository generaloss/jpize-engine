package megalul.projectvostok;

import megalul.projectvostok.block.blocks.Block;
import megalul.projectvostok.chunk.mesh.ChunkBuilder;
import megalul.projectvostok.control.FirstPersonPlayerCameraTarget;
import megalul.projectvostok.control.GameCamera;
import megalul.projectvostok.control.PlayerController;
import megalul.projectvostok.control.RayCast;
import megalul.projectvostok.entity.Player;
import megalul.projectvostok.options.KeyMapping;
import megalul.projectvostok.options.Options;
import megalul.projectvostok.world.ChunkProvider;
import megalul.projectvostok.world.World;
import megalul.projectvostok.world.light.WorldLight;
import megalul.projectvostok.world.render.WorldRenderer;
import pize.Pize;
import pize.context.ContextListener;
import pize.files.FileHandle;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.graphics.gl.*;
import pize.graphics.util.batch.TextureBatch;
import pize.io.glfw.Key;
import pize.math.vecmath.vector.Vec3i;
import pize.util.time.Sync;

public class Main implements ContextListener{

    public static String GAME_DIR_PATH = "./";
    public static boolean UPDATE_DEPTH_MAP = false;


    public static void main(String[] args){
        Pize.create("Project Vostok", 1280, 720);
        Pize.run(new Main());
    }

    private final TextureBatch uiBatch;
    private final BitmapFont font;

    private final Options options;
    private final World world;
    private final Player player;
    private final PlayerController playerController;
    private final GameCamera camera;
    private final RayCast rayCast;
    private final WorldRenderer renderer;
    private final Sync fpsSync;
    
    private float zoomFOV;

    public Main(){
        uiBatch = new TextureBatch(200);
        font = FontLoader.getDefault();
        
        options = new Options(this, GAME_DIR_PATH);
        camera = new GameCamera(0.1, 1000, 110);
        fpsSync = new Sync(0);
        world = new World(this);
        player = new Player(world);
        playerController = new PlayerController(this, player);
        rayCast = new RayCast(this, 2000);
        renderer = new WorldRenderer(this);
    }
    
    public void init(){
        Gl.enable(Target.DEPTH_TEST);
        Gl.depthFunc(DepthFunc.LEQUAL);
        
        new FileHandle(GAME_DIR_PATH).mkDirs();
        
        options.load();
        
        camera.setTarget(new FirstPersonPlayerCameraTarget(player));
        world.putEntity(player);
        player.getPosition().y = 16 + 128 + 1;
    }

    
    public void render(){
        camera.update();
        rayCast.update();
        playerController.update();
        controls();
        
        Gl.clearBuffers(true);
        getWorld().getProvider().updateMeshes();
        renderer.render();
        renderUi();
    }

    private void renderUi(){
        if(!options.isShowFPS())
            return;

        uiBatch.setColor(0.5F, 0.4F, 0.1F, 1);
        uiBatch.begin();

        // INFO
        font.drawText(uiBatch, "fps: " + Pize.getFPS(), 25, Pize.getHeight() - 25 - font.getScaledLineHeight());
        font.drawText(uiBatch, "position: " + camera.getX() + ", " + camera.getY() + ", " + camera.getZ(), 25, Pize.getHeight() - 25 - font.getScaledLineHeight() * 2);
        font.drawText(uiBatch, "chunk: " + camera.chunkX() + ", " + camera.chunkZ(), 25, Pize.getHeight() - 25 - font.getScaledLineHeight() * 3);
        font.drawText(uiBatch, "Threads:", 25, Pize.getHeight() - 25 - font.getScaledLineHeight() * 4);
        font.drawText(uiBatch, "chunk find tps: " + world.getProvider().findTps.get(), 25, Pize.getHeight() - 25 - font.getScaledLineHeight() * 5);
        font.drawText(uiBatch, "chunk load tps: " + world.getProvider().loadTps.get(),   25, Pize.getHeight() - 25 - font.getScaledLineHeight() * 6);
        font.drawText(uiBatch, "chunk build tps: " + world.getProvider().buildTps.get(), 25, Pize.getHeight() - 25 - font.getScaledLineHeight() * 7);
        font.drawText(uiBatch, "chunk check tps: " + world.getProvider().checkTps.get(), 25, Pize.getHeight() - 25 - font.getScaledLineHeight() * 8);
        font.drawText(uiBatch, "meshes: " + world.getProvider().getMeshes().size(), 25, Pize.getHeight() - 25 - font.getScaledLineHeight() * 9);
        font.drawText(uiBatch, "render chunks: " + world.getProvider().getChunks().stream().filter(camera::isChunkSeen).count(), 25, Pize.getHeight() - 25 - font.getScaledLineHeight() * 10);
        font.drawText(uiBatch, "Light time (I/D): " + WorldLight.increaseTime + " ms, " + WorldLight.decreaseTime + " ms", 25, Pize.getHeight() - 25 - font.getScaledLineHeight() * 11);
        font.drawText(uiBatch, "Chunk build time (T/V): " + ChunkBuilder.buildTime + " ms, " + ChunkBuilder.vertexCount + " vertices", 25, Pize.getHeight() - 25 - font.getScaledLineHeight() * 12);
        Vec3i imaginaryPos = rayCast.getImaginaryBlockPosition();
        Vec3i selectedPos = rayCast.getSelectedBlockPosition();
        font.drawText(uiBatch, "Selected light level (F/B): " + world.getLight(imaginaryPos.x, imaginaryPos.y, imaginaryPos.z) + ", " + world.getLight(selectedPos.x, selectedPos.y, selectedPos.z), 25, Pize.getHeight() - 25 - font.getScaledLineHeight() * 13);

        // CTRL HINT
        font.drawText(uiBatch, "1, 2, 3 - set render mode", 25, 25 + font.getScaledLineHeight() * 0);
        font.drawText(uiBatch, "R - show mouse", 25, 25 + font.getScaledLineHeight() * 1);
        font.drawText(uiBatch, "F3 + G - show chunk border", 25, 25 + font.getScaledLineHeight() * 2);
        font.drawText(uiBatch, options.getKey(KeyMapping.ZOOM) + " + Mouse Wheel - zoom", 25, 25 + font.getScaledLineHeight() * 3);
        font.drawText(uiBatch, "L - stop loading chunks", 25, 25 + font.getScaledLineHeight() * 4);

        uiBatch.end();
    }

    private void controls(){
        // EXIT
        if(Pize.isDown(Key.ESCAPE))
            Pize.exit();
        
        // FULLSCREEN
        if(Pize.isDown(Key.F11)){
            playerController.getRotationController().lockNextFrame();
            Pize.window().toggleFullscreen();
        }
        
        // RENDER MODE
        if(Pize.isDown(Key.NUM_1))
            Gl.setPolygonMode(Face.FRONT, PolygonMode.FILL);
        if(Pize.isDown(Key.NUM_2))
            Gl.setPolygonMode(Face.FRONT, PolygonMode.LINE);
        if(Pize.isDown(Key.NUM_3))
            Gl.setPolygonMode(Face.FRONT, PolygonMode.POINT);
        
        // MOUSE SET BLOCK
        if(Pize.isTouched() && rayCast.isSelected()){
            if(Pize.mouse().isLeftDown()){
                Vec3i blockPos = rayCast.getSelectedBlockPosition();
                world.setBlock(blockPos.x, blockPos.y, blockPos.z, Block.AIR.getState());
            }else if(Pize.mouse().isRightDown()){
                Vec3i blockPos = rayCast.getImaginaryBlockPosition();
                world.setBlock(blockPos.x, blockPos.y, blockPos.z, Block.LAMP.getState());
            }else if(Pize.mouse().isMiddleDown()){
                Vec3i blockPos = rayCast.getImaginaryBlockPosition();
                world.setBlock(blockPos.x, blockPos.y, blockPos.z, Block.OAK_LOG.getState());
            }
        }
        
        // KEYBOARD SET BLOCK
        if(Pize.isPressed(Key.B)){
            final Vec3i camPos = new Vec3i(
                camera.getPos().xf(),
                camera.getPos().yf(),
                camera.getPos().zf()
            );
            world.setBlock(camPos.x, camPos.y, camPos.z, Block.GLASS.getState());
        }
        
        // SHOW MOUSE
        if(Pize.isDown(Key.R))
            playerController.getRotationController().switchShowMouse();
        
        // CHUNK BORDER
        if(Pize.isPressed(Key.F3) && Pize.isDown(Key.G))
            renderer.toggleShowChunkBorder();
        
        // ZOOM
        if(Pize.isDown(options.getKey(KeyMapping.ZOOM)))
            zoomFOV = options.getFOV() / 3F;
        else if(Pize.isPressed(options.getKey(KeyMapping.ZOOM))){
            zoomFOV -= Pize.mouse().getScroll() * (zoomFOV / 8);
            if(zoomFOV >= options.getFOV())
                zoomFOV = options.getFOV();
            
            camera.setFov(zoomFOV);
        }else if(Pize.isReleased(options.getKey(KeyMapping.ZOOM)))
            camera.setFov(options.getFOV());
        
       // STOP LOAD CHUNKS
       if(Pize.isDown(Key.L))
           ChunkProvider.doLoadChunks = !ChunkProvider.doLoadChunks;
    }


    public void resize(int width, int height){
        camera.resize(width, height);
        renderer.resize(width, height);
    }

    public void dispose(){
        uiBatch.dispose();
        font.dispose();
        renderer.dispose();

        options.save();
    }
    

    public final Options getOptions(){
        return options;
    }

    public final GameCamera getCamera(){
        return camera;
    }

    public final World getWorld(){
        return world;
    }

    public final Sync getFpsSync(){
        return fpsSync;
    }
    
    public final Player getPlayer(){
        return player;
    }
    
    public final PlayerController getPlayerController(){
        return playerController;
    }
    
    public final RayCast getRayCast(){
        return rayCast;
    }
    
}
