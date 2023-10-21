package jpize.tests.mcose.client.renderer.infopanel;

import jpize.Jpize;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.util.batch.TextureBatch;
import jpize.math.Maths;
import jpize.math.vecmath.vector.Vec3f;
import jpize.math.vecmath.vector.Vec3i;
import jpize.tests.mcose.client.Minecraft;
import jpize.tests.mcose.client.ClientGame;
import jpize.tests.mcose.client.audio.SoundPlayer;
import jpize.tests.mcose.client.chunk.builder.ChunkBuilder;
import jpize.tests.mcose.client.control.BlockRayCast;
import jpize.tests.mcose.client.control.camera.GameCamera;
import jpize.tests.mcose.client.entity.LocalPlayer;
import jpize.tests.mcose.client.level.ClientLevel;
import jpize.tests.mcose.client.options.KeyMapping;
import jpize.tests.mcose.client.options.Options;
import jpize.tests.mcose.client.renderer.GameRenderer;
import jpize.tests.mcose.client.renderer.text.TextComponentBatch;
import jpize.tests.mcose.main.chunk.ChunkUtils;
import jpize.tests.mcose.main.modification.loader.Modification;
import jpize.tests.mcose.main.text.Component;
import jpize.tests.mcose.main.text.TextColor;
import jpize.tests.mcose.main.time.GameTime;
import jpize.util.Disposable;
import jpize.util.Utils;

import java.util.Collection;

public class InfoRenderer implements Disposable{
    
    private final GameRenderer gameRenderer;
    
    private int infoLineNum, hintLineNum;
    private final TextureBatch batch;
    private final TextComponentBatch textBatch;
    
    private boolean open, animationEnded;
    private float animationTimeLine, panelOffsetX; // For open animation
    
    public InfoRenderer(GameRenderer gameRenderer){
        this.gameRenderer = gameRenderer;
        
        textBatch = gameRenderer.getTextComponentBatch();
        batch = new TextureBatch(200);
        
        animationEnded = true;
        
        // Open
        new Thread(()->{
            Utils.delayElapsed(500);
            setOpen(true);
        }).start();
    }
    
    
    private void animate(){
        final int width = Jpize.getWidth() / 2;
        
        if(!animationEnded){
            final float animationSpeed = Math.max(0.03F, 1 - animationTimeLine * animationTimeLine) * Jpize.getDt() * 5;
            
            if(open){ // open
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
        
        if(!open && animationEnded)
            return;
        
        final Minecraft session = gameRenderer.getSession();
        final ClientGame game = session.getGame();
        final GameCamera camera = game.getCamera();
        
        if(camera == null)
            return;
        
        final Options options = session.getOptions();
        
        final ClientLevel level = game.getLevel();
        if(level == null)
            return;

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
        info(new Component().color(TextColor.YELLOW).text(Jpize.getFPS() + " fps"));
        
        // Packets
        infoNextLine();
        info(TextColor.GRAY, "Packets sent", TextColor.YELLOW, game.getConnectionHandler().getTX());
        info(TextColor.GRAY, "Packets received", TextColor.YELLOW, game.getConnectionHandler().getRX());
        
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
            .color(TextColor.RED  ).text(ChunkUtils.getLocalCoord(playerPos.xFloor()) + " ")
            .color(TextColor.GREEN).text(ChunkUtils.getLocalCoord(playerPos.yFloor()) + " ")
            .color(TextColor.AQUA ).text(ChunkUtils.getLocalCoord(playerPos.zFloor()))
        );
        
        // Chunk Coordinates
        info(new Component()
            .color(TextColor.RED).text("Chunk").reset().text(": ")
            .color(TextColor.RED  ).text(    ChunkUtils.getChunkPos(playerPos.xFloor()) + " ")
            .color(TextColor.GREEN).text(ChunkUtils.getSectionIndex(playerPos.yFloor()) + " ")
            .color(TextColor.AQUA ).text(    ChunkUtils.getChunkPos(playerPos.zFloor()))
        );
        
        // Level
        infoNextLine();
        info(TextColor.DARK_GREEN, "Level", TextColor.AQUA, level.getConfiguration().getName());

        // Biome
        info(TextColor.DARK_GREEN, "Biome", TextColor.AQUA, level.getBiome(playerPos.xFloor(), playerPos.zFloor()));

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

        final int memoryTotal = Maths.round(Runtime.getRuntime().totalMemory() / 1024F / 1024);
        final int memoryFree = Maths.round(Runtime.getRuntime().freeMemory() / 1024F / 1024);
        info(new Component()
                .color(TextColor.YELLOW).text("Used Memory")
                .reset().text(": ")
                .color(TextColor.AQUA).text(memoryTotal - memoryFree)
                .reset().text("/")
                .color(TextColor.AQUA).text(memoryTotal + " MB")
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
        final BitmapFont font = textBatch.getFont();

        infoLineNum++;
        final float x = 5 + panelOffsetX;
        final float y = Jpize.getHeight() - 5 - font.getOptions().getAdvanceScaled() * infoLineNum;
        textBatch.drawComponent(text, x, y);
    }
    
    private void hint(Component text){
        final BitmapFont font = textBatch.getFont();

        hintLineNum++;
        final float x = Jpize.getWidth() - 5 - textBatch.getFont().getLineWidth(text.toString()) - panelOffsetX;
        final float y = Jpize.getHeight() - 5 - font.getOptions().getAdvanceScaled() * hintLineNum;
        textBatch.drawComponent(text, x, y);
    }
    

    public boolean isOpen(){
        return open;
    }

    public void setOpen(boolean opened){
        if(!animationEnded)
            return;
        
        this.open = opened;
        animationEnded = false;
    }
    
    public void toggleOpen(){
        setOpen(!open);
    }
    
    
    @Override
    public void dispose(){
        batch.dispose();
    }
    
}
