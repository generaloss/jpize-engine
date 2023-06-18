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
import pize.tests.voxelgame.client.level.ClientLevel;
import pize.tests.voxelgame.client.options.KeyMapping;
import pize.tests.voxelgame.client.options.Options;
import pize.tests.voxelgame.clientserver.net.packet.SBPacketPing;
import pize.tests.voxelgame.clientserver.net.packet.SBPacketPlayerBlockSet;

public class GameController{
    
    private final Main session;
    private final PlayerController playerController;
    private float zoomFOV;
    private boolean f3Plus;
    
    public GameController(Main session){
        this.session = session;
        playerController = new PlayerController(session);
    }
    
    public Main getSession(){
        return session;
    }
    
    
    public void update(){
        playerController.update();
        debugControl();
    }
    
    private void debugControl(){
        final Options options = session.getOptions();
        final GameCamera camera = session.getGame().getCamera();
        final RayCast rayCast = session.getGame().getRayCast();
        final ClientLevel world = session.getGame().getLevel();
        
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
                final Vec3i blockPos = rayCast.getSelectedBlockPosition();
                world.setBlock(blockPos.x, blockPos.y, blockPos.z, Block.AIR.getState());
                session.getGame().sendPacket(new SBPacketPlayerBlockSet(blockPos.x, blockPos.y, blockPos.z, Block.AIR.getState()));
            }else if(Pize.mouse().isRightDown()){
                final Vec3i blockPos = rayCast.getImaginaryBlockPosition();
                world.setBlock(blockPos.x, blockPos.y, blockPos.z, Block.LAMP.getState());
                session.getGame().sendPacket(new SBPacketPlayerBlockSet(blockPos.x, blockPos.y, blockPos.z, Block.LAMP.getState()));
            }
        }
        
        // SHOW MOUSE
        if(Key.L.isDown())
            playerController.getRotationController().switchShowMouse();
        
        // CHUNK BORDER
        if(Key.F3.isPressed() && Key.G.isDown()){
            session.getRenderer().getWorldRenderer().toggleShowChunkBorder();
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
            session.getGame().sendPacket(new SBPacketPing(System.currentTimeMillis()));
        
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
