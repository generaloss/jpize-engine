package pize.graphics.postprocess.effects;

import pize.Pize;
import pize.app.Disposable;
import pize.files.Resource;
import pize.graphics.gl.*;
import pize.graphics.postprocess.FrameBufferObject;
import pize.graphics.texture.Texture;
import pize.graphics.util.Shader;
import pize.math.vecmath.matrix.Matrix4f;
import pize.math.vecmath.vector.Vec3f;

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

        fbo.setAttachment(Attachment.DEPTH_ATTACHMENT);
        fbo.setWrite(false);
        fbo.setRead(false);

        fbo.getInfo()
            .setSizedFormat(SizedFormat.DEPTH_COMPONENT32)
            .setWrap(Wrap.CLAMP_TO_BORDER)
            .setFilter(Filter.NEAREST)
            .setType(Type.FLOAT)
            .getBorderColor().set(1F, 1F, 1F, 1F);

        fbo.create();

        // Shader
        shader = new Shader(new Resource("shader/shadowMapping/shadow.vert"), new Resource("shader/shadowMapping/shadow.frag"));

        projectionMatrix = new Matrix4f().toOrthographic(-size.x / 2, size.x / 2, size.y / 2, -size.y / 2, 1, size.z + 1);
        spaceMatrix = new Matrix4f();
        lookAtMatrix = new Matrix4f();
    }


    public void begin(){
        Gl.viewport(width, height);
        fbo.bind();
        glClear(GL_DEPTH_BUFFER_BIT);
        
        shader.bind();
        spaceMatrix.set(projectionMatrix.getMul(lookAtMatrix.toLookAt(pos, dir)));
        shader.setUniform("u_space", spaceMatrix);
    }

    public void end(){
        fbo.unbind();
        Gl.viewport(Pize.getWidth(), Pize.getHeight());
    }

    public Texture getShadowMap(){
        return fbo.getTexture();
    }

    public Matrix4f getLightSpace(){
        return spaceMatrix;
    }

    public void setModelMatrix(Matrix4f model){
        shader.setUniform("u_model", model);
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
