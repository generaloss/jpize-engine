package jpize.graphics.util;

import jpize.app.Disposable;
import jpize.app.Resizable;
import jpize.gl.Gl;
import jpize.graphics.postprocess.FrameBufferObject;
import jpize.graphics.texture.Texture;

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
    
    public Texture getFrameTexture(){
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
