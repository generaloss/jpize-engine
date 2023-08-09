package pize.tests.minecraftosp.client.renderer.particle;

import pize.app.Disposable;
import pize.files.Resource;
import pize.graphics.camera.PerspectiveCamera;
import pize.graphics.gl.BufUsage;
import pize.graphics.gl.Type;
import pize.graphics.mesh.QuadMesh;
import pize.graphics.mesh.VertexAttr;
import pize.graphics.texture.Region;
import pize.graphics.texture.Texture;
import pize.graphics.util.Shader;
import pize.graphics.util.color.Color;
import pize.math.vecmath.matrix.Matrix4f;
import pize.math.vecmath.vector.Vec3f;

import java.util.concurrent.CopyOnWriteArrayList;

import static pize.graphics.mesh.QuadIndexBuffer.QUAD_INDICES;
import static pize.graphics.mesh.QuadIndexBuffer.QUAD_VERTICES;

public class ParticleBatch implements Disposable{
    
    final CopyOnWriteArrayList<ParticleInstance> instances;
    final Shader shader;
    final Matrix4f rotateMatrix;
    final QuadMesh mesh;
    final int size;
    Texture lastTexture;
    final Color currentColor;
    final float[] vertices;
    int vertexIndex, particleIndex;
    
    public ParticleBatch(int size){
        this.size = size;
        this.instances = new CopyOnWriteArrayList<>();
        this.shader = new Shader(new Resource("shader/level/particle/particle-batch.vert"), new Resource("shader/level/particle/particle-batch.frag"));
        // Matrices
        this.rotateMatrix = new Matrix4f();
        // Buffer
        this.mesh = new QuadMesh(
                size,
                new VertexAttr(3, Type.FLOAT), // pos3
                new VertexAttr(4, Type.FLOAT), // col4
                new VertexAttr(2, Type.FLOAT)  // uv2
        );
        // Vertices array
        this.vertices = new float[QUAD_VERTICES * size * mesh.getBuffer().getVertexSize()];
        // Color
        this.currentColor = new Color();
    }
    
    
    public void spawnParticle(Particle particle, Vec3f position){
        final ParticleInstance instance = particle.createInstance(position);
        instances.add(instance);
    }
    
    
    public void render(PerspectiveCamera camera){
        setup(camera); // Setup shader
        
        for(ParticleInstance instance: instances){
            instance.update();
            
            if(instance.getElapsedTimeSeconds() > instance.lifeTimeSeconds){
                instances.remove(instance);
                continue;
            }
            
            renderInstance(instance, camera);
        }
        
        flush(); // Render
    }
    
    private void renderInstance(ParticleInstance instance, PerspectiveCamera camera){
        if(particleIndex >= size)
            flush();
        
        // Texture
        final Texture texture = instance.getParticle().texture;
        if(texture != lastTexture){
            flush();
            lastTexture = texture;
        }
        final Region region = instance.region;
        
        // Color
        currentColor.set(instance.color);
        currentColor.setA(currentColor.a() * instance.getAlpha());
        
        // Matrix
        rotateMatrix.toRotatedZ(instance.rotation).mul(new Matrix4f().toLookAt(camera.getRotation().getDirection()));
        
        // Setup vertices
        final Vec3f v0 = new Vec3f(-0.5,  0.5, 0) .mul(rotateMatrix) .mul(instance.size) .add(instance.position);
        final Vec3f v1 = new Vec3f(-0.5, -0.5, 0) .mul(rotateMatrix) .mul(instance.size) .add(instance.position);
        final Vec3f v2 = new Vec3f( 0.5, -0.5, 0) .mul(rotateMatrix) .mul(instance.size) .add(instance.position);
        final Vec3f v3 = new Vec3f( 0.5,  0.5, 0) .mul(rotateMatrix) .mul(instance.size) .add(instance.position);
        
        // Add vertices
        addVertex(v0.x, v0.y, v0.z, region.u1(), region.v1());
        addVertex(v1.x, v1.y, v1.z, region.u1(), region.v2());
        addVertex(v2.x, v2.y, v2.z, region.u2(), region.v2());
        addVertex(v3.x, v3.y, v3.z, region.u2(), region.v1());
        
        particleIndex++;
    }
    
    private void addVertex(float x, float y, float z, float u, float v){
        final int pointer = vertexIndex * mesh.getBuffer().getVertexSize();
        
        // pos3
        vertices[pointer    ] = x;
        vertices[pointer + 1] = y;
        vertices[pointer + 2] = z;
        // col4
        vertices[pointer + 3] = currentColor.r();
        vertices[pointer + 4] = currentColor.g();
        vertices[pointer + 5] = currentColor.b();
        vertices[pointer + 6] = currentColor.a();
        // uv2
        vertices[pointer + 7] = u;
        vertices[pointer + 8] = v;
        
        vertexIndex++;
    }
    
    private void setup(PerspectiveCamera camera){
        shader.bind();
        shader.setUniform("u_projection", camera.getProjection());
        shader.setUniform("u_view", camera.getImaginaryView());
    }
    
    private void flush(){
        if(lastTexture == null)
            return;
        
        shader.setUniform("u_texture", lastTexture);
        mesh.getBuffer().setData(vertices, BufUsage.STREAM_DRAW);
        mesh.render(particleIndex * QUAD_INDICES);
        
        vertexIndex = 0;
        particleIndex = 0;
    }
    
    @Override
    public void dispose(){
        shader.dispose();
        mesh.dispose();
    }
    
}