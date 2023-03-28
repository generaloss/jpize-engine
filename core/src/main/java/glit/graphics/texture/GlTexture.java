package glit.graphics.texture;

import glit.graphics.gl.Format;
import glit.graphics.gl.SizedFormat;
import glit.graphics.gl.Type;
import glit.graphics.gl.GlObject;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public abstract class GlTexture extends GlObject{
    
    protected final TextureParameters parameters;
    
    public GlTexture(int GL_TARGET){
        super(GL_TARGET);
        
        ID = glGenTextures();
        parameters = new TextureParameters();
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
    
    
    
    protected void texImage2D(int glTarget, ByteBuffer buffer, int width, int height){
        glTexImage2D(
            glTarget, 0, parameters.getSizedFormat().GL, width, height,
            0, parameters.getFormat().GL,
            parameters.getType().GL, buffer
        );
    }
    
    protected void texImage2D(ByteBuffer buffer, int width, int height){
        texImage2D(TARGET,buffer, width, height);
    }
    
    protected void texSubImage3D(int glTarget, ByteBuffer buffer, int width, int height, int z){
        glTexSubImage3D(glTarget, 0, 0, 0, z, width, height, 1, parameters.getFormat().GL, parameters.getType().GL, buffer);
    }
    
    protected void texSubImage3D(ByteBuffer buffer, int width, int height, int z){
        texSubImage3D(TARGET, buffer, width, height, z);
    }
    
    
    protected void genMipMap(){
        glGenerateMipmap(TARGET);
    }
    
    public void bind(){
        glBindTexture(TARGET, ID);
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
