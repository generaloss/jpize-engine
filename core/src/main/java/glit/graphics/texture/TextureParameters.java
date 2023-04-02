package glit.graphics.texture;

import glit.graphics.gl.*;
import glit.graphics.gl.Format;
import glit.graphics.gl.SizedFormat;
import glit.graphics.util.color.Color;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL46.*;

public class TextureParameters{

    private Filter minFilter, magFilter;
    private Wrap wrapS, wrapT, wrapR;
    
    private SizedFormat format;
    private Type type;
    private final Color borderColor;
    
    private int mipmapLevels;
    private float anisotropyLevels, lodBias;

    
    public TextureParameters(){
        minFilter = Filter.NEAREST_MIPMAP_LINEAR;
        magFilter = Filter.NEAREST;
        
        wrapS = Wrap.CLAMP_TO_EDGE;
        wrapT = Wrap.CLAMP_TO_EDGE;
        wrapR = Wrap.CLAMP_TO_EDGE;
        
        format = SizedFormat.RGBA8;
        type = Type.UNSIGNED_BYTE;
        borderColor = new Color(0, 0, 0, 0F);
    
        mipmapLevels = 4;
        anisotropyLevels = 0;
        lodBias = -glGetFloat(GL_MAX_TEXTURE_LOD_BIAS);
    }
    
    
    public void use(int TARGET){
        glTexParameteri(TARGET, GL_TEXTURE_MIN_FILTER, minFilter.GL);
        glTexParameteri(TARGET, GL_TEXTURE_MAG_FILTER, magFilter.GL);
        glTexParameteri(TARGET, GL_TEXTURE_WRAP_S, wrapS.GL);
        glTexParameteri(TARGET, GL_TEXTURE_WRAP_T, wrapT.GL);
        glTexParameteri(TARGET, GL_TEXTURE_WRAP_R, wrapR.GL);
        glTexParameterfv(TARGET, GL_TEXTURE_BORDER_COLOR, borderColor.toArray());
        glTexParameteri(TARGET, GL_TEXTURE_MAX_LEVEL, mipmapLevels);
        glTexParameterf(TARGET, GL_TEXTURE_MAX_ANISOTROPY, Math.min(anisotropyLevels, glGetFloat(GL_MAX_TEXTURE_MAX_ANISOTROPY)));
        glTexParameterf(TARGET, GL_TEXTURE_LOD_BIAS, Math.min(lodBias, glGetInteger(GL_MAX_TEXTURE_LOD_BIAS)));
    }
    
    
    public void texImage2D(int target, ByteBuffer buffer, int width, int height){
        texImage2D(target, buffer, width, height, 0);
    }
    
    public void texImage2D(int target, ByteBuffer buffer, int width, int height, int level){
        glTexImage2D(
            target, level, format.GL, width, height,
            0, format.getBase().GL, type.GL, buffer
        );
    }
    
    public void texSubImage3D(int target, ByteBuffer buffer, int width, int height, int z){
        glTexSubImage3D(
            target, 0, 0, 0, z, width, height, 1,
            format.getBase().GL, type.GL, buffer
        );
    }


    public Wrap getWrapS(){
        return wrapS;
    }

    public Wrap getWrapT(){
        return wrapT;
    }
    
    public Wrap getWrapR(){
        return wrapR;
    }

    public TextureParameters setWrap(Wrap wrap){
        setWrap(wrap, wrap, wrap);
        
        return this;
    }
    
    public TextureParameters setWrap(Wrap s, Wrap t){
        wrapS = s;
        wrapT = t;
    
        return this;
    }
    
    public TextureParameters setWrap(Wrap s, Wrap t, Wrap r){
        wrapS = s;
        wrapT = t;
        wrapR = r;
    
        return this;
    }


    public Filter getMinFilter(){
        return minFilter;
    }

    public Filter getMagFilter(){
        return magFilter;
    }

    public TextureParameters setFilter(Filter filter){
        setFilter(filter, filter);
        
        return this;
    }

    public TextureParameters setFilter(Filter min, Filter mag){
        minFilter = min;
        magFilter = mag;
    
        return this;
    }
    
    
    public Color getBorderColor(){
        return borderColor;
    }
    
    
    public Format getFormat(){
        return format.getBase();
    }
    
    
    public SizedFormat getSizedFormat(){
        return format;
    }
    
    public TextureParameters setSizedFormat(SizedFormat sizedFormat){
        this.format = sizedFormat;
    
        return this;
    }
    

    public Type getType(){
        return type;
    }

    public TextureParameters setType(Type type){
        this.type = type;
    
        return this;
    }


    public int getMipmapLevels(){
        return mipmapLevels;
    }

    public TextureParameters setMipmapLevels(int mipmapLevels){
        this.mipmapLevels = mipmapLevels;
    
        return this;
    }
    
    
    public float getAnisotropyLevels(){
        return anisotropyLevels;
    }
    
    public TextureParameters setAnisotropyLevels(float anisotropyLevels){
        this.anisotropyLevels = anisotropyLevels;
        
        return this;
    }
    
    
    public float getLodBias(){
        return lodBias;
    }
    
    public TextureParameters setLodBias(float lodBias){
        this.lodBias = lodBias;
        
        return this;
    }

}
