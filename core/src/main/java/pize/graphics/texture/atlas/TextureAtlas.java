package pize.graphics.texture.atlas;

import pize.files.Resource;
import pize.graphics.texture.Pixmap;
import pize.graphics.texture.PixmapIO;
import pize.graphics.texture.Region;
import pize.graphics.texture.Texture;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TextureAtlas{

    private final List<Image> images;
    private Region[] regions;
    private Texture texture;

    public TextureAtlas(){
        images = new ArrayList<>();
    }
    
    public void generate(int width, int height, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom){
        // Sort images from big to small perimeter
        final int atlasHalfPerimeter = width + height;
        images.sort(Comparator.comparingInt(
            image -> (atlasHalfPerimeter - image.halfPerimeter)
        ));
        
        final Pixmap pixmap = new Pixmap(width, height);
        
        final TextureAtlasNode root = new TextureAtlasNode(0, 0, width - paddingLeft, height - paddingTop);
        regions = new Region[images.size()];
        
        // Iterate all images to generate
        for(final Image image: images){
            final TextureAtlasNode drawResult = root.insert(image.pixmap, paddingLeft, paddingTop, paddingRight, paddingBottom);
            if(drawResult == null)
                throw new Error("Insufficient atlas area");
            
            int drawX = drawResult.getX() + paddingLeft;
            int drawY = drawResult.getY() + paddingTop;
            int drawWidth = image.pixmap.getWidth();
            int drawHeight = image.pixmap.getHeight();
            
            pixmap.drawPixmap(image.pixmap, drawX, drawY);
            
            regions[image.index] = new Region(
                (double) (drawX) / width,
                (double) (drawY) / height,
                (double) (drawX + drawWidth ) / width,
                (double) (drawY + drawHeight) / height
            );
        }
        
        texture = new Texture(pixmap);
        images.clear();
    }
    
    public void generate(int width, int height, int paddingRight, int paddingBottom){
        generate(width, height, 0, 0, paddingRight, paddingBottom);
    }
    
    public void generate(int width, int height, int padding){
        generate(width, height, padding, padding);
    }
    
    public void generate(int width, int height){
        generate(width, height, 0);
    }
    
    
    public int put(Pixmap pixmap){
        int index = images.size();
        images.add(new Image(pixmap, index));
        return index;
    }
    
    public int put(Resource res){
        return put(PixmapIO.load(res));
    }
    
    public int put(String path){
        return put(new Resource(path));
    }
    

    public Region[] getRegions(){
        return regions;
    }
    
    public Region getRegion(int index){
        return regions[index];
    }

    
    public Texture getTexture(){
        return texture;
    }

    public int size(){
        return Math.max(regions.length, images.size());
    }


    private static class Image{

        public final Pixmap pixmap;
        public final int index; // Indexing for regions
        public final int halfPerimeter;

        public Image(Pixmap pixmap, int index){
            this.pixmap = pixmap;
            this.index = index;
            halfPerimeter = pixmap.getWidth() + pixmap.getHeight();
        }

    }

}
