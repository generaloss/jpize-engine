package jpize.tests.minecraftosp.client.control;

import jpize.Jpize;
import jpize.gl.tesselation.GlFace;
import jpize.gl.Gl;
import jpize.gl.tesselation.GlPolygonMode;
import jpize.glfw.key.Key;
import jpize.math.Maths;
import jpize.math.vecmath.vector.Vec3f;
import jpize.math.vecmath.vector.Vec3i;
import jpize.physic.BoundingBox3f;
import jpize.physic.BoxBody;
import jpize.tests.minecraftosp.Minecraft;
import jpize.tests.minecraftosp.client.block.Block;
import jpize.tests.minecraftosp.client.block.Blocks;
import jpize.tests.minecraftosp.client.block.shape.BlockCollide;
import jpize.tests.minecraftosp.client.chat.Chat;
import jpize.tests.minecraftosp.client.control.camera.GameCamera;
import jpize.tests.minecraftosp.client.entity.LocalPlayer;
import jpize.tests.minecraftosp.client.level.ClientLevel;
import jpize.tests.minecraftosp.client.options.KeyMapping;
import jpize.tests.minecraftosp.client.options.Options;
import jpize.tests.minecraftosp.client.renderer.infopanel.ChunkInfoRenderer;
import jpize.tests.minecraftosp.client.renderer.infopanel.InfoRenderer;
import jpize.tests.minecraftosp.main.block.BlockData;
import jpize.tests.minecraftosp.main.entity.Entity;
import jpize.tests.minecraftosp.main.net.packet.SBPacketChunkRequest;
import jpize.tests.minecraftosp.main.net.packet.SBPacketPing;
import jpize.tests.minecraftosp.main.net.packet.SBPacketPlayerBlockSet;

import java.util.Collection;

public class GameController{
    
    private final Minecraft session;
    private final PlayerController playerController;
    private boolean f3Plus;
    
    public GameController(Minecraft session){
        this.session = session;
        playerController = new PlayerController(session);
    }
    
    public Minecraft getSession(){
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
            Jpize.window().toggleFullscreen();
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
                    Jpize.setClipboardString(chat.getEnteringText());
                if(Key.V.isDown())
                    chat.getTextProcessor().insertLine(Jpize.getClipboardString());
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

        if(camera == null)
            return;

        // F3 + ...
        if(Key.F3.isPressed()){
            // G - Chunk border
            if(Key.G.isDown()){
                session.getRenderer().getWorldRenderer().getChunkBorderRenderer().toggleShow();
                f3Plus = true;
                return;
            }

            // R - Reload chunks
            if(Key.H.isDown()){
                session.getGame().getLevel().getChunkManager().reload();
                f3Plus = true;
                return;
            }

            // C - Reload chunks
            if(Key.C.isDown()){
                getSession().getGame().sendPacket(new SBPacketChunkRequest(camera.chunkX(), camera.chunkZ()));
                f3Plus = true;
                return;
            }
        }

        // Info Panel
        if(Key.F3.isReleased()){
            if(!f3Plus){
                final InfoRenderer info = session.getRenderer().getInfoRenderer();
                final ChunkInfoRenderer chunkInfo = session.getRenderer().getChunkInfoRenderer();

                info.toggleOpen();

                if(info.isOpen()){
                    if(Key.LEFT_SHIFT.isPressed())
                        chunkInfo.setOpen(true);
                }else
                    chunkInfo.setOpen(false);
            }

            f3Plus = false;
        }

        // Place/Destroy/Copy block
        if(blockRayCast.isSelected()){
            final LocalPlayer player = session.getGame().getPlayer();
            
            if(Jpize.mouse().isLeftDown() || Key.U.isPressed()){
                final Vec3i blockPos = blockRayCast.getSelectedBlockPosition();
                level.setBlock(blockPos.x, blockPos.y, blockPos.z, Blocks.AIR);
                session.getGame().sendPacket(new SBPacketPlayerBlockSet(blockPos.x, blockPos.y, blockPos.z, Blocks.AIR.getDefaultData()));
                
                for(int i = 0; i < 100; i++){
                    session.getGame().spawnParticle(session.BREAK_PARTICLE, new Vec3f(
                        blockPos.x + Maths.random(1F),
                        blockPos.y + Maths.random(1F),
                        blockPos.z + Maths.random(1F)
                    ));
                }
            }else if(Jpize.mouse().isRightDown() || Key.J.isPressed()){
                placeBlock();
            }else if(Jpize.mouse().isMiddleDown()){
                final Vec3i blockPos = blockRayCast.getSelectedBlockPosition();
                player.holdBlock = level.getBlockProps(blockPos.x, blockPos.y, blockPos.z).getBlock();
            }
        }
        
        // Show mouse
        if(Key.M.isDown())
            playerController.getRotationController().toggleShowMouse();
        
        // Camera zoom
        if(options.getKey(KeyMapping.ZOOM).isDown())
            camera.setZoom(10);
        else if(options.getKey(KeyMapping.ZOOM).isPressed()){
            camera.setZoom(camera.getZoom() + Jpize.mouse().getScroll() * (camera.getZoom() / 8));
        }else if(options.getKey(KeyMapping.ZOOM).isReleased())
            camera.setZoom(1);
        
        // Ping server
        if(Key.P.isDown())
            session.getGame().sendPacket(new SBPacketPing(System.nanoTime()));
        
        // Polygon Mode
        if(Key.F9.isDown())
            Gl.polygonMode(GlFace.FRONT_AND_BACK, GlPolygonMode.LINE);
        if(Key.F8.isDown())
            Gl.polygonMode(GlFace.FRONT_AND_BACK, GlPolygonMode.FILL);
        
        // Exit
        if(Key.ESCAPE.isDown())
            Jpize.exit();

        // Player
        playerController.update();
    }
    
    private void placeBlock(){
        final BlockRayCast blockRayCast = session.getGame().getBlockRayCast();
        final ClientLevel level = session.getGame().getLevel();
        final Vec3i blockPos = blockRayCast.getImaginaryBlockPosition();
        final LocalPlayer player = session.getGame().getPlayer();
        
        final Block block = player.holdBlock;
        final int blockStates = block.getStates().size();

        byte blockState = 0;
        if(blockStates > 1)
            blockState = (byte) Maths.random(0, blockStates - 1);

        short blockData = BlockData.getData(block, blockState);
        
        final BlockCollide collideShape = block.getState(blockState).getCollide();
        if(collideShape != null){
            
            final BoundingBox3f[] blockBoxes = collideShape.getBoxes();
            final BoxBody blockBox = new BoxBody(new Vec3f(blockPos));
            final Collection<Entity> entities = session.getGame().getLevel().getEntities();
            
            // Check intersect with player & entity
            for(BoundingBox3f box: blockBoxes){
                blockBox.getBoundingBox().resize(box);
                
                if(player.intersects(blockBox))
                    return;
                
                for(Entity entity: entities)
                    if(entity.intersects(blockBox))
                        return;
            }
        }
        
        level.setBlockState(blockPos.x, blockPos.y, blockPos.z, blockData);
        session.getGame().sendPacket(new SBPacketPlayerBlockSet(blockPos.x, blockPos.y, blockPos.z, blockData));
    }
    
    
    public final PlayerController getPlayerController(){
        return playerController;
    }
    
}
