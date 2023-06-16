package pize.tests.voxelgame.client;

import pize.Pize;
import pize.graphics.gl.Face;
import pize.graphics.gl.Gl;
import pize.graphics.gl.PolygonMode;
import pize.io.glfw.Key;
import pize.math.vecmath.vector.Vec3i;
import pize.tests.voxelgame.Main;
import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.client.control.GameCamera;
import pize.tests.voxelgame.client.control.PerspectiveType;
import pize.tests.voxelgame.client.control.PlayerController;
import pize.tests.voxelgame.client.control.RayCast;
import pize.tests.voxelgame.client.options.KeyMapping;
import pize.tests.voxelgame.client.options.Options;
import pize.tests.voxelgame.client.world.ClientWorld;
import pize.tests.voxelgame.clientserver.net.packet.PacketPing;

public class GameController{
    
    private final Main sessionOF;
    private final PlayerController playerController;
    private float zoomFOV;
    private boolean f3Plus;
    
    public GameController(Main sessionOF){
        this.sessionOF = sessionOF;
        playerController = new PlayerController(sessionOF);
    }
    
    public Main getSessionOf(){
        return sessionOF;
    }
    
    
    public void update(){
        playerController.update();
        debugControl();
    }
    
    private void debugControl(){
        final Options options = sessionOF.getOptions();
        final GameCamera camera = sessionOF.getGame().getCamera();
        final RayCast rayCast = sessionOF.getGame().getRayCast();
        final ClientWorld world = sessionOF.getGame().getWorld();
        
        // EXIT
        if(Key.ESCAPE.isDown())
            Pize.exit();
        
        // FULLSCREEN
        if(Key.F11.isDown()){
            playerController.getRotationController().lockNextFrame();
            Pize.window().toggleFullscreen();
        }
        
        // RENDER MODE
        if(Key.NUM_1.isDown())
            Gl.polygonMode(Face.FRONT, PolygonMode.FILL);
        if(Key.NUM_2.isDown())
            Gl.polygonMode(Face.FRONT, PolygonMode.LINE);
        if(Key.NUM_3.isDown())
            Gl.polygonMode(Face.FRONT, PolygonMode.POINT);
        
        // MOUSE SET BLOCK
        if(Pize.isTouched() && rayCast.isSelected()){
            if(Pize.mouse().isLeftDown()){
                Vec3i blockPos = rayCast.getSelectedBlockPosition();
                world.setBlock(blockPos.x, blockPos.y, blockPos.z, Block.AIR.getState(), false);
            }else if(Pize.mouse().isRightDown()){
                Vec3i blockPos = rayCast.getImaginaryBlockPosition();
                world.setBlock(blockPos.x, blockPos.y, blockPos.z, Block.LAMP.getState(), false);
            }else if(Pize.mouse().isMiddleDown()){
                Vec3i blockPos = rayCast.getImaginaryBlockPosition();
                world.setBlock(blockPos.x, blockPos.y, blockPos.z, Block.OAK_LOG.getState(), false);
            }
        }
        
        // KEYBOARD SET BLOCK
        if(Key.B.isPressed()){
            final Vec3i camPos = new Vec3i(
                camera.getPos().xf(),
                camera.getPos().yf(),
                camera.getPos().zf()
            );
            world.setBlock(camPos.x, camPos.y, camPos.z, Block.GLASS.getState(), false);
        }
        
        // SHOW MOUSE
        if(Key.L.isDown())
            playerController.getRotationController().switchShowMouse();
        
        // CHUNK BORDER
        if(Key.F3.isPressed() && Key.G.isDown()){
            sessionOF.getRenderer().getWorldRenderer().toggleShowChunkBorder();
            f3Plus = true;
        }
        
        // INFO
        if(Key.F3.isReleased()){
            if(!f3Plus)
                options.setShowFPS(!options.isShowFPS());
            
            f3Plus = false;
        }
        
        // ZOOM
        if(options.getKey(KeyMapping.ZOOM).isDown())
            zoomFOV = options.getFOV() / 3F;
        else if(options.getKey(KeyMapping.ZOOM).isPressed()){
            zoomFOV -= Pize.mouse().getScroll() * (zoomFOV / 8);
            if(zoomFOV >= options.getFOV())
                zoomFOV = options.getFOV();
            
            camera.setFov(zoomFOV);
        }else if(options.getKey(KeyMapping.ZOOM).isReleased())
            camera.setFov(options.getFOV());
        
        // PING SERVER
        if(Key.P.isDown())
            sessionOF.getGame().sendPacket(new PacketPing(System.currentTimeMillis()));
        
        // TOGGLE PERSPECTIVE
        if(options.getKey(KeyMapping.TOGGLE_PERSPECTIVE).isDown()){
            switch(camera.getPerspective()){
                case FIRST_PERSON -> camera.setPerspective(PerspectiveType.THIRD_PERSON_BACK);
                case THIRD_PERSON_BACK -> camera.setPerspective(PerspectiveType.THIRD_PERSON_FRONT);
                case THIRD_PERSON_FRONT -> camera.setPerspective(PerspectiveType.FIRST_PERSON);
            }
        }
    }
    
    
    public final PlayerController getPlayerController(){
        return playerController;
    }
    
}
