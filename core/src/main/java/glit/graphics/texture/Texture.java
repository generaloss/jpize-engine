package glit.graphics.texture;

import glit.context.Resizable;
import glit.files.FileHandle;

import static org.lwjgl.opengl.GL33.*;

public class Texture extends GlTexture implements Resizable{
    
    private int width;
    private int height;
    private Pixmap pixmap;
    
    
    public Texture(int width, int height){
        super(GL_TEXTURE_2D);
        
        resize(width, height);
    }

    public Texture(Pixmap pixmap){
        super(GL_TEXTURE_2D);
        
        this.pixmap = pixmap;
        resize(pixmap.getWidth(), pixmap.getHeight());
    }

    public Texture(String filepath){
        this(PixmapLoader.loadFrom(filepath));
    }

    public Texture(FileHandle file){
        this(PixmapLoader.loadFrom(file));
    }

    public Texture(Texture texture){
        this(texture.getPixmap().clone());
    }


    public void resize(int width, int height){
        this.width = width;
        this.height = height;
        update();
    }

    public void update(){
        bind();
        texImage2D(pixmap != null ? pixmap.getBuffer() : null, width, height);
        parameters.use(GL_TEXTURE_2D);
        genMipMap();
    }

    public void regenerateMipmapLevels(int levels){
        if(parameters.getMipmapLevels() == levels)
            return;

        parameters.setMipmapLevels(levels);

        glBindTexture(GL_TEXTURE_2D, ID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, parameters.getMipmapLevels());
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    public Texture setPixmap(Pixmap pixmap){
        if(this.pixmap == null)
            return this;
        
        this.pixmap.set(pixmap);
        this.width = pixmap.getWidth();
        this.height = pixmap.getHeight();
        
        update();
        return this;
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
    
    
    public static void unbind(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }

}