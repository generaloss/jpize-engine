package pize.tests.voxelgame.client.control;

import pize.Pize;
import pize.io.glfw.Key;
import pize.math.vecmath.vector.Vec3i;
import pize.tests.voxelgame.Main;
import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.client.chat.Chat;
import pize.tests.voxelgame.client.entity.LocalPlayer;
import pize.tests.voxelgame.client.level.ClientLevel;
import pize.tests.voxelgame.client.options.KeyMapping;
import pize.tests.voxelgame.client.options.Options;
import pize.tests.voxelgame.clientserver.net.packet.SBPacketPing;
import pize.tests.voxelgame.clientserver.net.packet.SBPacketPlayerBlockSet;

public class GameController{
    
    private final Main session;
    private final PlayerController playerController;
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
        
        // Place/Destroy/Take block
        if(Pize.isTouched() && rayCast.isSelected()){
            final LocalPlayer player = session.getGame().getPlayer();
            
            if(Pize.mouse().isLeftDown()){
                final Vec3i blockPos = rayCast.getSelectedBlockPosition();
                world.setBlock(blockPos.x, blockPos.y, blockPos.z, Block.AIR.getDefaultState());
                session.getGame().sendPacket(new SBPacketPlayerBlockSet(blockPos.x, blockPos.y, blockPos.z, Block.AIR.getDefaultState()));
                
            }else if(Pize.mouse().isRightDown()){
                final Vec3i blockPos = rayCast.getImaginaryBlockPosition();
                world.setBlock(blockPos.x, blockPos.y, blockPos.z, player.holdBlock);
                session.getGame().sendPacket(new SBPacketPlayerBlockSet(blockPos.x, blockPos.y, blockPos.z, player.holdBlock));
                
            }else if(Pize.mouse().isMiddleDown()){
                final Vec3i blockPos = rayCast.getSelectedBlockPosition();
                player.holdBlock = world.getBlock(blockPos.x, blockPos.y, blockPos.z);
            }
        }
        
        // Show mouse
        if(Key.M.isDown())
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
            camera.setZoom(10);
        else if(options.getKey(KeyMapping.ZOOM).isPressed()){
            camera.setZoom(camera.getZoom() + Pize.mouse().getScroll() * (camera.getZoom() / 8));
        }else if(options.getKey(KeyMapping.ZOOM).isReleased())
            camera.setZoom(1);
        
        // Ping server
        if(Key.P.isDown())
            session.getGame().sendPacket(new SBPacketPing(System.currentTimeMillis()));
    }
    
    
    public final PlayerController getPlayerController(){
        return playerController;
    }
    
}
