package pize.graphics.util;

import pize.context.Disposable;
import pize.context.Resizable;
import pize.graphics.gl.Gl;
import pize.graphics.postprocess.FrameBufferObject;
import pize.graphics.texture.Texture;

public class Framebuffer2D implements Resizable, Disposable{ // Для самых ленивых
    
    private final FrameBufferObject fbo;
    
    public Framebuffer2D(){
        fbo = new FrameBufferObject();
        fbo.create();
    }
    
    
    public void begin(){
        fbo.bind();
        Gl.clearColorBuffer();
    }
    
    public void end(){
        fbo.unbind();
    }
    
    public Texture getTexture(){
        return fbo.getTexture();
    }
    
    
    @Override
    public void resize(int width, int height){
        fbo.resize(width, height);
    }
    
    @Override
    public void dispose(){
        fbo.dispose();
    }
    
}
