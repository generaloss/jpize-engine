package glit.graphics.texture;

import glit.graphics.gl.*;
import glit.graphics.gl.Format;
import glit.graphics.gl.SizedFormat;
import glit.graphics.util.color.Color;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_MAX_LEVEL;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;

public class TextureParameters{

    private Filter minFilter, magFilter;
    private Wrap wrapS, wrapT, wrapR;
    
    private SizedFormat format;
    private Type type;
    
    private int mipmapLevels;
    private final Color borderColor;

    public TextureParameters(){
        minFilter = Filter.LINEAR_MIPMAP_LINEAR;
        magFilter = Filter.NEAREST;
        wrapS = Wrap.CLAMP_TO_EDGE;
        wrapT = Wrap.CLAMP_TO_EDGE;
        wrapR = Wrap.CLAMP_TO_EDGE;
        type = Type.UNSIGNED_BYTE;
        format = SizedFormat.RGBA8;
        mipmapLevels = 1;
        borderColor = new Color(0, 0, 0, 0);
    }
    
    
    public void use(int glTarget){
        glTexParameteri(glTarget, GL_TEXTURE_MIN_FILTER, minFilter.GL);
        glTexParameteri(glTarget, GL_TEXTURE_MAG_FILTER, magFilter.GL);
        glTexParameteri(glTarget, GL_TEXTURE_WRAP_S, wrapS.GL);
        glTexParameteri(glTarget, GL_TEXTURE_WRAP_T, wrapT.GL);
        glTexParameteri(glTarget, GL_TEXTURE_WRAP_R, wrapR.GL);
        glTexParameterfv(glTarget, GL_TEXTURE_BORDER_COLOR, borderColor.toArray());
        glTexParameteri(glTarget, GL_TEXTURE_MAX_LEVEL, mipmapLevels);
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

}
