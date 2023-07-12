package pize.graphics.texture;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL33.GL_TEXTURE_2D_ARRAY;
import static org.lwjgl.opengl.GL33.glBindTexture;

public class TextureArray extends GlTexture{
    
    private final List<Pixmap> pixmapList;

    public TextureArray(Pixmap... textureData){
        super(GL_TEXTURE_2D_ARRAY);
        bind();
        
        pixmapList = new ArrayList<>();
        for(int z = 0; z < textureData.length; z++){
            Pixmap td = textureData[z];
            pixmapList.add(td);
    
            parameters.texSubImage3D(GL_TEXTURE_2D_ARRAY, td.getBuffer(), td.getWidth(), td.getHeight(), z);
            parameters.use(TEXTURE_TYPE);
            genMipMap();
        }
        unbind();
    }

    public TextureArray(BufferedImage... bufferedImage){
        super(GL_TEXTURE_2D_ARRAY);
        bind();
        
        pixmapList = new ArrayList<>();
        for(int z = 0; z < bufferedImage.length; z++){
            Pixmap td = PixmapIO.load(bufferedImage[z]);
            pixmapList.add(td);
    
            parameters.texSubImage3D(GL_TEXTURE_2D_ARRAY, td.getBuffer(), td.getWidth(), td.getHeight(), z);
            parameters.use(TEXTURE_TYPE);
            genMipMap();
        }
        unbind();
    }

    public TextureArray(Texture... texture){
        super(GL_TEXTURE_2D_ARRAY);
        bind();
        
        pixmapList = new ArrayList<>();
        for(int z = 0; z < texture.length; z++){
            Pixmap td = texture[z].getPixmap().copy();
            pixmapList.add(td);
    
            parameters.texSubImage3D(GL_TEXTURE_2D_ARRAY, td.getBuffer(), td.getWidth(), td.getHeight(), z);
            parameters.use(TEXTURE_TYPE);
            genMipMap();
        }
        unbind();
    }

    public TextureArray(String... file){
        this(false, file);
    }

    public TextureArray(boolean invY, String... file){
        super(GL_TEXTURE_2D_ARRAY);
        bind();
        
        pixmapList = new ArrayList<>();
        for(int z = 0; z < file.length; z++){
            String f = file[z];
            Pixmap td = PixmapIO.load(f, false, invY);
            pixmapList.add(td);
    
            parameters.texSubImage3D(GL_TEXTURE_2D_ARRAY, td.getBuffer(), td.getWidth(), td.getHeight(), z);
        }
        
        parameters.use(TEXTURE_TYPE);
        genMipMap();
    }


    public static void unbind(){
        glBindTexture(GL_TEXTURE_2D_ARRAY, 0);
    }

    public List<Pixmap> getPixmapList(){
        return pixmapList;
    }

    public int getWidth(){
        if(pixmapList.size() > 0)
            return pixmapList.get(0).getWidth();
        else
            return -1;
    }

    public int getHeight(){
        if(pixmapList.size() > 0)
            return pixmapList.get(0).getHeight();
        else
            return -1;
    }
    
}
