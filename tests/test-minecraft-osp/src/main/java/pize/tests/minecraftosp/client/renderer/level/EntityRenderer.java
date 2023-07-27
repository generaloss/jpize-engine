package pize.tests.minecraftosp.client.renderer.level;

import pize.app.Disposable;
import pize.tests.minecraftosp.main.entity.Entity;
import pize.tests.minecraftosp.client.control.camera.GameCamera;
import pize.tests.minecraftosp.client.entity.RemotePlayer;
import pize.tests.minecraftosp.client.entity.model.PlayerModel;
import pize.tests.minecraftosp.client.level.ClientLevel;

public class EntityRenderer implements Disposable{
    
    final LevelRenderer levelRenderer;
    
    public EntityRenderer(LevelRenderer levelRenderer){
        this.levelRenderer = levelRenderer;
    }
    
    
    public void render(GameCamera camera){
        final ClientLevel level = levelRenderer.getGameRenderer().getSession().getGame().getLevel();
        
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
