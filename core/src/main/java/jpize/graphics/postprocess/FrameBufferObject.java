package jpize.graphics.postprocess;

import org.lwjgl.BufferUtils;
import jpize.Jpize;
import jpize.app.Resizable;
import jpize.gl.buffer.GlAttachment;
import jpize.gl.texture.GlFilter;
import jpize.gl.GlObject;
import jpize.gl.texture.GlWrap;
import jpize.graphics.texture.Texture;
import jpize.graphics.texture.TextureParameters;
import jpize.graphics.util.ScreenQuad;
import jpize.graphics.util.ScreenQuadShader;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL33.*;

public class FrameBufferObject extends GlObject implements Resizable{

    private int width, height;
    private GlAttachment attachment;
    private final Texture texture;
    private boolean draw, read;


    public FrameBufferObject(int width, int height){
        super(glGenFramebuffers());
        
        this.width = width;
        this.height = height;
        attachment = GlAttachment.COLOR_ATTACHMENT0;
        draw = true;
        read = true;
        
        texture = new Texture(width, height);
        texture.getParameters()
            .setWrap(GlWrap.CLAMP_TO_EDGE)
            .setFilter(GlFilter.NEAREST)
            .setMipmapLevels(0);
    }

    public FrameBufferObject(){
        this(Jpize.getWidth(), Jpize.getHeight());
    }


    public void setAttachment(GlAttachment attachment){
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

        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * texture.getFormat().getChannels());
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
