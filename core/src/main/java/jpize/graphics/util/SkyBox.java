package jpize.graphics.util;

import jpize.gl.Gl;
import jpize.gl.type.GlType;
import jpize.gl.vertex.GlVertAttr;
import jpize.graphics.camera.Camera;
import jpize.graphics.mesh.IndexedMesh;
import jpize.graphics.texture.CubeMap;
import jpize.util.math.vecmath.matrix.Matrix4f;
import jpize.app.Disposable;
import jpize.util.file.Resource;

public class SkyBox implements Disposable{

    private final CubeMap cubeMap;
    private final Shader shader;
    private final IndexedMesh mesh;
    
    public SkyBox(String positive_x, String negative_x,
                  String positive_y, String negative_y,
                  String positive_z, String negative_z){

        cubeMap = new CubeMap(positive_x, negative_x, positive_y, negative_y, positive_z, negative_z);

        shader = new Shader(Resource.internal("shader/skybox/skybox.vert"), Resource.internal("shader/skybox/skybox.frag"));

        mesh = new IndexedMesh(new GlVertAttr(3, GlType.FLOAT));
        mesh.getBuffer().setData(new float[]{
            -2, -2,  2, //0
             2, -2,  2, //1
            -2,  2,  2, //2
             2,  2,  2, //3
            -2, -2, -2, //4
             2, -2, -2, //5
            -2,  2, -2, //6
             2,  2, -2  //7
        });
        mesh.getIndexBuffer().setData(new int[]{
            7, 6, 2,  2, 3, 7, //Top
            0, 4, 5,  5, 1, 0, //Bottom
            0, 2, 6,  6, 4, 0, //Left
            7, 3, 1,  1, 5, 7, //Right
            3, 2, 0,  0, 1, 3, //Front
            4, 6, 7,  7, 5, 4, //Back

            //2, 6, 7,  7, 3, 2, //Top
            //5, 4, 0,  0, 1, 5, //Bottom
            //6, 2, 0,  0, 4, 6, //Left
            //1, 3, 7,  7, 5, 1, //Right
            //0, 2, 3,  3, 1, 0, //Front
            //7, 6, 4,  4, 5, 7, //Back
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
        shader.uniform("u_projection", projection);
        shader.uniform("u_view", view);
        shader.uniform("u_cubeMap", cubeMap);

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
