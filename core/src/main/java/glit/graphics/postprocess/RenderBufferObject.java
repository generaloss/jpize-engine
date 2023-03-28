package glit.graphics.postprocess;

import glit.Glit;
import glit.context.Resizable;
import glit.graphics.gl.*;
import glit.graphics.texture.Texture;
import glit.graphics.texture.TextureParameters;

import static org.lwjgl.opengl.GL33.*;

public class RenderBufferObject extends GlObject implements Resizable{

    private int width, height, attachment;
    private final Texture texture;


    public RenderBufferObject(int width, int height){
        super(GL_RENDERBUFFER);
        
        this.width = width;
        this.height = height;
        attachment = GL_DEPTH_ATTACHMENT;

        texture = new Texture(width, height);
        texture.getParameters()
            .setSizedFormat(SizedFormat.DEPTH_COMPONENT32)
            .setType(Type.FLOAT)
            .setWrap(Wrap.CLAMP_TO_EDGE)
            .setFilter(Filter.NEAREST)
            .setMipmapLevels(0);
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

        ID = glGenRenderbuffers();
        bind();
        glFramebufferTexture2D(GL_FRAMEBUFFER, attachment, GL_TEXTURE_2D, texture.getID(), 0);
        glRenderbufferStorage(TARGET, texture.getSizedFormat().GL, width, height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, attachment, TARGET, ID);
        unbind();
    }

    @Override
    public void resize(int width, int height){
        this.width = width;
        this.height = height;
        texture.resize(width, height);

        bind();
        glRenderbufferStorage(TARGET, texture.getSizedFormat().GL, width, height);
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
        glBindRenderbuffer(TARGET, ID);
    }


    @Override
    public void dispose(){
        glDeleteRenderbuffers(ID);

        texture.dispose();
    }
    
    
    public static void unbind(){
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
    }

}
