package pize.tests.minecraftosp.client.renderer.infopanel;

import pize.Pize;
import pize.app.Disposable;
import pize.graphics.util.batch.TextureBatch;
import pize.math.Maths;
import pize.math.vecmath.vector.Vec3f;
import pize.math.vecmath.vector.Vec3i;
import pize.tests.minecraftosp.Minecraft;
import pize.tests.minecraftosp.client.ClientGame;
import pize.tests.minecraftosp.client.audio.SoundPlayer;
import pize.tests.minecraftosp.client.net.ClientConnection;
import pize.tests.minecraftosp.main.modification.loader.Modification;
import pize.tests.minecraftosp.client.chunk.mesh.builder.ChunkBuilder;
import pize.tests.minecraftosp.client.control.BlockRayCast;
import pize.tests.minecraftosp.client.control.camera.GameCamera;
import pize.tests.minecraftosp.client.entity.LocalPlayer;
import pize.tests.minecraftosp.client.level.ClientLevel;
import pize.tests.minecraftosp.client.options.KeyMapping;
import pize.tests.minecraftosp.client.options.Options;
import pize.tests.minecraftosp.client.renderer.GameRenderer;
import pize.tests.minecraftosp.client.renderer.text.TextComponentBatch;
import pize.tests.minecraftosp.main.chunk.ChunkUtils;
import pize.tests.minecraftosp.main.time.GameTime;
import pize.tests.minecraftosp.main.text.Component;
import pize.tests.minecraftosp.main.text.TextColor;
import pize.util.Utils;

import java.util.Collection;

public class InfoPanelRenderer implements Disposable{
    
    private final GameRenderer gameRenderer;
    
    private int infoLineNum, hintLineNum;
    private final TextureBatch batch;
    private final TextComponentBatch textBatch;
    
    private boolean opened, animationEnded;
    private float animationTimeLine, panelOffsetX; // For open animation
    
    public InfoPanelRenderer(GameRenderer gameRenderer){
        this.gameRenderer = gameRenderer;
        
        textBatch = gameRenderer.getTextComponentBatch();
        batch = new TextureBatch(200);
        
        animationEnded = true;
        
        // Open
        new Thread(()->{
            Utils.delayElapsed(500);
            setOpened(true);
        }).start();
    }
    
    
    private void animate(){
        final int width = Pize.getWidth() / 2;
        
        if(!animationEnded){
            final float animationSpeed = Math.max(0.03F, 1 - animationTimeLine * animationTimeLine) * Pize.getDt() * 5;
            
            if(opened){ // open
                animationTimeLine += animationSpeed;
                
                if(animationTimeLine >= 1){
                    animationTimeLine = 1;
                    animationEnded = true;
                }
                
            }else{ // close
                animationTimeLine -= animationSpeed;
                
                if(animationTimeLine <= 0){
                    animationTimeLine = 0;
                    animationEnded = true;
                }
            }
            
            panelOffsetX = (1 - animationTimeLine) * -width;
        }
    }
    
