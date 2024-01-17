package test;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.gl.glenum.GlTarget;
import jpize.gl.tesselation.GlPrimitive;
import jpize.gl.type.GlType;
import jpize.gl.vertex.GlVertAttr;
import jpize.graphics.camera.PerspectiveCamera;
import jpize.io.MotionCtrl;
import jpize.io.EulerRotCtrl;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.font.FontLoader;
import jpize.graphics.mesh.IndexedMesh;
import jpize.graphics.util.BaseShader;
import jpize.graphics.util.SkyBox;
import jpize.app.JpizeApplication;
import jpize.util.math.vecmath.matrix.Matrix4f;
import jpize.util.math.vecmath.vector.Vec3f;
import jpize.physic.Ray3f;
import jpize.physic.utils.Intersector;
import jpize.sdl.Sdl;
import jpize.sdl.input.Key;
import jpize.util.time.Sync;

public class TriangleIntersectionTest extends JpizeApplication{
    
    SkyBox skybox;
    
    BaseShader shader;
    PerspectiveCamera camera;
    EulerRotCtrl rotCtrl;
    MotionCtrl motionCtrl;
    Ray3f ray;
    
    IndexedMesh rayMesh;
    IndexedMesh mesh;

    Sync sync = new Sync(40);
    BitmapFont font = FontLoader.getDefault();
    
    public void init(){
        // Camera
        camera = new PerspectiveCamera(0.5F, 500, 70);
        rotCtrl = new EulerRotCtrl(camera.getRotation());
        // rotCtrl.setSpeed(0.25F);
        motionCtrl = new MotionCtrl();
        ray = new Ray3f();
        // Skybox
        skybox = new SkyBox();
        // Shader
        shader = BaseShader.getPos3UColor();
        // Mesh
        mesh = new IndexedMesh(new GlVertAttr(3, GlType.FLOAT));
        mesh.getBuffer().setData(new float[]{
            1, 3, 0, // 0
            0, 0, 0, // 1
            3, 1, 0, // 2
        });
        mesh.getIndexBuffer().setData(new int[]{
            0, 1, 2
        });
        
        // Ray Mesh
        rayMesh = new IndexedMesh(new GlVertAttr(3, GlType.FLOAT));
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
        // sync.sync();
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
        font.drawText("fps: " + Jpize.getFPS(), 10, 10);
    }

    public void update(){
        // Exit & Fullscreen
        if(Key.ESCAPE.isDown())
            Jpize.exit();
        if(Key.F11.isDown())
            Jpize.window().toggleFullscreenDesktop();
        if(Key.M.isDown())
            rotCtrl.toggleEnabled();

        if(Key.V.isDown())
            Sdl.enableVsync(!Sdl.isVsyncEnabled());

        // Camera control
        motionCtrl.update(camera.getRotation().yaw);
        final Vec3f cameraMotion = motionCtrl.getDirectedMotion();

        // Camera update
        camera.getPosition().add(cameraMotion.mul(Jpize.getDt() * 2));
        rotCtrl.update();
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
