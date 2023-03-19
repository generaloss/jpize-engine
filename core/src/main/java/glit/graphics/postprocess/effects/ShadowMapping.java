package glit.graphics.postprocess.effects;

import glit.Glit;
import glit.context.Disposable;
import glit.files.FileHandle;
import glit.graphics.gl.Filter;
import glit.graphics.gl.InternalFormat;
import glit.graphics.gl.Type;
import glit.graphics.gl.Wrap;
import glit.graphics.postprocess.FrameBufferObject;
import glit.graphics.texture.Texture;
import glit.graphics.gl.Gl;
import glit.graphics.util.Shader;
import glit.math.vecmath.matrix.Matrix4f;
import glit.math.vecmath.vector.Vec3f;

import static org.lwjgl.opengl.GL33.*;

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

        fbo.setAttachment(GL_DEPTH_ATTACHMENT);
        fbo.setWrite(false);
        fbo.setRead(false);

        fbo.getInfo().setInternalFormat(InternalFormat.DEPTH_COMPONENT32);
        fbo.getInfo().setWrap(Wrap.CLAMP_TO_BORDER);
        fbo.getInfo().setFilter(Filter.NEAREST);
        fbo.getInfo().setType(Type.FLOAT);
        fbo.getInfo().getBorderColor().set(1F, 1F, 1F, 1F);

        fbo.create();

        // Shader
        shader = new Shader(new FileHandle("shader/shadowMapping/shadow.vert"), new FileHandle("shader/shadowMapping/shadow.frag"));

        projectionMatrix = new Matrix4f().toOrthographic(-size.x / 2, size.x / 2, size.y / 2, -size.y / 2, 1, size.z + 1);
        spaceMatrix = new Matrix4f();
        lookAtMatrix = new Matrix4f();
    }


    public void begin(){
        Gl.setViewport(width, height);
        fbo.bind();

        glClear(GL_DEPTH_BUFFER_BIT);
        shader.bind();

        spaceMatrix.set(projectionMatrix.getMul(lookAtMatrix.toLookAt(pos, dir)));
        shader.setUniform("u_space", spaceMatrix);
    }

    public void end(){
        FrameBufferObject.unbind();
        Gl.setViewport(Glit.getWidth(), Glit.getHeight());
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
