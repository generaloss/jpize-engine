package pize.tests.voxelgame.client.control;

import pize.Pize;
import pize.graphics.gl.Face;
import pize.graphics.gl.Gl;
import pize.graphics.gl.PolygonMode;
import pize.io.glfw.Key;
import pize.io.glfw.KeyAction;
import pize.math.vecmath.vector.Vec3i;
import pize.tests.voxelgame.Main;
import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.client.chat.Chat;
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
        final Options options = session.getOptions();
        final GameCamera camera = session.getGame().getCamera();
        final RayCast rayCast = session.getGame().getRayCast();
        final ClientLevel world = session.getGame().getLevel();
        
        /** Window **/
        
        // Fullscreen
        if(Key.F11.isDown()){
            playerController.getRotationController().lockNextFrame();
            Pize.window().toggleFullscreen();
        }
        
        
        /** Chat **/
        
        final Chat chat = session.getGame().getChat();
        
        Pize.keyboard().addKeyCallback((keyCode, action)->{
            if(chat.isOpened() && action == KeyAction.REPEAT){
                if(keyCode == Key.UP.GLFW)
                    chat.historyUp();
                if(keyCode == Key.DOWN.GLFW)
                    chat.historyDown();
            }
        });
        
        if(chat.isOpened()){
            if(Key.ENTER.isDown()){
                chat.enter();
                chat.close();
            }
            
            if(Key.UP.isDown())
                chat.historyUp();
            if(Key.DOWN.isDown())
                chat.historyDown();
            
            if(Key.ESCAPE.isDown())
                chat.close();
            
            return; // Abort subsequent control
            
        }else if(options.getKey(KeyMapping.CHAT).isDown())
            chat.open();
        else if(options.getKey(KeyMapping.COMMAND).isDown()){
            chat.openAsCommandLine();
        }
        
        /** Game **/
        
        // Exit
        if(Key.ESCAPE.isDown())
            Pize.exit();
        
        // Player
        playerController.update();
        
        // Render mode
        if(Key.NUM_1.isDown())
            Gl.polygonMode(Face.FRONT, PolygonMode.FILL);
        if(Key.NUM_2.isDown())
            Gl.polygonMode(Face.FRONT, PolygonMode.LINE);
        if(Key.NUM_3.isDown())
            Gl.polygonMode(Face.FRONT, PolygonMode.POINT);
        
        // Place/Destroy block
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
        
        // Show mouse
        if(Key.L.isDown())
            playerController.getRotationController().toggleShowMouse();
        
        // Chunk border
        if(Key.F3.isPressed() && Key.G.isDown()){
            session.getRenderer().getWorldRenderer().toggleShowChunkBorder();
            f3Plus = true;
        }
        
        // Info
        if(Key.F3.isReleased()){
            if(!f3Plus)
                options.setShowFPS(!options.isShowFPS());
            
            f3Plus = false;
        }
        
        // Camera zoom
        if(options.getKey(KeyMapping.ZOOM).isDown())
            zoomFOV = options.getFOV() / 3F;
        else if(options.getKey(KeyMapping.ZOOM).isPressed()){
            zoomFOV -= Pize.mouse().getScroll() * (zoomFOV / 8);
            if(zoomFOV >= options.getFOV())
                zoomFOV = options.getFOV();
            
            camera.setFov(zoomFOV);
        }else if(options.getKey(KeyMapping.ZOOM).isReleased())
            camera.setFov(options.getFOV());
        
        // Ping server
        if(Key.P.isDown())
            session.getGame().sendPacket(new SBPacketPing(System.currentTimeMillis()));
    }
    
    
    public final PlayerController getPlayerController(){
        return playerController;
    }
    
}
