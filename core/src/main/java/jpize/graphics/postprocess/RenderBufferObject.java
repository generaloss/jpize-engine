package jpize.graphics.postprocess;

import jpize.Jpize;
import jpize.app.Resizable;
import jpize.gl.GlObject;
import jpize.gl.buffer.GlAttachment;
import jpize.gl.texture.GlFilter;
import jpize.gl.texture.GlSizedFormat;
import jpize.gl.texture.GlWrap;
import jpize.gl.type.GlType;
import jpize.graphics.texture.Texture;
import jpize.graphics.texture.TextureParameters;

import static org.lwjgl.opengl.GL33.*;

public class RenderBufferObject extends GlObject implements Resizable{

    private int width, height;
    private GlAttachment attachment;
    private final Texture texture;


    public RenderBufferObject(int width, int height){
        super(glGenRenderbuffers());
        
        this.width = width;
        this.height = height;
        attachment = GlAttachment.DEPTH_ATTACHMENT;

        texture = new Texture(width, height);
        texture.getParameters()
            .setSizedFormat(GlSizedFormat.DEPTH_COMPONENT32)
            .setType(GlType.FLOAT)
            .setWrap(GlWrap.CLAMP_TO_EDGE)
            .setFilter(GlFilter.NEAREST)
            .setMipmapLevels(0);
    }

    public RenderBufferObject(){
        this(Jpize.getWidth(), Jpize.getHeight());
    }


    public void setAttachment(GlAttachment attachment){
        this.attachment = attachment;
    }

    public TextureParameters getInfo(){
        return texture.getParameters();
    }

    public void create(){
        texture.update();
        
        bind();
        glFramebufferTexture2D(GL_FRAMEBUFFER, attachment.GL, GL_TEXTURE_2D, texture.getID(), 0);
        glRenderbufferStorage(GL_RENDERBUFFER, texture.getSizedFormat().GL, width, height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, attachment.GL, GL_RENDERBUFFER, ID);
        unbind();
    }

    @Override
    public void resize(int width, int height){
        this.width = width;
        this.height = height;
        texture.resize(width, height);

        bind();
        glRenderbufferStorage(GL_RENDERBUFFER, texture.getSizedFormat().GL, width, height);
        unbind();
    }
    

    public Texture getTexture(){
        return texture;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
    
    public void bind(){
        glBindRenderbuffer(GL_RENDERBUFFER, ID);
    }


    @Override
    public void dispose(){
        glDeleteRenderbuffers(ID);

        texture.dispose();
    }
    
    
    public void unbind(){
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
    }

}
