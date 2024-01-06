package test;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.gl.glenum.GlTarget;
import jpize.gl.tesselation.GlPrimitive;
import jpize.gl.type.GlType;
import jpize.gl.vertex.GlVertexAttr;
import jpize.graphics.camera.PerspectiveCamera;
import jpize.graphics.camera.controller.Motion3DController;
import jpize.graphics.camera.controller.Rotation3DController;
import jpize.graphics.mesh.IndexedMesh;
import jpize.graphics.util.BaseShader;
import jpize.graphics.util.SkyBox;
import jpize.io.context.JpizeApplication;
import jpize.math.vecmath.matrix.Matrix4f;
import jpize.math.vecmath.vector.Vec3f;
import jpize.physic.Ray3f;
import jpize.physic.utils.Intersector;
import jpize.sdl.input.Key;

public class TriangleIntersectionTest extends JpizeApplication{
    
    SkyBox skybox;
    
    BaseShader shader;
    PerspectiveCamera camera;
    Rotation3DController rotationController;
    Motion3DController motionController;
    Ray3f ray;
    
    IndexedMesh rayMesh;
    IndexedMesh mesh;
    
    public void init(){
        // Camera
        camera = new PerspectiveCamera(0.5F, 500, 70);
        rotationController = new Rotation3DController();
        motionController = new Motion3DController();
        ray = new Ray3f();
        // Skybox
        skybox = new SkyBox();
        // Shader
        shader = BaseShader.getPos3UColor();
        // Mesh
        mesh = new IndexedMesh(new GlVertexAttr(3, GlType.FLOAT));
        mesh.getBuffer().setData(new float[]{
            1, 3, 0, // 0
            0, 0, 0, // 1
            3, 1, 0, // 2
        });
        mesh.getIndexBuffer().setData(new int[]{
            0, 1, 2
        });
        
        // Ray Mesh
        rayMesh = new IndexedMesh(new GlVertexAttr(3, GlType.FLOAT));
        rayMesh.setMode(GlPrimitive.LINES);
        rayMesh.getIndexBuffer().setData(new int[]{
            0, 1,
            2, 3,
            4, 5,
        });
        
        // Disable cull face
        Gl.disable(GlTarget.CULL_FACE);
    }
    
    public void render(){
        // Clear color
        Gl.clearColorDepthBuffers();

        // Render skybox
        skybox.render(camera);
        
        // Render mesh & ray
        shader.bind();
        shader.setMatrices(camera);
        shader.setColor(1, 0, 0);
        mesh.render();
        shader.setColor(0, 0, 1);
        rayMesh.render();

        //Utils.delayElapsed(100);
    }

    public void update(){
        // Exit & Fullscreen
        if(Key.ESCAPE.isDown())
            Jpize.exit();
        if(Key.F11.isDown())
            Jpize.window().toggleFullscreenDesktop();
        if(Key.M.isDown())
            rotationController.toggleEnabled();

        // Camera control
        motionController.update(camera.getRotation().yaw);
        final Vec3f cameraMotion = motionController.getDirectedMotion();

        // Camera update
        camera.getPosition().add(cameraMotion.mul(Jpize.getDt() * 2));
        rotationController.update();
        camera.getRotation().set(rotationController.getRotation());
        camera.update();

        ray.set(camera.getPosition(), camera.getRotation().getDir(), 100);
        final float intersection = Intersector.getRayIntersectionMesh(ray, new Matrix4f(),
            new float[]{
                1, 3, 0,
                0, 0, 0,
                3, 1, 0
            },
            new int[]{ 0, 1, 2 }
        );
        if(intersection != -1)
            System.out.println(intersection);

        final Vec3f pos = camera.getPosition();
        final Vec3f v2 = pos.copy().add(camera.getRotation().getDir().mul(100));
        rayMesh.getBuffer().setData(new float[]{
            0, 0, 0, // pos.x, pos.y, pos.z,
            v2.x, v2.y, v2.z,

            pos.x, 0, pos.z,
            pos.x + 10, 0, pos.z,

            pos.x, 0, pos.z,
            pos.x, 0, pos.z + 1000000000,
        });
    }

    public void resize(int width, int height){
        camera.resize(width, height);
    }
    
}