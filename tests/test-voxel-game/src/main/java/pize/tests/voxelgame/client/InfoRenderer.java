package pize.tests.voxelgame.client;

import pize.Pize;
import pize.app.Disposable;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.graphics.util.batch.TextureBatch;
import pize.math.Maths;
import pize.tests.voxelgame.Main;
import pize.tests.voxelgame.client.chunk.mesh.ChunkBuilder;
import pize.tests.voxelgame.client.control.GameCamera;
import pize.tests.voxelgame.client.entity.LocalPlayer;
import pize.tests.voxelgame.client.level.ClientLevel;
import pize.tests.voxelgame.client.net.ClientPacketHandler;
import pize.tests.voxelgame.client.options.KeyMapping;
import pize.tests.voxelgame.client.options.Options;

public class InfoRenderer implements Disposable{
    
    private final Main sessionOF;
    
    private int infoLineNum, hintLineNum;
    private final TextureBatch batch;
    private final BitmapFont font;
    
    public InfoRenderer(Main sessionOF){
        this.sessionOF = sessionOF;
        
        batch = new TextureBatch(200);
        font = FontLoader.loadFnt("font/default.fnt");
        font.setScale(2);
    }
    
    public Main getSessionOf(){
        return sessionOF;
    }
    
    public void render(){
        renderInfo();
    }
    
    private void renderInfo(){
        if(sessionOF.getGame().getCamera() == null)
            return;
        
        Options options = sessionOF.getOptions();
        if(!options.isShowFPS())
            return;
        
        GameCamera camera = sessionOF.getGame().getCamera();
        ClientLevel clientWorld = sessionOF.getGame().getLevel();
        LocalPlayer player = sessionOF.getGame().getPlayer();
        
        infoLineNum = 0;
        hintLineNum = 0;
        
        font.setScale(Maths.round(Pize.getHeight() / 300F));
        batch.begin();
        
        // INFO
        info("fps: " + Pize.getFPS());
        
        info("position: " + player.getPosition().x + ", " + player.getPosition().y + ", " + player.getPosition().z);
        info("chunk: " + camera.chunkX() + ", " + camera.chunkZ());
        info("tx: " + ClientGame.tx + ", rx: " + ClientPacketHandler.rx);
        info("meshes: " + clientWorld.getChunkManager().getMeshes().size());
        info("render chunks: " + clientWorld.getChunkManager().getChunks().stream().filter(camera::isChunkSeen).count());
        info("Chunk build time (T/V): " + ChunkBuilder.buildTime + " ms, " + ChunkBuilder.vertexCount + " vertices");
        info("Player speed: " + LocalPlayer.speed);
        
        // info("Threads:");
        // if(serverWorld != null) info("chunk find tps: " + serverWorld.getChunkManager().findTps.get());
        // if(serverWorld != null) info("chunk load tps: " + serverWorld.getChunkManager().loadTps.get());
        // info("chunk build tps: " + clientWorld.getChunkManager().buildTps.get());
        // info("chunk check tps: " + clientWorld.getChunkManager().checkTps.get());
        // info("Light time (I/D): " + WorldLight.increaseTime + " ms, " + WorldLight.decreaseTime + " ms");
        // Vec3i imaginaryPos = rayCast.getImaginaryBlockPosition();
        // Vec3i selectedPos = rayCast.getSelectedBlockPosition();
        // info("Selected light level (F/B): " + clientWorld.getLight(imaginaryPos.x, imaginaryPos.y, imaginaryPos.z) + ", " + clientWorld.getLight(selectedPos.x, selectedPos.y, selectedPos.z));
        
        // CTRL HINT
        hint("1, 2, 3 - set render mode");
        hint("L - show mouse");
        hint("F3 + G - show chunk border");
        hint(options.getKey(KeyMapping.ZOOM) + " + Mouse Wheel - zoom");
        
        batch.end();
    }
    
    private void info(String text){
        infoLineNum++;
        
        final float scale = font.getScale();
        batch.setColor(0, 0, 0, 1);
        font.drawText(batch, text, 25 + scale, Pize.getHeight() - 25 - font.getScaledLineHeight() * infoLineNum - scale);
        
        batch.setColor(1, 1, 1, 1);
        font.drawText(batch, text, 25, Pize.getHeight() - 25 - font.getScaledLineHeight() * infoLineNum);
    }
    
    private void hint(String text){
        hintLineNum++;
        
        final float scale = font.getScale();
        batch.setColor(0, 0, 0, 1);
        font.drawText(batch, text, 25 + scale, 25 - scale + font.getScaledLineHeight() * hintLineNum);
        
        batch.setColor(1, 1, 1, 1);
        font.drawText(batch, text, 25, 25 + font.getScaledLineHeight() * hintLineNum);
    }
    
    @Override
    public void dispose(){
        batch.dispose();
        font.dispose();
    }
    
}
