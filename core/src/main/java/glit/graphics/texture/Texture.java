package glit.graphics.texture;

import glit.context.Disposable;
import glit.context.Resizable;
import glit.files.FileHandle;
import glit.graphics.gl.Format;
import glit.graphics.gl.InternalFormat;
import glit.graphics.gl.Type;

import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL46.GL_TEXTURE_MAX_ANISOTROPY;

public class Texture implements Cloneable, Disposable, Resizable{

    private final int id;
    private final TextureParameters parameters;

    private int width;
    private int height;
    private Pixmap pixmap;


    public Texture(int id, int width, int height){
        this.id = id;
        parameters = new TextureParameters();
        resize(width, height);
    }

    public Texture(int id, Pixmap pixmap){
        this.pixmap = pixmap;
        this.id = id;
        parameters = new TextureParameters();
        resize(pixmap.getWidth(), pixmap.getHeight());
    }

    public Texture(int width, int height){
        this(glGenTextures(), width, height);
    }

    public Texture(String filepath){
        this(glGenTextures(), PixmapLoader.loadFrom(filepath));
    }

    public Texture(FileHandle file){
        this(glGenTextures(), PixmapLoader.loadFrom(file));
    }

    public Texture(Texture texture){
        this(glGenTextures(), texture.getPixmap().clone());
    }

    public Texture(Pixmap pixmap){
        this(glGenTextures(), pixmap);
    }


    public void resize(int width, int height){
        this.width = width;
        this.height = height;
        update();
    }

    public void update(){
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, parameters.minFilter.gl);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, parameters.magFilter.gl);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, parameters.wrapS.gl);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, parameters.wrapT.gl);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, parameters.mipmapLevels);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAX_ANISOTROPY, Math.min(16, glGetFloat(GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT)));
        glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, parameters.borderColor.toArray());

        glTexImage2D(
            GL_TEXTURE_2D, 0, parameters.internalFormat.gl, width, height,
            0, parameters.internalFormat.getBaseFormat().gl,
            parameters.type.gl, pixmap != null ? pixmap.getBuffer() : null
        );

        glGenerateMipmap(GL_TEXTURE_2D);
    }

    public void generateMipmapLevels(int levels){
        if(parameters.mipmapLevels == levels)
            return;

        parameters.mipmapLevels = levels;

        glBindTexture(GL_TEXTURE_2D, id);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, parameters.mipmapLevels);
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    public Texture setPixmap(Pixmap pixmap){
        if(this.pixmap != null)
            this.pixmap.set(pixmap);
        else
            this.pixmap = pixmap;

        width = pixmap.getWidth();
        height = pixmap.getHeight();

        glBindTexture(GL_TEXTURE_2D, id);

        glTexImage2D(
            GL_TEXTURE_2D, 0, Pixmap.FORMAT.gl, width, height,
            0, Pixmap.FORMAT.getBaseFormat().gl, Type.UNSIGNED_BYTE.gl, pixmap.getBuffer()
        );

        return this;
    }


    public void bind(int num){
        glActiveTexture(GL_TEXTURE0 + num);
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public static void unbind(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getId(){
        return id;
    }


    public Pixmap getPixmap(){
        return pixmap;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public float aspect(){
        return (float) width / height;
    }


    public TextureParameters getParameters(){
        return parameters;
    }

    public InternalFormat getInternalFormat(){
        return parameters.getInternalFormat();
    }

    public Format getFormat(){
        return getInternalFormat().getBaseFormat();
    }

    public Type getType(){
        return parameters.getType();
    }


    @Override
    public Texture clone(){
        return new Texture(this);
    }

    @Override
    public void dispose(){
        glDeleteTextures(id);
    }

}