    public void render(){
        animate();
        
        if(!opened && animationEnded)
            return;
        
        final Minecraft session = gameRenderer.getSession();
        final ClientGame game = session.getGame();
        final GameCamera camera = game.getCamera();
        
        if(camera == null)
            return;
        
        final Options options = session.getOptions();
        
        final ClientLevel level = game.getLevel();
        final ChunkBuilder chunkBuilder = level.getChunkManager().getChunkBuilders()[0];
        final GameTime time = game.getTime();
        
        final LocalPlayer player = game.getPlayer();
        final Vec3f playerPos = player.getLerpPosition();
        
        final BlockRayCast blockRayCast = game.getBlockRayCast();
        final Vec3i blockPos = blockRayCast.getSelectedBlockPosition();
        final Vec3i imaginaryBlockPos = blockRayCast.getImaginaryBlockPosition();
        final int lightLevel = level.getLight(imaginaryBlockPos.x, imaginaryBlockPos.y, imaginaryBlockPos.z);

        infoLineNum = 0;
        hintLineNum = 0;
        
        batch.begin();
        textBatch.setBackgroundColor(0, 0, 0, 0.2);
        
        /** -------- INFO -------- */
        
        // Game Version
        final Collection<Modification> loadedMods = session.getModLoader().getLoadedMods();
        final String modLoaderState = loadedMods.isEmpty() ? "Vanilla" : loadedMods.size() + " Mod(s) loaded";
        info(new Component().color(TextColor.DARK_GRAY)
            .text("Minecraft Open Source Edition")
            .text(" " + session.getVersion().getName() + " (" + modLoaderState + ")")
        );
        
        // FPS
        info(new Component().color(TextColor.YELLOW).text(Pize.getFPS() + " fps"));
        
        // Packets
        infoNextLine();
        info(TextColor.GRAY, "Packets sent", TextColor.YELLOW, ClientGame.tx);
        info(TextColor.GRAY, "Packets received", TextColor.YELLOW, ClientConnection.rx);
        
        // Position
        infoNextLine();
        info(new Component()
            .color(TextColor.RED  ).text("X")
            .color(TextColor.GREEN).text("Y")
            .color(TextColor.AQUA ).text("Z")
            .reset().text(": ")
            .color(TextColor.RED  ).text(String.format("%.3f", playerPos.x) + "  ")
            .color(TextColor.GREEN).text(String.format("%.3f", playerPos.y) + "  ")
            .color(TextColor.AQUA ).text(String.format("%.3f", playerPos.z))
        );
        
        // Block
        info(new Component()
            .color(TextColor.RED).text("Block").reset().text(": ")
            .color(TextColor.RED  ).text(blockPos.x + " ")
            .color(TextColor.GREEN).text(blockPos.y + " ")
            .color(TextColor.AQUA ).text(blockPos.z)
        );
        
        // Chunk Relative
        info(new Component()
            .color(TextColor.RED).text("Local").reset().text(": ")
            .color(TextColor.RED  ).text(ChunkUtils.getLocalCoord(playerPos.xf()) + " ")
            .color(TextColor.GREEN).text(ChunkUtils.getLocalCoord(playerPos.yf()) + " ")
            .color(TextColor.AQUA ).text(ChunkUtils.getLocalCoord(playerPos.zf()))
        );
        
        // Chunk Coordinates
        info(new Component()
            .color(TextColor.RED).text("Chunk").reset().text(": ")
            .color(TextColor.RED  ).text(    ChunkUtils.getChunkPos(playerPos.xf()) + " ")
            .color(TextColor.GREEN).text(ChunkUtils.getSectionIndex(playerPos.yf()) + " ")
            .color(TextColor.AQUA ).text(    ChunkUtils.getChunkPos(playerPos.zf()))
        );
        
        // Level
        infoNextLine();
        info(TextColor.DARK_GREEN, "Level", TextColor.AQUA, level.getConfiguration().getName());
        
        // Rotation
        info(TextColor.DARK_GREEN, "Rotation", TextColor.AQUA,
               "Yaw: " + String.format("%.1f", Maths.frac(camera.getRotation().yaw, -180, 180)) +
            " Pitch: " + String.format("%.1f", camera.getRotation().pitch)
        );
        
        // Speed
        info(TextColor.DARK_GREEN, "Move Speed: ", TextColor.AQUA, String.format("%.2f", player.getVelocity().len() * GameTime.TICKS_IN_SECOND) + " m/s");
        
        // Render Distance
        infoNextLine();
        info(TextColor.BLUE, "Render Distance", TextColor.YELLOW, options.getRenderDistance());
        
        // Chunks Rendered
        info(new Component()
            .color(TextColor.BLUE).text("Chunks (")
            .color(TextColor.YELLOW).text("rendered").color(TextColor.BLUE).text("/").color(TextColor.ORANGE).text("total").color(TextColor.BLUE).text(")").reset().text(": ")
            .color(TextColor.YELLOW).text(gameRenderer.getWorldRenderer().getChunkRenderer().getRenderedChunks())
            .color(TextColor.BLUE).text("/")
            .color(TextColor.ORANGE).text(level.getChunkManager().getAllChunks().size())
        );
        
        // Chunk Build Time
        info(new Component()
            .color(TextColor.BLUE).text("Chunk Build Time (")
            .color(TextColor.YELLOW).text("time").color(TextColor.BLUE).text("/").color(TextColor.ORANGE).text("vertices").color(TextColor.BLUE).text(")").reset().text(": ")
            .color(TextColor.YELLOW).text(chunkBuilder.buildTime + " ms")
            .color(TextColor.BLUE).text("/")
            .color(TextColor.ORANGE).text(chunkBuilder.verticesNum)
        );

        // Light
        infoNextLine();
        info(new Component()
                .color(TextColor.YELLOW).text("Light Level")
                .reset().text(": ")
                .color(TextColor.AQUA).text(lightLevel)
                .reset().text("/")
                .color(TextColor.AQUA).text(ChunkUtils.MAX_LIGHT_LEVEL)
        );

        // Time
        infoNextLine();
        info(TextColor.YELLOW, "Day: ", TextColor.AQUA, time.getDayNumber());
        info(TextColor.YELLOW, "Time: ", TextColor.AQUA, time.getTime());

        // Sounds
        infoNextLine();
        info(new Component()
                .color(TextColor.YELLOW).text("Sounds")
                .reset().text(": ")
                .color(TextColor.AQUA).text(session.getSoundPlayer().getPlayingSoundsNum())
                .reset().text("/")
                .color(TextColor.AQUA).text(SoundPlayer.MAX_SOUND_SOURCES)
        );
        
        // info("Threads:");
        // if(serverWorld != null) info("chunk find tps: " + serverWorld.getChunkManager().findTps.get());
        // if(serverWorld != null) info("chunk load tps: " + serverWorld.getChunkManager().loadTps.get());
        // info("chunk build tps: " + level.getChunkManager().buildTps.get());
        // info("chunk check tps: " + level.getChunkManager().checkTps.get());
        // info("Light time (I/D): " + WorldLight.increaseTime + " ms, " + WorldLight.decreaseTime + " ms");
        // Vec3i imaginaryPos = rayCast.getImaginaryBlockPosition();
        // Vec3i selectedPos = rayCast.getSelectedBlockPosition();
        // info("Selected light level (F/B): " + level.getLight(imaginaryPos.x, imaginaryPos.y, imaginaryPos.z) + ", " + level.getLight(selectedPos.x, selectedPos.y, selectedPos.z));
        
        // HINTS
        hint(new Component().color(TextColor.ORANGE).text("F9 / F8").color(TextColor.GRAY).text(" - Polygon Mode (Line / Fill)"));
        hint(new Component().color(TextColor.ORANGE).text("F3 + G").color(TextColor.GRAY).text(" - Show chunk border"));
        hint(new Component().color(TextColor.ORANGE).text("F3 + C").color(TextColor.GRAY).text(" - Update current chunk"));
        hint(new Component().color(TextColor.ORANGE).text("F3 + H").color(TextColor.GRAY).text(" - Update all chunks"));
        hintNextLine();
        hint(new Component().color(TextColor.ORANGE).text("ESCAPE").color(TextColor.GRAY).text(" - Exit"));
        hint(new Component().color(TextColor.ORANGE).text("M").color(TextColor.GRAY).text(" - Show mouse"));
        hint(new Component().color(TextColor.ORANGE).text(options.getKey(KeyMapping.CHAT)).color(TextColor.GRAY).text(" - Chat"));
        hint(new Component().color(TextColor.ORANGE).text(options.getKey(KeyMapping.TOGGLE_PERSPECTIVE)).color(TextColor.GRAY).text(" - Toggle perspective"));
        hint(new Component().color(TextColor.ORANGE).text(options.getKey(KeyMapping.ZOOM) + " + Mouse Wheel").color(TextColor.GRAY).text(" - zoom"));

        batch.end();
    }
    
    
    private void infoNextLine(){
        infoLineNum++;
    }

