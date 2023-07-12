package pize.tests.voxelgame.client.renderer.level;

import pize.Pize;
import pize.app.Disposable;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.client.ClientGame;
import pize.tests.voxelgame.client.level.ClientLevel;

public class VignetteRenderer implements Disposable{
    
    final LevelRenderer levelRenderer;
    private final Texture vignetteTexture;
    float vignette;
    
    public VignetteRenderer(LevelRenderer levelRenderer){
        this.levelRenderer = levelRenderer;
        
        vignetteTexture = new Texture("texture/vignette.png");
    }
    
    
    public void render(TextureBatch batch){
        batch.begin();
        
        // Get light level
        final ClientGame game = levelRenderer.getGameRenderer().getSession().getGame();
        final Vec3f playerPos = game.getPlayer().getPosition();
        final ClientLevel level = game.getLevel();
        final float light = level.getLight(playerPos.xf(), playerPos.yf(), playerPos.zf());
        
        // Interpolation
        vignette += ((1 - light / 15F) - vignette) / 100F;
        
        // Render
        batch.setAlpha(vignette);
        batch.draw(vignetteTexture, 0, 0, Pize.getWidth(), Pize.getHeight());
        batch.resetColor();
        batch.end();
    }
    
    @Override
    public void dispose(){
        vignetteTexture.dispose();
    }
    
}
