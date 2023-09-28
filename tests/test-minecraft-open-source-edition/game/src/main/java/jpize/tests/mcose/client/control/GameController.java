package jpize.tests.mcose.client.control;

import jpize.Jpize;
import jpize.gl.tesselation.GlFace;
import jpize.gl.Gl;
import jpize.gl.tesselation.GlPolygonMode;
import jpize.glfw.key.Key;
import jpize.math.Maths;
import jpize.math.vecmath.vector.Vec3f;
import jpize.math.vecmath.vector.Vec3i;
import jpize.physic.axisaligned.box.AABox;
import jpize.physic.axisaligned.box.AABoxBody;
import jpize.tests.mcose.client.Minecraft;
import jpize.tests.mcose.client.block.Block;
import jpize.tests.mcose.client.block.Blocks;
import jpize.tests.mcose.client.block.shape.BlockCollide;
import jpize.tests.mcose.client.chat.Chat;
import jpize.tests.mcose.client.control.camera.GameCamera;
import jpize.tests.mcose.client.entity.LocalPlayer;
import jpize.tests.mcose.client.level.ClientLevel;
import jpize.tests.mcose.client.options.KeyMapping;
import jpize.tests.mcose.client.options.Options;
import jpize.tests.mcose.client.renderer.infopanel.ChunkInfoRenderer;
import jpize.tests.mcose.client.renderer.infopanel.InfoRenderer;
import jpize.tests.mcose.main.block.BlockData;
import jpize.tests.mcose.main.entity.Entity;
import jpize.tests.mcose.main.net.packet.serverbound.SBPacketChunkRequest;
import jpize.tests.mcose.main.net.packet.serverbound.SBPacketPing;
import jpize.tests.mcose.main.net.packet.serverbound.SBPacketPlayerBlockSet;

import java.util.Collection;

public class GameController{
    
    private final Minecraft session;
    private boolean f3Plus;
    
    public GameController(Minecraft session){
        this.session = session;
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
        if(Key.F11.isDown())
            Jpize.window().toggleFullscreen();
        
        
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
                getSession().getGame().getConnectionHandler().sendPacket(new SBPacketChunkRequest(camera.chunkX(), camera.chunkZ()));
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
                session.getGame().getConnectionHandler().sendPacket(new SBPacketPlayerBlockSet(blockPos.x, blockPos.y, blockPos.z, Blocks.AIR.getDefaultData()));
                
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
        //if(Key.M.isDown())
        //    playerController.getRotationController().toggleShowMouse();
        
        // Camera zoom
        if(options.getKey(KeyMapping.ZOOM).isDown())
            camera.setZoom(10);
        else if(options.getKey(KeyMapping.ZOOM).isPressed()){
            camera.setZoom(camera.getZoom() + Jpize.mouse().getScroll() * (camera.getZoom() / 8));
        }else if(options.getKey(KeyMapping.ZOOM).isReleased())
            camera.setZoom(1);
        
        // Ping server
        if(Key.P.isDown())
            session.getGame().getConnectionHandler().sendPacket(new SBPacketPing(System.nanoTime()));
        
        // Polygon Mode
        if(Key.F9.isDown())
            Gl.polygonMode(GlFace.FRONT_AND_BACK, GlPolygonMode.LINE);
        if(Key.F8.isDown())
            Gl.polygonMode(GlFace.FRONT_AND_BACK, GlPolygonMode.FILL);
        
        // Exit
        if(Key.ESCAPE.isDown())
            Jpize.exit();
    }
    
    private void placeBlock(){
        final BlockRayCast blockRayCast = session.getGame().getBlockRayCast();
        final ClientLevel level = session.getGame().getLevel();
        final Vec3i blockPos = blockRayCast.getImaginaryBlockPosition();
        final LocalPlayer player = session.getGame().getPlayer();
        
        final Block block = player.holdBlock;
        final int blockStates = block.getStates().size();

        byte blockState = 0;

        if(block == Blocks.OAK_LOG || block == Blocks.BIRCH_LOG || block == Blocks.SPRUCE_LOG){
            // F:0  -Y:2  +Y:1
            int pitch = (int) ((player.getRotation().pitch % 360 / 90 % 4 + 4.5F) % 4);
            if(pitch == 3)
                pitch--;

            // -X:2  +X:0  -Z:3  +Z:1
            if(pitch == 0){
                int yaw = (int) ((player.getRotation().yaw % 360 / 90 % 4 + 4.5F) % 4);
                if(yaw == 2 || yaw == 0)
                    blockState = 2;

                if(yaw == 3 || yaw == 1)
                    blockState = 1;
            }
        }

        else if(blockStates > 1)
            blockState = (byte) Maths.random(0, blockStates - 1);

        final short blockData = BlockData.getData(block, blockState);
        
        final BlockCollide collideShape = block.getState(blockState).getCollide();
        if(collideShape != null){
            
            final AABox[] blockBoxes = collideShape.getBoxes();
            final AABoxBody blockBox = new AABoxBody(new Vec3f(blockPos));
            final Collection<Entity> entities = session.getGame().getLevel().getEntities();
            
            // Check intersect with player & entity
            for(AABox box: blockBoxes){
                blockBox.getBoundingBox().resize(box);
                
                if(player.intersects(blockBox))
                    return;
                
                for(Entity entity: entities)
                    if(entity.intersects(blockBox))
                        return;
            }
        }
        
        level.setBlockState(blockPos.x, blockPos.y, blockPos.z, blockData);
        session.getGame().getConnectionHandler().sendPacket(new SBPacketPlayerBlockSet(blockPos.x, blockPos.y, blockPos.z, blockData));
    }
    
}
