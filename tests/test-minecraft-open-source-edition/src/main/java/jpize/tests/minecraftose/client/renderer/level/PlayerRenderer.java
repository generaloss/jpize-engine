package jpize.tests.minecraftose.client.renderer.level;

import jpize.util.Disposable;
import jpize.tests.minecraftose.client.control.camera.GameCamera;
import jpize.tests.minecraftose.client.control.camera.PerspectiveType;
import jpize.tests.minecraftose.client.entity.model.PlayerModel;
import jpize.tests.minecraftose.client.options.Options;

public class PlayerRenderer implements Disposable{
    
    final LevelRenderer levelRenderer;
    
    public PlayerRenderer(LevelRenderer levelRenderer){
        this.levelRenderer = levelRenderer;
    }
    
    
    public void render(GameCamera camera){
        final Options options = levelRenderer.getGameRenderer().getSession().getOptions();
        final PlayerModel playerModel = levelRenderer.getGameRenderer().getSession().getGame().getPlayer().getModel();
        
        // Render player
        if(playerModel != null){
            playerModel.animate();
            if(camera.getPerspective() != PerspectiveType.FIRST_PERSON || options.isFirstPersonModel())
                playerModel.render(camera);
        }
    }
    
    @Override
    public void dispose(){ }
    
}