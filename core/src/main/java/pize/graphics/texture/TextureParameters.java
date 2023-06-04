package pize.graphics.texture;

import pize.graphics.gl.*;
import pize.graphics.gl.Format;
import pize.graphics.gl.SizedFormat;
import pize.graphics.util.color.Color;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL46.*;

public class TextureParameters{
    
    public static Filter DEFAULT_MIN_FILTER = Filter.NEAREST;
    public static Filter DEFAULT_MAG_FILTER = Filter.NEAREST;
    public static Wrap DEFAULT_WRAP_S = Wrap.CLAMP_TO_EDGE;
    public static Wrap DEFAULT_WRAP_T = Wrap.CLAMP_TO_EDGE;
    public static Wrap DEFAULT_WRAP_R = Wrap.CLAMP_TO_EDGE;
    public static SizedFormat DEFAULT_FORMAT = SizedFormat.RGBA8;
    public static Type DEFAULT_TYPE = Type.UNSIGNED_BYTE;
    public static int DEFAULT_MIPMAP_LEVELS = 4;
    public static float DEFAULT_LOD_BIAS = -glGetFloat(GL_MAX_TEXTURE_LOD_BIAS);
    public static float DEFAULT_ANISOTROPY_LEVELS = 0;
    

    private Filter minFilter, magFilter;
    private Wrap wrapS, wrapT, wrapR;
    
    private SizedFormat format;
    private Type type;
    private final Color borderColor;
    
    private int mipmapLevels;
    private float lodBias, anisotropyLevels;

    
    public TextureParameters(){
        minFilter = DEFAULT_MIN_FILTER;
        magFilter = DEFAULT_MAG_FILTER;
        
        wrapS = DEFAULT_WRAP_S;
        wrapT = DEFAULT_WRAP_T;
        wrapR = DEFAULT_WRAP_R;
        
        format = DEFAULT_FORMAT;
        type = DEFAULT_TYPE;
        borderColor = new Color(0, 0, 0, 0F);
    
        mipmapLevels = DEFAULT_MIPMAP_LEVELS;
        lodBias = DEFAULT_LOD_BIAS;
        anisotropyLevels = DEFAULT_ANISOTROPY_LEVELS;
    }
    
    
    public void use(int TARGET){
        glTexParameteri(TARGET, GL_TEXTURE_MIN_FILTER, minFilter.GL);
        glTexParameteri(TARGET, GL_TEXTURE_MAG_FILTER, magFilter.GL);
        glTexParameteri(TARGET, GL_TEXTURE_WRAP_S, wrapS.GL);
        glTexParameteri(TARGET, GL_TEXTURE_WRAP_T, wrapT.GL);
        glTexParameteri(TARGET, GL_TEXTURE_WRAP_R, wrapR.GL);
        glTexParameterfv(TARGET, GL_TEXTURE_BORDER_COLOR, borderColor.toArray());
        glTexParameteri(TARGET, GL_TEXTURE_MAX_LEVEL, mipmapLevels);
        glTexParameterf(TARGET, GL_TEXTURE_LOD_BIAS, Math.min(lodBias, glGetInteger(GL_MAX_TEXTURE_LOD_BIAS)));
        glTexParameterf(TARGET, GL_TEXTURE_MAX_ANISOTROPY, Math.min(anisotropyLevels, glGetFloat(GL_MAX_TEXTURE_MAX_ANISOTROPY)));
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
