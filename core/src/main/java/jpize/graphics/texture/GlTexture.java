package jpize.graphics.texture;

import jpize.gl.texture.GlFormat;
import jpize.gl.GlObject;
import jpize.gl.texture.GlSizedFormat;
import jpize.gl.texture.GlTexTarget;
import jpize.gl.type.GlType;

import static org.lwjgl.opengl.GL33.*;

public abstract class GlTexture extends GlObject{
    
    protected final TextureParameters parameters;
    protected final GlTexTarget target;
    
    public GlTexture(GlTexTarget target, TextureParameters parameters){
        super(glGenTextures());
        this.target = target;
        
        this.parameters = parameters;
    }
    
    public GlTexture(GlTexTarget target){
        this(target, new TextureParameters());
    }
    
    
    public TextureParameters getParameters(){
        return parameters;
    }
    
    public GlType getType(){
        return parameters.getType();
    }
    
    public GlFormat getFormat(){
        return parameters.getFormat();
    }
    
    public GlSizedFormat getSizedFormat(){
        return parameters.getSizedFormat();
    }
    
    
    protected void genMipMap(){
        glGenerateMipmap(target.GL);
    }
    
    public void bind(){
        glBindTexture(target.GL, ID);
    }
    
    
    public void bind(int num){
        glActiveTexture(GL_TEXTURE0 + num);
        bind();
    }

    public static void unbindAll(){
        for(GlTexTarget target: GlTexTarget.values())
            glBindTexture(target.GL, 0);
    }

    @Override
    public void dispose(){
        glDeleteTextures(ID);
    }
    
}
