package pize.graphics.postprocess;

import pize.Pize;
import pize.context.Resizable;
import pize.graphics.gl.*;
import pize.graphics.texture.Texture;
import pize.graphics.texture.TextureParameters;

import static org.lwjgl.opengl.GL33.*;

public class RenderBufferObject extends GlObject implements Resizable{

    private int width, height;
    private Attachment attachment;
    private final Texture texture;


    public RenderBufferObject(int width, int height){
        super(glGenRenderbuffers());
        
        this.width = width;
        this.height = height;
        attachment = Attachment.DEPTH_ATTACHMENT;

        texture = new Texture(width, height);
        texture.getParameters()
            .setSizedFormat(SizedFormat.DEPTH_COMPONENT32)
            .setType(Type.FLOAT)
            .setWrap(Wrap.CLAMP_TO_EDGE)
            .setFilter(Filter.NEAREST)
            .setMipmapLevels(0);
    }

    public RenderBufferObject(){
        this(Pize.getWidth(), Pize.getHeight());
    }


    public void setAttachment(Attachment attachment){
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