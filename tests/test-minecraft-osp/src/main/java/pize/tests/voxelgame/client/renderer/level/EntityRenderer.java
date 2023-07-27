package pize.tests.voxelgame.client.renderer.level;

import pize.app.Disposable;
import pize.tests.voxelgame.client.control.camera.GameCamera;
import pize.tests.voxelgame.client.entity.RemotePlayer;
import pize.tests.voxelgame.client.entity.model.PlayerModel;
import pize.tests.voxelgame.client.level.ClientLevel;
import pize.tests.voxelgame.main.entity.Entity;

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
