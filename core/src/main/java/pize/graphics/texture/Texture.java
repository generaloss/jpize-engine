package pize.graphics.texture;

import pize.app.Resizable;
import pize.files.Resource;

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
        this(PixmapIO.load(filepath));
    }

    public Texture(Resource res){
        this(PixmapIO.load(res));
    }

    public Texture(Texture texture){
        this(texture.getPixmap().copy());
    }


    public void resize(int width, int height){
        this.width = width;
        this.height = height;
        update();
    }

    public void update(){
        bind();
        parameters.use(GL_TEXTURE_2D);
        parameters.texImage2D(GL_TEXTURE_2D, pixmap != null ? pixmap.getBuffer() : null, width, height);
        genMipMap();
    }

    public void regenerateMipmapLevels(Pixmap... mipmaps){
        if(mipmaps.length == 0)
            return;
    
        bind();
        parameters.setMipmapLevels(mipmaps.length);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, parameters.getMipmapLevels());
        
        for(int i = 0; i < parameters.getMipmapLevels(); i++){
            Pixmap pixmap = mipmaps[i];
            parameters.texImage2D(GL_TEXTURE_2D, pixmap.getBuffer(), pixmap.width, pixmap.height, i + 1);
        }
    }
    
    public void regenerateMipmapLevels(int levels){
        if(parameters.getMipmapLevels() == levels)
            return;
        
        bind();
        parameters.setMipmapLevels(levels);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, parameters.getMipmapLevels());
        genMipMap();
    }
    
    protected void genMipMapManual(){
        Pixmap pixmap = this.pixmap.getMipmapped();
        for(int level = 1; level <= parameters.getMipmapLevels(); level++){
            parameters.texImage2D(GL_TEXTURE_2D, pixmap.getBuffer(), pixmap.width, pixmap.height, level);
            if(level != parameters.getMipmapLevels())
                pixmap = pixmap.getMipmapped();
        }
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