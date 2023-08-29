package jpize.tests.minecraftosp.client.renderer.level;

import jpize.util.Disposable;
import jpize.files.Resource;
import jpize.graphics.util.Shader;
import jpize.math.vecmath.matrix.Matrix4f;
import jpize.math.vecmath.vector.Vec3f;
import jpize.tests.minecraftosp.client.block.shape.BlockCursor;
import jpize.tests.minecraftosp.client.control.camera.GameCamera;
import jpize.tests.minecraftosp.client.control.BlockRayCast;

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
        final BlockRayCast rayCast = levelRenderer.getGameRenderer().getSession().getGame().getBlockRayCast();
        if(!rayCast.isSelected())
            return;
        
        shader.bind();
        shader.setUniform("u_projection", camera.getProjection());
        shader.setUniform("u_view", camera.getView());
        
        translationMatrix.toTranslated(new Vec3f(rayCast.getSelectedBlockPosition()).sub(camera.getX(), 0, camera.getZ()));
        shader.setUniform("u_model", translationMatrix);
        
        final BlockCursor shape = rayCast.getSelectedBlockProps().getCursor();
        if(shape != null)
            shape.render();
    }
    
    @Override
    public void dispose(){
        shader.dispose();
    }
    
}
