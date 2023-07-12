package pize.tests.voxelgame.client.renderer.level;

import pize.app.Disposable;
import pize.files.Resource;
import pize.graphics.util.Shader;
import pize.math.vecmath.matrix.Matrix4f;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.client.block.shape.BlockCursorShape;
import pize.tests.voxelgame.client.control.camera.GameCamera;
import pize.tests.voxelgame.client.control.BlockRayCast;

public class BlockSelectorRenderer implements Disposable{
    
    private final LevelRenderer levelRenderer;
    private final Shader shader;
    private final Matrix4f translationMatrix;
    
    public BlockSelectorRenderer(LevelRenderer levelRenderer){
        this.levelRenderer = levelRenderer;
        
        this.shader = new Shader(new Resource("shader/line.vert"), new Resource("shader/line.frag"));
        
        this.translationMatrix = new Matrix4f();
    }
    
    
    public void render(GameCamera camera){
        final BlockRayCast blockRayCast = levelRenderer.getGameRenderer().getSession().getGame().getBlockRayCast();
        if(!blockRayCast.isSelected())
            return;
        
        shader.bind();
        shader.setUniform("u_projection", camera.getProjection());
        shader.setUniform("u_view", camera.getView());
        
        translationMatrix.toTranslated(new Vec3f(blockRayCast.getSelectedBlockPosition()).sub(camera.getX(), 0, camera.getZ()));
        shader.setUniform("u_model", translationMatrix);
        
        final BlockCursorShape shape = blockRayCast.getSelectedBlockProps().getCursorShape();
        if(shape != null)
            shape.render();
    }
    
    @Override
    public void dispose(){
        shader.dispose();
    }
    
}
