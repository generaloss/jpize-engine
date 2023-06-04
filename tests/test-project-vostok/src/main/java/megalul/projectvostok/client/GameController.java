package megalul.projectvostok.client;

import megalul.projectvostok.Main;
import megalul.projectvostok.client.block.blocks.Block;
import megalul.projectvostok.client.control.GameCamera;
import megalul.projectvostok.client.control.PlayerController;
import megalul.projectvostok.client.control.RayCast;
import megalul.projectvostok.client.options.KeyMapping;
import megalul.projectvostok.client.options.Options;
import megalul.projectvostok.server.world.ServerWorld;
import pize.Pize;
import pize.graphics.gl.Face;
import pize.graphics.gl.Gl;
import pize.graphics.gl.PolygonMode;
import pize.io.glfw.Key;
import pize.math.vecmath.vector.Vec3i;

public class GameController{
    
    private final Main sessionOF;
    private final PlayerController playerController;
    private float zoomFOV;
    
    public GameController(Main sessionOF){
        this.sessionOF = sessionOF;
        playerController = new PlayerController(sessionOF, sessionOF.getClientPlayer());
    }
    
    public Main getSessionOf(){
        return sessionOF;
    }
    
    
    public void update(){
        playerController.update();
        debugControl();
    }
    
    private void debugControl(){
        Options options = sessionOF.getOptions();
        GameCamera camera = sessionOF.getCamera();
        RayCast rayCast = sessionOF.getRayCast();
        ServerWorld world = sessionOF.getLocalServer().getDefaultWorld();
        
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
            Gl.polygonMode(Face.FRONT, PolygonMode.FILL);
        if(Pize.isDown(Key.NUM_2))
            Gl.polygonMode(Face.FRONT, PolygonMode.LINE);
        if(Pize.isDown(Key.NUM_3))
            Gl.polygonMode(Face.FRONT, PolygonMode.POINT);
        
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
            sessionOF.getRenderer().getWorldRenderer().toggleShowChunkBorder();
        
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
        
        // PING SERVER
        if(Pize.isDown(Key.P))
            ;//sessionOF.getNet().sendPacket(new PacketPing(System.currentTimeMillis()));
    }
    
    
    public final PlayerController getPlayerController(){
        return playerController;
    }
    
}
