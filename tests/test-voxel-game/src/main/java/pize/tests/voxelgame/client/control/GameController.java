package pize.tests.voxelgame.client.control;

import pize.Pize;
import pize.graphics.gl.Face;
import pize.graphics.gl.Gl;
import pize.graphics.gl.PolygonMode;
import pize.io.glfw.Key;
import pize.math.Maths;
import pize.math.vecmath.vector.Vec3f;
import pize.math.vecmath.vector.Vec3i;
import pize.physic.BoundingBox3;
import pize.physic.BoxBody;
import pize.tests.voxelgame.VoxelGame;
import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.Blocks;
import pize.tests.voxelgame.client.block.shape.BlockCollideShape;
import pize.tests.voxelgame.client.chat.Chat;
import pize.tests.voxelgame.client.control.camera.GameCamera;
import pize.tests.voxelgame.client.entity.LocalPlayer;
import pize.tests.voxelgame.client.level.ClientLevel;
import pize.tests.voxelgame.client.options.KeyMapping;
import pize.tests.voxelgame.client.options.Options;
import pize.tests.voxelgame.main.entity.Entity;
import pize.tests.voxelgame.main.net.packet.SBPacketPing;
import pize.tests.voxelgame.main.net.packet.SBPacketPlayerBlockSet;

import java.util.Collection;

public class GameController{
    
    private final VoxelGame session;
    private final PlayerController playerController;
    private boolean f3Plus;
    
    public GameController(VoxelGame session){
        this.session = session;
        playerController = new PlayerController(session);
    }
    
    public VoxelGame getSession(){
        return session;
    }
    
    
    public void update(){
        final Options options = session.getOptions();
        final GameCamera camera = session.getGame().getCamera();
        final BlockRayCast blockRayCast = session.getGame().getBlockRayCast();
        final ClientLevel level = session.getGame().getLevel();
        
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
            
            if(Key.LEFT_CONTROL.isPressed()){
                if(Key.C.isDown())
                    Pize.setClipboardString(chat.getEnteringText());
                if(Key.V.isDown())
                    chat.getTextProcessor().insertLine(Pize.getClipboardString());
            }
            
            if(Key.ESCAPE.isDown())
                chat.close();
            
            return; // Abort subsequent control
            
        }else if(options.getKey(KeyMapping.CHAT).isDown())
            chat.open();
        else if(options.getKey(KeyMapping.COMMAND).isDown()){
            chat.openAsCommandLine();
        }
        
        /** Game **/
        
        // Player
        playerController.update();
        
        // Place/Destroy/Copy block
        if(Pize.isTouched() && blockRayCast.isSelected()){
            final LocalPlayer player = session.getGame().getPlayer();
            
            if(Pize.mouse().isLeftDown()){
                final Vec3i blockPos = blockRayCast.getSelectedBlockPosition();
                level.setBlock(blockPos.x, blockPos.y, blockPos.z, Blocks.AIR.getDefaultState());
                session.getGame().sendPacket(new SBPacketPlayerBlockSet(blockPos.x, blockPos.y, blockPos.z, Blocks.AIR.getDefaultState()));
                
                for(int i = 0; i < 100; i++){
                    session.getGame().spawnParticle(session.BREAK_PARTICLE, new Vec3f(
                        blockPos.x + Maths.random(1F),
                        blockPos.y + Maths.random(1F),
                        blockPos.z + Maths.random(1F)
                    ));
                }
            }else if(Pize.mouse().isRightDown()){
                placeBlock();
            }else if(Pize.mouse().isMiddleDown()){
                final Vec3i blockPos = blockRayCast.getSelectedBlockPosition();
                player.holdBlock = level.getBlockProps(blockPos.x, blockPos.y, blockPos.z);
            }
        }
        
        // Show mouse
        if(Key.M.isDown())
            playerController.getRotationController().toggleShowMouse();
        
        // Chunk border
        if(Key.F3.isPressed() && Key.G.isDown()){
            session.getRenderer().getWorldRenderer().getChunkBorderRenderer().toggleShow();
            f3Plus = true;
        }
        
        // Info Panel
        if(Key.F3.isReleased()){
            if(!f3Plus)
                session.getRenderer().getInfoRenderer().toggleOpen();
            
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
            session.getGame().sendPacket(new SBPacketPing(System.nanoTime()));
        
        // Polygon Mode
        if(Key.F9.isDown())
            Gl.polygonMode(Face.FRONT_AND_BACK, PolygonMode.LINE);
        if(Key.F8.isDown())
            Gl.polygonMode(Face.FRONT_AND_BACK, PolygonMode.FILL);
        
        // Exit
        if(Key.ESCAPE.isDown())
            Pize.exit();
    }
    
    private void placeBlock(){
        final BlockRayCast blockRayCast = session.getGame().getBlockRayCast();
        final ClientLevel level = session.getGame().getLevel();
        final Vec3i blockPos = blockRayCast.getImaginaryBlockPosition();
        final LocalPlayer player = session.getGame().getPlayer();
        
        final BlockProperties block = player.holdBlock;
        
        final BlockCollideShape collideShape = block.getCollideShape();
        if(collideShape != null){
            
            final BoundingBox3[] blockBoxes = collideShape.getBoxes();
            final BoxBody blockBox = new BoxBody(new Vec3f(blockPos));
            final Collection<Entity> entities = session.getGame().getLevel().getEntities();
            
            // Check intersect with player & entity
            for(BoundingBox3 box: blockBoxes){
                blockBox.getBoundingBox().resize(box);
                
                if(player.intersects(blockBox))
                    return;
                
                for(Entity entity: entities)
                    if(entity.intersects(blockBox))
                        return;
            }
        }
        
        level.setBlock(blockPos.x, blockPos.y, blockPos.z, block.getDefaultState());
        session.getGame().sendPacket(new SBPacketPlayerBlockSet(blockPos.x, blockPos.y, blockPos.z, block.getDefaultState()));
    }
    
    
    public final PlayerController getPlayerController(){
        return playerController;
    }
    
}
