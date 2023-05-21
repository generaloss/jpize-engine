package megalul.projectvostok.client;

import megalul.projectvostok.Main;
import megalul.projectvostok.SessionStatus;
import megalul.projectvostok.client.chunk.mesh.ChunkBuilder;
import megalul.projectvostok.client.control.GameCamera;
import megalul.projectvostok.client.control.RayCast;
import megalul.projectvostok.client.options.KeyMapping;
import megalul.projectvostok.client.options.Options;
import megalul.projectvostok.client.world.ClientWorld;
import megalul.projectvostok.server.world.ServerWorld;
import megalul.projectvostok.server.world.light.WorldLight;
import pize.Pize;
import pize.context.Disposable;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.graphics.util.batch.TextureBatch;
import pize.math.vecmath.vector.Vec3i;

public class InfoRenderer implements Disposable{
    
    private final Main sessionOF;
    
    private int infoLineNum, hintLineNum;
    private final TextureBatch batch;
    private final BitmapFont font;
    
    public InfoRenderer(Main sessionOF){
        this.sessionOF = sessionOF;
        
        batch = new TextureBatch(200);
        font = FontLoader.getDefault();
    }
    
    public Main getSessionOf(){
        return sessionOF;
    }
    
    public void render(){
        renderInfo();
    }
    
    private void renderInfo(){
        Options options = sessionOF.getOptions();
        GameCamera camera = sessionOF.getCamera();
        RayCast rayCast = sessionOF.getRayCast();
        ClientWorld clientWorld = sessionOF.getNet().getWorld();
        ServerWorld serverWorld = sessionOF.getLocalServer().getDefaultWorld();
        
        if(!options.isShowFPS())
            return;
        
        infoLineNum = 0;
        hintLineNum = 0;
        
        batch.setColor(0.5F, 0.4F, 0.1F, 1);
        batch.begin();
        
        // INFO
        info("fps: " + Pize.getFPS());
        
        if(sessionOF.status == SessionStatus.MENU || true)
            return;
        
        info("position: " + camera.getX() + ", " + camera.getY() + ", " + camera.getZ());
        info("chunk: " + camera.chunkX() + ", " + camera.chunkZ());
        info("Threads:");
        info("chunk find tps: " + serverWorld.getChunkProvider().findTps.get());
        info("chunk load tps: " + serverWorld.getChunkProvider().loadTps.get());
        info("chunk build tps: " + clientWorld.getChunkManager().buildTps.get());
        info("chunk check tps: " + clientWorld.getChunkManager().checkTps.get());
        info("meshes: " + clientWorld.getChunkManager().getMeshes().size());
        info("render chunks: " + clientWorld.getChunkManager().getChunks().stream().filter(camera::isChunkSeen).count());
        info("Light time (I/D): " + WorldLight.increaseTime + " ms, " + WorldLight.decreaseTime + " ms");
        info("Chunk build time (T/V): " + ChunkBuilder.buildTime + " ms, " + ChunkBuilder.vertexCount + " vertices");
        Vec3i imaginaryPos = rayCast.getImaginaryBlockPosition();
        Vec3i selectedPos = rayCast.getSelectedBlockPosition();
        info("Selected light level (F/B): " + clientWorld.getLight(imaginaryPos.x, imaginaryPos.y, imaginaryPos.z) + ", " + clientWorld.getLight(selectedPos.x, selectedPos.y, selectedPos.z));
        
        // CTRL HINT
        hint("1, 2, 3 - set render mode");
        hint("R - show mouse");
        hint("F3 + G - show chunk border");
        hint(options.getKey(KeyMapping.ZOOM) + " + Mouse Wheel - zoom");
        hint("L - stop loading chunks");
        
        batch.end();
    }
    
    private void info(String text){
        infoLineNum++;
        font.drawText(batch, text, 25, Pize.getHeight() - 25 - font.getScaledLineHeight() * infoLineNum);
    }
    
    private void hint(String text){
        hintLineNum++;
        font.drawText(batch, text, 25, 25 + font.getScaledLineHeight() * hintLineNum);
    }
    
    @Override
    public void dispose(){
        batch.dispose();
        font.dispose();
    }
    
}
