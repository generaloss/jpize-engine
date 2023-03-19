package glit.graphics.util;

import glit.context.Disposable;
import glit.files.FileHandle;
import glit.graphics.camera.Camera;
import glit.graphics.gl.Gl;
import glit.graphics.gl.Type;
import glit.graphics.texture.CubeMap;
import glit.graphics.vertex.Mesh;
import glit.graphics.vertex.VertexAttr;

public class SkyBox implements Disposable{

    private final CubeMap cubeMap;
    private final Shader shader;
    public final Mesh mesh;

    public SkyBox(String px, String nx, String py, String ny, String pz, String nz){
        cubeMap = new CubeMap(px, nx, py, ny, pz, nz);

        shader = new Shader(new FileHandle("shader/skybox/skybox.vert"), new FileHandle("shader/skybox/skybox.frag"));

        mesh = new Mesh(new VertexAttr(3, Type.FLOAT));
        mesh.setVertices(new float[]{
             1,-1, 1,  1, 1, 1, -1, 1, 1,
            -1, 1, 1, -1,-1, 1,  1,-1, 1,

             1, 1,-1,  1, 1, 1,  1,-1, 1,
             1,-1, 1,  1,-1,-1,  1, 1,-1,

            -1, 1, 1, -1, 1,-1, -1,-1,-1,
            -1,-1,-1, -1,-1, 1, -1, 1, 1,

             1, 1,-1,  1,-1,-1, -1,-1,-1,
            -1,-1,-1, -1, 1,-1,  1, 1,-1,

             1,-1, 1, -1,-1, 1, -1,-1,-1,
            -1,-1,-1,  1,-1,-1,  1,-1, 1,

             1, 1, 1,  1, 1,-1, -1, 1, 1,
            -1, 1, 1,  1, 1,-1, -1, 1,-1,
        });
    }

    public void render(Camera camera){
        Gl.depthMask(false);

        shader.bind();
        shader.setUniform("u_projection", camera.getProjection());
        shader.setUniform("u_view", camera.getView());
        shader.setUniform("u_cubeMap", cubeMap);

        mesh.render();

        Gl.depthMask(true);
    }

    @Override
    public void dispose(){
        cubeMap.dispose();
        shader.dispose();
        mesh.dispose();
    }

}
