package pize.graphics.util;

import pize.util.Disposable;
import pize.util.Resizable;
import pize.gl.Gl;
import pize.graphics.postprocess.FrameBufferObject;
import pize.graphics.postprocess.RenderBufferObject;
import pize.graphics.texture.Texture;

public class Framebuffer3D implements Resizable, Disposable{
    
    private final FrameBufferObject fbo;
    private final RenderBufferObject rbo;
    
    public Framebuffer3D(){
        fbo = new FrameBufferObject();
        fbo.create();
        fbo.bind();
        
        rbo = new RenderBufferObject();
        rbo.create();
        
        fbo.unbind();
    }
    
    
    public void begin(){
        fbo.bind();
        rbo.bind();
        Gl.clearColorDepthBuffers();
    }
    
    public void end(){
        fbo.unbind();
        rbo.unbind();
    }
    
    public Texture getFrameTexture(){
        return fbo.getTexture();
    }
    
    public Texture getDepthMap(){
        return rbo.getTexture();
    }
    
    
    @Override
    public void resize(int width, int height){
        fbo.resize(width, height);
        rbo.resize(width, height);
    }
    
    @Override
    public void dispose(){
        fbo.dispose();
        rbo.dispose();
    }
    
}
