package pize.devtests;

import pize.Pize;
import pize.app.AppAdapter;
import pize.graphics.camera.PerspectiveCamera;
import pize.graphics.camera.controller.Rotation3DController;
import pize.graphics.gl.Gl;
import pize.graphics.gl.Primitive;
import pize.graphics.gl.Target;
import pize.graphics.gl.Type;
import pize.graphics.util.BaseShader;
import pize.graphics.util.SkyBox;
import pize.graphics.mesh.Mesh;
import pize.graphics.mesh.VertexAttr;
import pize.io.glfw.Key;
import pize.math.Maths;
import pize.math.vecmath.vector.Vec3f;
import pize.physic.Ray3f;

public class TriangleIntersectionTest extends AppAdapter{
    
    SkyBox skyBox;
    
    BaseShader shader;
    PerspectiveCamera camera;
    Rotation3DController rotationController;
    Ray3f ray;
    
    Mesh rayMesh;
    Mesh mesh;
    
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
        mesh = new Mesh(new VertexAttr(3, Type.FLOAT));
        mesh.setVertices(new float[]{
            1, 3, 0, // 0
            0, 0, 0, // 1
            3, 1, 0, // 2
        });
        mesh.setIndices(new int[]{
            0, 1, 2
        });
        
        // Ray Mesh
        rayMesh = new Mesh(new VertexAttr(3, Type.FLOAT));
        rayMesh.setRenderMode(Primitive.LINES);
        rayMesh.setIndices(new int[]{
            0, 1
        });
        
        // Disable cull face
        Gl.disable(Target.CULL_FACE);
    }
    
    public void update(){
        // Exit & Fullscreen
        if(Key.ESCAPE.isDown())
            Pize.exit();
        if(Key.F11.isDown())
            Pize.window().toggleFullscreen();
        
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
        camera.getPosition().add(cameraMotion.mul(Pize.getDt() * 2));
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
        rayMesh.setVertices(new float[]{
            0, 0, 0, // position.x, position.y, position.z,
            v2.x, v2.y, v2.z
        });
    }
    
    public void render(){
        // Clear color
        Gl.clearColorBuffer();
        Gl.clearColor(0.2, 0.2, 0.22);
        
        // Render skybox
        skyBox.render(camera);
        
        // Render mesh & ray
        shader.bind();
        shader.setMatrices(camera);
        shader.setColor(1, 0, 0);
        mesh.render();
        rayMesh.render();
    }
    
}
