package glit.graphics.texture;

import glit.graphics.gl.Filter;
import glit.graphics.gl.InternalFormat;
import glit.graphics.gl.Type;
import glit.graphics.gl.Wrap;
import glit.graphics.util.color.Color;

public class TextureParameters{

    protected Filter minFilter;
    protected Filter magFilter;
    protected Wrap wrapS;
    protected Wrap wrapT;
    protected final Color borderColor;

    protected InternalFormat internalFormat;
    protected Type type;
    protected int mipmapLevels;

    public TextureParameters(){
        minFilter = Filter.LINEAR_MIPMAP_LINEAR;
        magFilter = Filter.NEAREST;
        wrapS = Wrap.REPEAT;
        wrapT = Wrap.REPEAT;
        type = Type.UNSIGNED_BYTE;
        internalFormat = InternalFormat.RGBA8;
        mipmapLevels = 1;
        borderColor = new Color(0, 0, 0, 0);
    }


    public Wrap getWrapS(){
        return wrapS;
    }

    public Wrap getWrapT(){
        return wrapT;
    }

    public void setWrap(Wrap wrap){
        setWrap(wrap, wrap);
    }

    public void setWrap(Wrap s, Wrap t){
        wrapS = s;
        wrapT = t;
    }


    public Filter getMinFilter(){
        return minFilter;
    }

    public Filter getMagFilter(){
        return magFilter;
    }

    public void setFilter(Filter filter){
        setFilter(filter, filter);
    }

    public void setFilter(Filter min, Filter mag){
        minFilter = min;
        magFilter = mag;
    }


    public Color getBorderColor(){
        return borderColor;
    }


    public InternalFormat getInternalFormat(){
        return internalFormat;
    }

    public void setInternalFormat(InternalFormat internalFormat){
        this.internalFormat = internalFormat;
    }


    public Type getType(){
        return type;
    }

    public void setType(Type type){
        this.type = type;
    }


    public int getMipmapLevels(){
        return mipmapLevels;
    }

    public void setMipmapLevels(int mipmapLevels){
        this.mipmapLevels = mipmapLevels;
    }

}
