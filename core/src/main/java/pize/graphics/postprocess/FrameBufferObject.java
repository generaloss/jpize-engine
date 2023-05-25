package pize.graphics.postprocess;

import pize.Pize;
import pize.activity.Resizable;
import pize.graphics.gl.Attachment;
import pize.graphics.gl.Filter;
import pize.graphics.gl.GlObject;
import pize.graphics.gl.Wrap;
import pize.graphics.texture.Texture;
import pize.graphics.texture.TextureParameters;
import pize.graphics.util.ScreenQuad;
import pize.graphics.util.ScreenQuadShader;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL33.*;

public class FrameBufferObject extends GlObject implements Resizable{

    private int width, height;
    private Attachment attachment;
    private final Texture texture;
    private boolean draw, read;


    public FrameBufferObject(int width, int height){
        super(glGenFramebuffers());
        
        this.width = width;
        this.height = height;
        attachment = Attachment.COLOR_ATTACHMENT0;
        draw = true;
        read = true;
        
        texture = new Texture(width, height);
        texture.getParameters()
            .setWrap(Wrap.CLAMP_TO_EDGE)
            .setFilter(Filter.NEAREST)
            .setMipmapLevels(0);
    }

    public FrameBufferObject(){
        this(Pize.getWidth(), Pize.getHeight());
    }


    public void setAttachment(Attachment attachment){
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
        
        bind();
        glFramebufferTexture2D(GL_FRAMEBUFFER, attachment.GL, GL_TEXTURE_2D, texture.getID(), 0);
        glDrawBuffer(draw ? attachment.GL : GL_NONE);
        glReadBuffer(read ? attachment.GL : GL_NONE);
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
        glBindFramebuffer(GL_FRAMEBUFFER, ID);
    }
    
    
    @Override
    public void dispose(){
        glDeleteFramebuffers(ID);
        
        texture.dispose();
    }
    
    
    public void unbind(){
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

}
