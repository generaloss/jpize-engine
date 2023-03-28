package glit.graphics.postprocess;

import glit.Glit;
import glit.context.Resizable;
import glit.graphics.gl.Filter;
import glit.graphics.gl.GlObject;
import glit.graphics.gl.Wrap;
import glit.graphics.texture.Texture;
import glit.graphics.texture.TextureParameters;
import glit.graphics.util.ScreenQuad;
import glit.graphics.util.ScreenQuadShader;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL33.*;

public class FrameBufferObject extends GlObject implements Resizable{

    private int width, height, attachment;
    private final Texture texture;
    private boolean draw, read;


    public FrameBufferObject(int width, int height){
        super(GL_FRAMEBUFFER);
        
        this.width = width;
        this.height = height;
        attachment = GL_COLOR_ATTACHMENT0;
        draw = true;
        read = true;
        
        texture = new Texture(width, height);
        texture.getParameters()
            .setWrap(Wrap.CLAMP_TO_EDGE)
            .setFilter(Filter.NEAREST)
            .setMipmapLevels(0);
    }

    public FrameBufferObject(){
        this(Glit.getWidth(), Glit.getHeight());
    }


    public void setAttachment(int attachment){
        this.attachment = attachment;
    }

    public void setRead(boolean read){
        this.read = read;
    }

    public void setWrite(boolean draw){
        this.draw = draw;
    }

    public TextureParameters getInfo(){
        return texture.getParameters();
    }


    public void create(){
        texture.update();
    
        ID = glGenFramebuffers();
        bind();
        glFramebufferTexture2D(TARGET, attachment, GL_TEXTURE_2D, texture.getID(), 0);
        glDrawBuffer(draw ? attachment : GL_NONE);
        glReadBuffer(read ? attachment : GL_NONE);
        unbind();
    }

    @Override
    public void resize(int width, int height){
        this.width = width;
        this.height = height;
        texture.resize(width, height);
    }


    public void copyTo(Texture texture){
        bind();
        texture.bind();
        glCopyTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, 0, 0, width, height);
        unbind();
    }

    public ByteBuffer getBuffer(Texture texture){
        bind();
        int width = texture.getWidth();
        int height = texture.getHeight();

        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * texture.getFormat().getChannels());
        glReadPixels(0, 0, width, height, texture.getFormat().GL, texture.getType().GL, buffer);

        unbind();
        return buffer;
    }
    

    public void renderToScreen(){
        unbind();

        ScreenQuadShader.use(texture);
        ScreenQuad.render();
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
        glBindFramebuffer(TARGET, ID);
    }
    
    
    @Override
    public void dispose(){
        glDeleteFramebuffers(ID);
        
        texture.dispose();
    }
    
    
    public static void unbind(){
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

}
