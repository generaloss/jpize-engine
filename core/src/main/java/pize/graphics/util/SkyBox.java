package pize.graphics.util;

import pize.context.Disposable;
import pize.files.Resource;
import pize.graphics.camera.Camera;
import pize.graphics.gl.Gl;
import pize.graphics.gl.Type;
import pize.graphics.texture.CubeMap;
import pize.graphics.vertex.Mesh;
import pize.graphics.vertex.VertexAttr;
import pize.math.vecmath.matrix.Matrix4f;

public class SkyBox implements Disposable{

    private final CubeMap cubeMap;
    private final Shader shader;
    private final Mesh mesh;

    public SkyBox(String px, String nx, String py, String ny, String pz, String nz){
        cubeMap = new CubeMap(px, nx, py, ny, pz, nz);

        shader = new Shader(new Resource("shader/skybox/skybox.vert"), new Resource("shader/skybox/skybox.frag"));

        mesh = new Mesh(new VertexAttr(3, Type.FLOAT));
        mesh.setVertices(new float[]{
            -1, -1,  1, //0
             1, -1,  1, //1
            -1,  1,  1, //2
             1,  1,  1, //3
            -1, -1, -1, //4
             1, -1, -1, //5
            -1,  1, -1, //6
             1,  1, -1  //7
        });
        mesh.setIndices(new int[]{
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
        render(camera.getProjection(), camera.getView());
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
