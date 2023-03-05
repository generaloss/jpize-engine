package glit.graphics.postprocess;

import glit.Glit;
import glit.context.Disposable;
import glit.context.Resizable;
import glit.graphics.gl.Filter;
import glit.graphics.gl.Wrap;
import glit.graphics.texture.Texture;
import glit.graphics.texture.TextureParameters;
import glit.graphics.util.ScreenQuad;
import glit.graphics.util.ScreenQuadShader;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL33.*;

public class FrameBufferObject implements Disposable, Resizable{

    private int frameBuffer, width, height, attachment;
    private final Texture texture;
    private boolean draw, read;


    public FrameBufferObject(int width, int height){
        this.width = width;
        this.height = height;
        attachment = GL_COLOR_ATTACHMENT0;
        draw = true;
        read = true;

        texture = new Texture(glGenTextures(), width, height);
        texture.getParameters().setWrap(Wrap.CLAMP_TO_EDGE);
        texture.getParameters().setFilter(Filter.NEAREST);
        texture.getParameters().setMipmapLevels(0);
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

        frameBuffer = glGenFramebuffers();
        bind();
        glFramebufferTexture2D(GL_FRAMEBUFFER, attachment, GL_TEXTURE_2D, texture.getId(), 0);
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
        glBindTexture(GL_TEXTURE_2D, texture.getId());
        glCopyTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, 0, 0, width, height);
        unbind();
    }

    public ByteBuffer getBuffer(Texture texture){
        bind();
        int width = texture.getWidth();
        int height = texture.getHeight();

        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * texture.getFormat().getChannels());
        glReadPixels(0, 0, width, height, texture.getFormat().gl, texture.getType().gl, buffer);

        unbind();
        return buffer;
    }


    public void renderToScreen(){
        unbind();

        ScreenQuadShader.use(texture);
        ScreenQuad.render();
    }


    public void bind(){
        glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer);
    }

    public static void unbind(){
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public Texture getTexture(){
        return texture;
    }

    public int getId(){
        return frameBuffer;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }


    @Override
    public void dispose(){
        glDeleteFramebuffers(frameBuffer);

        texture.dispose();
    }

}