    private void hintNextLine(){
        hintLineNum++;
    }
    
    private void info(TextColor keyColor, String key, TextColor valueColor, String value){
        info(new Component().color(keyColor).text(key).reset().text(": ").color(valueColor).text(value));
    }
    
    private void info(TextColor keyColor, String key, TextColor valueColor, Object value){
        info(keyColor, key, valueColor, String.valueOf(value));
    }
    
    private void info(Component text){
        infoLineNum++;
        final float x = 5 + panelOffsetX;
        final float y = Pize.getHeight() - 5 - textBatch.getFont().getLineAdvanceScaled() * infoLineNum;
        textBatch.drawComponent(text, x, y);
    }
    
    private void hint(Component text){
        hintLineNum++;
        final float x = Pize.getWidth() - 5 - textBatch.getFont().getLineWidth(text.toString()) - panelOffsetX;
        final float y = Pize.getHeight() - 5 - textBatch.getFont().getLineAdvanceScaled() * hintLineNum;
        textBatch.drawComponent(text, x, y);
    }
    
    
    public void setOpened(boolean opened){
        if(!animationEnded)
            return;
        
        this.opened = opened;
        animationEnded = false;
    }
    
    public void toggleOpen(){
        setOpened(!opened);
    }
    
    
    @Override
    public void dispose(){
        batch.dispose();
    }
    
}
