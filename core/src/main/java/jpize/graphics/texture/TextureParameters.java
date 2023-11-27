package jpize.graphics.texture;

import jpize.gl.texture.*;
import jpize.gl.type.GlType;
import jpize.graphics.util.color.Color;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL46.*;

public class TextureParameters{
    
    public static GlFilter DEFAULT_MIN_FILTER = GlFilter.LINEAR_MIPMAP_LINEAR;
    public static GlFilter DEFAULT_MAG_FILTER = GlFilter.NEAREST;
    public static GlWrap DEFAULT_WRAP_S = GlWrap.CLAMP_TO_EDGE;
    public static GlWrap DEFAULT_WRAP_T = GlWrap.CLAMP_TO_EDGE;
    public static GlWrap DEFAULT_WRAP_R = GlWrap.CLAMP_TO_EDGE;
    public static GlSizedFormat DEFAULT_FORMAT = GlSizedFormat.RGBA8;
    public static GlType DEFAULT_TYPE = GlType.UNSIGNED_BYTE;
    public static int DEFAULT_MIPMAP_LEVELS = 0;
    public static float DEFAULT_LOD_BIAS = -getMaxLodBias();
    public static float DEFAULT_ANISOTROPY_LEVELS = 0;
    

    private GlFilter minFilter, magFilter;
    private GlWrap wrapS, wrapT, wrapR;
    
    private GlSizedFormat format;
    private GlType type;
    private final Color borderColor;
    
    private int mipmapLevels;
    private float lodBias, anisotropyLevels;

    
    public TextureParameters(){
        this.minFilter = DEFAULT_MIN_FILTER;
        this.magFilter = DEFAULT_MAG_FILTER;
        
        this.wrapS = DEFAULT_WRAP_S;
        this.wrapT = DEFAULT_WRAP_T;
        this.wrapR = DEFAULT_WRAP_R;
        
        this.format = DEFAULT_FORMAT;
        this.type = DEFAULT_TYPE;
        this.borderColor = new Color(0, 0, 0, 0);
    
        this.mipmapLevels = DEFAULT_MIPMAP_LEVELS;
        this.lodBias = DEFAULT_LOD_BIAS;
        this.anisotropyLevels = DEFAULT_ANISOTROPY_LEVELS;
    }
    
    
    public void use(GlTexTarget target){
        glTexParameteri (target.GL, GL_TEXTURE_MIN_FILTER, minFilter.GL);
        glTexParameteri (target.GL, GL_TEXTURE_MAG_FILTER, magFilter.GL);
        glTexParameteri (target.GL, GL_TEXTURE_WRAP_S, wrapS.GL);
        glTexParameteri (target.GL, GL_TEXTURE_WRAP_T, wrapT.GL);
        glTexParameteri (target.GL, GL_TEXTURE_WRAP_R, wrapR.GL);
        glTexParameterfv(target.GL, GL_TEXTURE_BORDER_COLOR, borderColor.toArray());
        glTexParameteri (target.GL, GL_TEXTURE_MAX_LEVEL, mipmapLevels);
        glTexParameterf (target.GL, GL_TEXTURE_LOD_BIAS, Math.min(lodBias, glGetInteger(GL_MAX_TEXTURE_LOD_BIAS)));
        glTexParameterf (target.GL, GL_TEXTURE_MAX_ANISOTROPY, Math.min(anisotropyLevels, glGetFloat(GL_MAX_TEXTURE_MAX_ANISOTROPY)));
    }


    public void texSubImage3D(GlTexParam target, ByteBuffer buffer, int width, int height, int z){
        glTexSubImage3D(target.GL, 0, 0, 0, z, width, height, 1, format.getBase().GL, type.GL, buffer);
    }

    public void texImage2D(GlTexParam target, ByteBuffer buffer, int width, int height, int level){
        glTexImage2D(target.GL, level, format.GL, width, height, 0, format.getBase().GL, type.GL, buffer);
    }

    public void texImage2D(GlTexParam target, ByteBuffer buffer, int width, int height){
        texImage2D(target, buffer, width, height, 0);
    }


    public GlWrap getWrapS(){
        return wrapS;
    }

    public GlWrap getWrapT(){
        return wrapT;
    }
    
    public GlWrap getWrapR(){
        return wrapR;
    }

    public TextureParameters setWrap(GlWrap wrap){
        setWrap(wrap, wrap, wrap);
        return this;
    }
    
    public TextureParameters setWrap(GlWrap s, GlWrap t){
        wrapS = s;
        wrapT = t;
        return this;
    }
    
    public TextureParameters setWrap(GlWrap s, GlWrap t, GlWrap r){
        wrapS = s;
        wrapT = t;
        wrapR = r;
        return this;
    }


    public GlFilter getMinFilter(){
        return minFilter;
    }

    public GlFilter getMagFilter(){
        return magFilter;
    }

    public TextureParameters setFilter(GlFilter filter){
        setFilter(filter, filter);
        return this;
    }

    public TextureParameters setFilter(GlFilter min, GlFilter mag){
        minFilter = min;
        magFilter = mag;
        return this;
    }
    
    
    public Color getBorderColor(){
        return borderColor;
    }
    
    
    public GlFormat getFormat(){
        return format.getBase();
    }
    
    
    public GlSizedFormat getSizedFormat(){
        return format;
    }
    
    public TextureParameters setSizedFormat(GlSizedFormat sizedFormat){
        this.format = sizedFormat;
        return this;
    }
    

    public GlType getType(){
        return type;
    }

    public TextureParameters setType(GlType type){
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


    public static float getMaxLodBias(){
        return glGetFloat(GL_MAX_TEXTURE_LOD_BIAS);
    }

}
