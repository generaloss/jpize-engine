package pize.devtests;

import pize.Jize;
import pize.gl.Gl;
import pize.gl.glenum.GlTarget;
import pize.gl.tesselation.GlPrimitive;
import pize.gl.type.GlType;
import pize.gl.vertex.GlVertexAttr;
import pize.glfw.key.Key;
import pize.graphics.camera.PerspectiveCamera;
import pize.graphics.camera.controller.Rotation3DController;
import pize.graphics.mesh.IndexedMesh;
import pize.graphics.util.BaseShader;
import pize.graphics.util.SkyBox;
import pize.io.context.ContextAdapter;
import pize.math.Maths;
import pize.math.vecmath.vector.Vec3f;
import pize.physic.Ray3f;

public class TriangleIntersectionTest extends ContextAdapter{
    
    SkyBox skyBox;
    
    BaseShader shader;
    PerspectiveCamera camera;
    Rotation3DController rotationController;
    Ray3f ray;
    
    IndexedMesh rayMesh;
    IndexedMesh mesh;
    
    public void init(){
        // Camera
        camera = new PerspectiveCamera(0.5F, 500, 70);
        rotationController = new Rotation3DController();
        ray = new Ray3f();
        // Skybox
        skyBox = new SkyBox();
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
            0, 1
        });
        
        // Disable cull face
        Gl.disable(GlTarget.CULL_FACE);
    }
    
    public void render(){
        // Clear color
        Gl.clearColorDepthBuffers();
        Gl.clearColor(0.2, 0.2, 0.22);
        
        // Render skybox
        skyBox.render(camera);
        
        // Render mesh & ray
        shader.bind();
        shader.setMatrices(camera);
        shader.setColor(1, 0, 0);
        mesh.render(3);
        rayMesh.render(2);
    }

    public void update(){
        // Exit & Fullscreen
        if(Key.ESCAPE.isDown())
            Jize.exit();
        if(Key.F11.isDown())
            Jize.window().toggleFullscreen();

        // Camera control
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
        if(Key.SPACE.isPressed())
            cameraMotion.y++;
        if(Key.LEFT_SHIFT.isPressed())
            cameraMotion.y--;

        // Camera update
        camera.getPosition().add(cameraMotion.mul(Jize.getDt() * 2));
        rotationController.update();
        camera.getRotation().set(rotationController.getRotation());
        camera.update();

        ray.set(camera.getPosition(), camera.getRotation().getDirection(), 100);
        if(ray.intersect(
                new Vec3f(1, 3, 0),
                new Vec3f(0, 0, 0),
                new Vec3f(3, 1, 0)
        ) != -1)
            System.out.println(1);

        final Vec3f position = camera.getPosition();
        final Vec3f v2 = position.copy().add(camera.getRotation().getDirection().mul(100));
        rayMesh.getBuffer().setData(new float[]{
                0, 0, 0, // position.x, position.y, position.z,
                v2.x, v2.y, v2.z
        });
    }
    
}
