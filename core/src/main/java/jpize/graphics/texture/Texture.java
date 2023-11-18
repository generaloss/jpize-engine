package jpize.graphics.texture;

import jpize.graphics.texture.pixmap.PixmapIO;
import jpize.util.file.Resource;
import jpize.gl.texture.GlTexParam;
import jpize.gl.texture.GlTexTarget;
import jpize.graphics.texture.pixmap.Pixmap;
import jpize.graphics.texture.pixmap.PixmapRGBA;
import jpize.util.Resizable;

import static org.lwjgl.opengl.GL33.*;

public class Texture extends GlTexture implements Resizable{
    
    private int width;
    private int height;
    private Pixmap pixmap;
    
    
    public Texture(int width, int height){
        super(GlTexTarget.TEXTURE_2D);
        resize(width, height);
    }

    public Texture(Pixmap pixmap){
        super(GlTexTarget.TEXTURE_2D);
        setPixmap(pixmap);
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
        parameters.use(GlTexTarget.TEXTURE_2D);
        parameters.texImage2D(GlTexParam.TEXTURE_2D, pixmap != null ? pixmap.getBuffer() : null, width, height);
        genMipMap();
    }

    public void regenerateMipmapLevels(Pixmap... mipmaps){
        if(mipmaps.length == 0)
            return;
    
        bind();
        parameters.setMipmapLevels(mipmaps.length);
        glTexParameteri(target.GL, GL_TEXTURE_MAX_LEVEL, parameters.getMipmapLevels());
        
        for(int i = 0; i < parameters.getMipmapLevels(); i++){
            Pixmap pixmap = mipmaps[i];
            parameters.texImage2D(GlTexParam.TEXTURE_2D, pixmap.getBuffer(), pixmap.getWidth(), pixmap.getHeight(), i + 1);
        }
    }
    
    public void regenerateMipmapLevels(int levels){
        if(parameters.getMipmapLevels() == levels)
            return;
        
        bind();
        parameters.setMipmapLevels(levels);
        glTexParameteri(target.GL, GL_TEXTURE_MAX_LEVEL, parameters.getMipmapLevels());
        genMipMap();
    }
    
    protected void genMipMapManual(){
        if(pixmap instanceof PixmapRGBA pixmap4){

            PixmapRGBA pixmap = pixmap4.getMipmapped();
            for(int level = 1; level <= parameters.getMipmapLevels(); level++){
                parameters.texImage2D(GlTexParam.TEXTURE_2D, pixmap.getBuffer(), pixmap.getWidth(), pixmap.getHeight(), level);
                if(level != parameters.getMipmapLevels())
                    pixmap = pixmap.getMipmapped();
            }

        }
    }

    public Texture setPixmap(Pixmap pixmap){
        if(this.pixmap == null)
            this.pixmap = pixmap;
        else
            this.pixmap.set(pixmap);

        parameters.setSizedFormat(pixmap.getFormat());
        resize(pixmap.getWidth(), pixmap.getHeight());

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