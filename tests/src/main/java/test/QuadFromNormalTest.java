package test;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.gl.glenum.GlTarget;
import jpize.gl.type.GlType;
import jpize.gl.vertex.GlVertAttr;
import jpize.graphics.camera.PerspectiveCamera;
import jpize.io.EulerRotCtrl;
import jpize.graphics.mesh.IndexedMesh;
import jpize.graphics.util.BaseShader;
import jpize.graphics.util.SkyBox;
import jpize.app.JpizeApplication;
import jpize.util.math.Maths;
import jpize.util.math.vector.Vec3f;
import jpize.util.math.vector.Vec3i;
import jpize.sdl.input.Key;

public class QuadFromNormalTest extends JpizeApplication{

    SkyBox skyBox;

    BaseShader shader;
    PerspectiveCamera camera;
    EulerRotCtrl rotCtrl;

    IndexedMesh quadMesh;

    public void init(){
        // Camera
        camera = new PerspectiveCamera(0.5F, 500, 70);
        camera.setImaginaryOrigins(true, true, true);
        camera.getPosition().y += 3;
        rotCtrl = new EulerRotCtrl(camera.getRotation());
        // Skybox
        skyBox = new SkyBox();
        // Shader
        shader = BaseShader.getPos3UColor();
        // Mesh

        quadMesh = new IndexedMesh(new GlVertAttr(3, GlType.FLOAT));
        generateFromBlockFace(new Vec3i(0, 1, 0));
        quadMesh.getIndexBuffer().setData(new int[]{
            0, 1, 2,
            2, 3, 0
        });

        // Disable cull face
        Gl.disable(GlTarget.CULL_FACE);
    }

    public void update(){
        // Exit & Fullscreen
        if(Key.ESCAPE.isDown())
            Jpize.closeWindow();
        if(Key.M.isDown())
            rotCtrl.toggleEnabled();
        if(Key.F11.isDown())
            Jpize.window().toggleFullscreenDesktop();

        // Camera control
        final Vec3f cameraMotion = getCameraMotion();
        if(Key.SPACE.isPressed())
            cameraMotion.y++;
        if(Key.LSHIFT.isPressed())
            cameraMotion.y--;

        // Camera update
        camera.getPosition().add(cameraMotion.mul(Jpize.getDt() * 2));
        rotCtrl.update();
        camera.update();
    }


    public void render(){
        // Clear buffer
        Gl.clearColorBuffer();

        // Render skybox
        skyBox.render(camera);

        // Render mesh & ray
        shader.bind();
        shader.setMatrices(camera);
        shader.setColor(1, 0, 0);
        quadMesh.render();
    }


    private Vec3f getCameraMotion(){
        final Vec3f cameraMotion = new Vec3f();
        final float yawSin = Maths.sinDeg(camera.getRotation().yaw);
        final float yawCos = Maths.cosDeg(camera.getRotation().yaw);
        if(Key.W.isPressed())
            cameraMotion.add(yawCos, 0, yawSin);
        if(Key.S.isPressed())
            cameraMotion.add(-yawCos, 0, -yawSin);
        if(Key.A.isPressed())
            cameraMotion.add(-yawSin, 0, yawCos);
        if(Key.D.isPressed())
            cameraMotion.add(yawSin, 0, -yawCos);
        return cameraMotion;
    }


    public void generateFromBlockFace(Vec3i normal){
        // Calculate positions (N - normal, p(1-4) - vertex position)

        //   p1 ------- p4
        //   |           |
        //   |     N     |
        //   |           |
        //   p2 ------- p3

        // Calc
        final Vec3f p4 = new Vec3f(1).sub(normal);  // calc p4
        final Vec3f p2 = p4.copy().mul(-1);         // inv p4
        final Vec3f p1 = new Vec3f(normal).crs(p4); // crs n*p4
        final Vec3f p3 = new Vec3f(normal).crs(p2); // crs n*p2

        System.out.println(new Vec3f(1, 0, 1).crs(new Vec3f(0, 1, 0)));
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);

        // Scale, shift
        normal.mul(0.5).add(0.5);

        p1.mul(0.5).add(normal);
        p2.mul(0.5).add(normal);
        p3.mul(0.5).add(normal);
        p4.mul(0.5).add(normal);

        // Set vertices
        quadMesh.getBuffer().setData(new float[]{
            p1.x, p1.y, p1.z, // 0
            p2.x, p2.y, p2.z, // 1
            p3.x, p3.y, p3.z, // 2
            p4.x, p4.y, p4.z, // 3
        });
    }

}
