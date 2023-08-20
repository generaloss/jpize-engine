package pize.graphics.util;

import pize.app.Disposable;
import pize.files.Resource;
import pize.graphics.camera.Camera;
import pize.lib.gl.Gl;
import pize.lib.gl.type.GlType;
import pize.graphics.mesh.IndexedMesh;
import pize.lib.gl.vertex.GlVertexAttr;
import pize.graphics.texture.CubeMap;
import pize.math.vecmath.matrix.Matrix4f;

public class SkyBox implements Disposable{

    private final CubeMap cubeMap;
    private final Shader shader;
    private final IndexedMesh mesh;
    
    public SkyBox(String px, String nx, String py, String ny, String pz, String nz){
        cubeMap = new CubeMap(px, nx, py, ny, pz, nz);

        shader = new Shader(new Resource("shader/skybox/skybox.vert"), new Resource("shader/skybox/skybox.frag"));

        mesh = new IndexedMesh(new GlVertexAttr(3, GlType.FLOAT));
        mesh.getBuffer().setData(new float[]{
            -1, -1,  1, //0
             1, -1,  1, //1
            -1,  1,  1, //2
             1,  1,  1, //3
            -1, -1, -1, //4
             1, -1, -1, //5
            -1,  1, -1, //6
             1,  1, -1  //7
        });
        mesh.getIndexBuffer().setData(new int[]{
            //Top
            7, 6, 2,
            2, 3, 7,
            //Bottom
            0, 4, 5,
            5, 1, 0,
            //Left
            0, 2, 6,
            6, 4, 0,
            //Right
            7, 3, 1,
            1, 5, 7,
            //Front
            3, 2, 0,
            0, 1, 3,
            //Back
            4, 6, 7,
            7, 5, 4
        });
    }
    
    public SkyBox(){
        this(
            "skybox/2/skybox_positive_x.png",
            "skybox/2/skybox_negative_x.png",
            "skybox/2/skybox_positive_y.png",
            "skybox/2/skybox_negative_y.png",
            "skybox/2/skybox_positive_z.png",
            "skybox/2/skybox_negative_z.png"
        );
    }
    

    public void render(Matrix4f projection, Matrix4f view){
        Gl.depthMask(false);
        
        shader.bind();
        shader.setUniform("u_projection", projection);
        shader.setUniform("u_view", view);
        shader.setUniform("u_cubeMap", cubeMap);

        mesh.render();

        Gl.depthMask(true);
    }
    
    public void render(Camera camera){
        final Matrix4f view = camera.getView().copy().cullPosition();
        render(camera.getProjection(), view);
    }
    
    public CubeMap getCubeMap(){
        return cubeMap;
    }

    @Override
    public void dispose(){
        cubeMap.dispose();
        shader.dispose();
        mesh.dispose();
    }

}
