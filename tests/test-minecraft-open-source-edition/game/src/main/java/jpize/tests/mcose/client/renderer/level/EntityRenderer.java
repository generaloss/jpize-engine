package jpize.tests.mcose.client.renderer.level;

import jpize.util.Disposable;
import jpize.tests.mcose.main.entity.Entity;
import jpize.tests.mcose.client.control.camera.GameCamera;
import jpize.tests.mcose.client.entity.RemotePlayer;
import jpize.tests.mcose.client.entity.model.PlayerModel;
import jpize.tests.mcose.client.level.ClientLevel;

public class EntityRenderer implements Disposable{
    
    final LevelRenderer levelRenderer;
    
    public EntityRenderer(LevelRenderer levelRenderer){
        this.levelRenderer = levelRenderer;
    }
    
    
    public void render(GameCamera camera){
        final ClientLevel level = levelRenderer.getGameRenderer().getSession().getGame().getLevel();
        if(level == null)
            return;

        for(Entity entity : level.getEntities()){
            
            // Remote players
            if(entity instanceof RemotePlayer remotePlayer){
                remotePlayer.updateInterpolation();
                
                final PlayerModel model = remotePlayer.getModel();
                if(model != null){
                    model.animate();
                    model.render(camera);
                }
            }
            
        }
    }
    
    @Override
    public void dispose(){ }
    
}
