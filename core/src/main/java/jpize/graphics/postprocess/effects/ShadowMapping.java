package jpize.graphics.postprocess.effects;

import jpize.Jpize;
import jpize.app.Disposable;
import jpize.util.file.Resource;
import jpize.gl.Gl;
import jpize.gl.buffer.GlAttachment;
import jpize.gl.texture.GlFilter;
import jpize.gl.texture.GlSizedFormat;
import jpize.gl.texture.GlWrap;
import jpize.gl.type.GlType;
import jpize.graphics.postprocess.FrameBufferObject;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.Shader;
import jpize.util.math.vecmath.matrix.Matrix4f;
import jpize.util.math.vecmath.vector.Vec3f;

import static org.lwjgl.opengl.GL33.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL33.glClear;

public class ShadowMapping implements Disposable{

    private final int width, height;
    private final Vec3f pos;
    private final Vec3f dir;

    private final FrameBufferObject fbo;
    private final Shader shader;
    private final Matrix4f projectionMatrix, spaceMatrix, lookAtMatrix;


    public ShadowMapping(int width, int height, Vec3f size, Vec3f pos, Vec3f dir){
        this.pos = pos;
        this.dir = dir.nor();

        this.width = width;
        this.height = height;

        // Framebuffer

        fbo = new FrameBufferObject(width, height);

        fbo.setAttachment(GlAttachment.DEPTH_ATTACHMENT);
        fbo.setWrite(false);
        fbo.setRead(false);

        fbo.getInfo()
            .setSizedFormat(GlSizedFormat.DEPTH_COMPONENT32)
            .setWrap(GlWrap.CLAMP_TO_BORDER)
            .setFilter(GlFilter.NEAREST)
            .setType(GlType.FLOAT)
            .getBorderColor().set(1);

        fbo.create();

        // Shader
        shader = new Shader(Resource.internal("shader/shadowMapping/shadow.vert"), Resource.internal("shader/shadowMapping/shadow.frag"));

        projectionMatrix = new Matrix4f().setOrthographic(-size.x / 2, size.x / 2, size.y / 2, -size.y / 2, 1, size.z + 1);
        spaceMatrix = new Matrix4f();
        lookAtMatrix = new Matrix4f();
    }


    public void begin(){
        Gl.viewport(width, height);
        fbo.bind();
        glClear(GL_DEPTH_BUFFER_BIT);
        
        shader.bind();
        spaceMatrix.set(projectionMatrix.getMul(lookAtMatrix.setLookAt(pos, dir)));
        shader.uniform("u_space", spaceMatrix);
    }

    public void end(){
        fbo.unbind();
        Gl.viewport(Jpize.getWidth(), Jpize.getHeight());
    }

    public Texture getShadowMap(){
        return fbo.getTexture();
    }

    public Matrix4f getLightSpace(){
        return spaceMatrix;
    }

    public void setModelMatrix(Matrix4f model){
        shader.uniform("u_model", model);
    }

    public Vec3f pos(){
        return pos;
    }

    public Vec3f dir(){
        return dir;
    }

    @Override
    public void dispose(){
        shader.dispose();
        fbo.dispose();
    }

}
