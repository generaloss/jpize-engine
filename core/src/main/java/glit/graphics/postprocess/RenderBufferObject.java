package glit.graphics.postprocess;

import glit.Glit;
import glit.context.Disposable;
import glit.context.Resizable;
import glit.graphics.gl.Filter;
import glit.graphics.gl.InternalFormat;
import glit.graphics.gl.Type;
import glit.graphics.gl.Wrap;
import glit.graphics.texture.Texture;
import glit.graphics.texture.TextureParameters;

import static org.lwjgl.opengl.GL33.*;

public class RenderBufferObject implements Disposable, Resizable{

    private int renderBuffer, width, height, attachment;
    private final Texture texture;


    public RenderBufferObject(int width, int height){
        this.width = width;
        this.height = height;
        attachment = GL_DEPTH_ATTACHMENT;

        texture = new Texture(glGenTextures(), width, height);
        texture.getParameters().setInternalFormat(InternalFormat.DEPTH_COMPONENT32);
        texture.getParameters().setType(Type.FLOAT);
        texture.getParameters().setWrap(Wrap.CLAMP_TO_EDGE);
        texture.getParameters().setFilter(Filter.NEAREST);
        texture.getParameters().setMipmapLevels(0);
    }

    public RenderBufferObject(){
        this(Glit.getWidth(), Glit.getHeight());
    }


    public void setAttachment(int attachment){
        this.attachment = attachment;
    }

    public TextureParameters getInfo(){
        return texture.getParameters();
    }

    public void create(){
        texture.update();

        renderBuffer = glGenRenderbuffers();
        bind();
        glFramebufferTexture2D(GL_FRAMEBUFFER, attachment, GL_TEXTURE_2D, texture.getId(), 0);
        glRenderbufferStorage(GL_RENDERBUFFER, texture.getParameters().getInternalFormat().gl, width, height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, attachment, GL_RENDERBUFFER, renderBuffer);
        unbind();
    }

    @Override
    public void resize(int width, int height){
        this.width = width;
        this.height = height;
        texture.resize(width, height);

        bind();
        glRenderbufferStorage(GL_RENDERBUFFER, texture.getInternalFormat().gl, width, height);
        unbind();
    }

    public void bind(){
        glBindRenderbuffer(GL_RENDERBUFFER, renderBuffer);
    }

    public static void unbind(){
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
    }

    public Texture getTexture(){
        return texture;
    }

    public int getId(){
        return renderBuffer;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }


    @Override
    public void dispose(){
        glDeleteRenderbuffers(renderBuffer);

        texture.dispose();
    }

}
