package pize.tests.voxelgame.client.renderer.level;

import pize.app.Disposable;
import pize.files.Resource;
import pize.graphics.gl.Primitive;
import pize.graphics.gl.Type;
import pize.graphics.util.Shader;
import pize.graphics.vertex.Mesh;
import pize.graphics.vertex.VertexAttr;
import pize.math.vecmath.matrix.Matrix4f;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.client.control.camera.GameCamera;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.SIZE;

public class ChunkBorderRenderer implements Disposable{
    
    private final LevelRenderer levelRenderer;
    private final Shader shader;
    private final Mesh mesh;
    private final Matrix4f translationMatrix;
    private boolean show;
    
    public ChunkBorderRenderer(LevelRenderer levelRenderer){
        this.levelRenderer = levelRenderer;
        
        this.shader = new Shader(new Resource("shader/line.vert"), new Resource("shader/line.frag"));
        
        this.mesh = new Mesh(new VertexAttr(3, Type.FLOAT));
        this.mesh.setRenderMode(Primitive.LINES);
        
        this.mesh.setVertices(new float[]{
            16, 256, 16, //0
            0 , 256, 16, //1
            16, 0  , 16, //2
            0 , 0  , 16, //3
            16, 256, 0 , //4
            0 , 256, 0 , //5
            16, 0  , 0 , //6
            0 , 0  , 0 , //7
        });
        this.mesh.setIndices(new int[]{
            7, 6, 6, 2, 2, 3, 3, 7, //Top
            0, 4, 4, 5, 5, 1, 1, 0, //Bottom
            0, 2, 2, 6, 6, 4, 4, 0, //Left
            7, 3, 3, 1, 1, 5, 5, 7, //Right
            3, 2, 2, 0, 0, 1, 1, 3, //Front
            4, 6, 6, 7, 7, 5, 5, 4, //Back
        });
        
        this.translationMatrix = new Matrix4f();
    }
    
    
    public boolean isShow(){
        return show;
    }
    
    public void show(boolean show){
        this.show = show;
    }
    
    public void toggleShow(){
        show(!show);
    }
    
    
    public void render(GameCamera camera){
        if(!show)
            return;
        
        shader.bind();
        shader.setUniform("u_projection", camera.getProjection());
        shader.setUniform("u_view", camera.getView());
        
        final Vec3f position = new Vec3f(camera.chunkX() * SIZE, 0, camera.chunkZ() * SIZE).sub(camera.getX(), 0, camera.getZ());
        translationMatrix.toTranslated(position);
        shader.setUniform("u_model", translationMatrix);
        
        mesh.render();
    }
    
    @Override
    public void dispose(){
        shader.dispose();
        mesh.dispose();
    }
    
}
