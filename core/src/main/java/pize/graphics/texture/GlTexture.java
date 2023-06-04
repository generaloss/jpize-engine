package pize.graphics.texture;

import pize.graphics.gl.Format;
import pize.graphics.gl.GlObject;
import pize.graphics.gl.SizedFormat;
import pize.graphics.gl.Type;

import static org.lwjgl.opengl.GL33.*;

public abstract class GlTexture extends GlObject{
    
    protected final TextureParameters parameters;
    protected final int TEXTURE_TYPE;
    
    public GlTexture(int TEXTURE_TYPE, TextureParameters parameters){
        super(glGenTextures());
        this.TEXTURE_TYPE = TEXTURE_TYPE;
        
        this.parameters = parameters;
    }
    
    public GlTexture(int TEXTURE_TYPE){
        this(TEXTURE_TYPE, new TextureParameters());
    }
    
    
    public TextureParameters getParameters(){
        return parameters;
    }
    
    public Type getType(){
        return parameters.getType();
    }
    
    public Format getFormat(){
        return parameters.getFormat();
    }
    
    public SizedFormat getSizedFormat(){
        return parameters.getSizedFormat();
    }
    
    
    protected void genMipMap(){
        glGenerateMipmap(TEXTURE_TYPE);
    }
    
    public void bind(){
        glBindTexture(TEXTURE_TYPE, ID);
    }
    
    
    public void bind(int num){
        glActiveTexture(GL_TEXTURE0 + num);
        bind();
    }
    
    @Override
    public void dispose(){
        glDeleteTextures(ID);
    }
    
}
