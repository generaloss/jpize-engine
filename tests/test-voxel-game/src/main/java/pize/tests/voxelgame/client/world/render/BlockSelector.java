package pize.tests.voxelgame.client.world.render;

import pize.tests.voxelgame.client.control.GameCamera;
import pize.tests.voxelgame.client.control.RayCast;
import pize.activity.Disposable;
import pize.graphics.gl.Primitive;
import pize.graphics.gl.Type;
import pize.graphics.vertex.Mesh;
import pize.graphics.vertex.VertexAttr;
import pize.math.vecmath.matrix.Matrix4f;
import pize.math.vecmath.vector.Vec3f;

public class BlockSelector implements Disposable{
    
    private final WorldRenderer rendererOF;
    
    private final Mesh mesh;
    private final Matrix4f translationMatrix;
    
    public BlockSelector(WorldRenderer rendererOF){
        this.rendererOF = rendererOF;
        
        mesh = new Mesh(new VertexAttr(3, Type.FLOAT));
        mesh.setRenderMode(Primitive.LINES);
        
        mesh.setVertices(new float[]{
            1, 1, 1, //0
            0, 1, 1, //1
            1, 0, 1, //2
            0, 0, 1, //3
            1, 1, 0, //4
            0, 1, 0, //5
            1, 0, 0, //6
            0, 0, 0, //7
        });
        mesh.setIndices(new int[]{
            7, 6, 6, 2, 2, 3, 3, 7, //Top
            0, 4, 4, 5, 5, 1, 1, 0, //Bottom
            0, 2, 2, 6, 6, 4, 4, 0, //Left
            7, 3, 3, 1, 1, 5, 5, 7, //Right
            3, 2, 2, 0, 0, 1, 1, 3, //Front
            4, 6, 6, 7, 7, 5, 5, 4, //Back
        });
        
        translationMatrix = new Matrix4f();
    }
    
    public WorldRenderer getWorldRendererOf(){
        return rendererOF;
    }
    
    
    public void render(){
        final RayCast rayCast = rendererOF.getSessionOf().getRayCast();
        if(!rayCast.isSelected())
            return;
        
        final GameCamera camera = rendererOF.getSessionOf().getCamera();
        translationMatrix.toTranslated(new Vec3f(rayCast.getSelectedBlockPosition()).sub(camera.getX(), 0, camera.getZ()));
        
        rendererOF.lineShader.setUniform("u_model", translationMatrix);
        mesh.render();
    }
    
    @Override
    public void dispose(){
        mesh.dispose();
    }
    
}
