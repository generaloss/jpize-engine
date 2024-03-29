package jpize.graphics.texture.atlas;

import jpize.util.res.Resource;
import jpize.graphics.texture.pixmap.PixmapRGBA;
import jpize.graphics.texture.pixmap.PixmapIO;
import jpize.graphics.texture.Region;
import jpize.graphics.texture.Texture;

import java.util.*;

public class TextureAtlas<I>{

    private final List<Image<I>> images;
    private Map<I, Region> regions;
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
        
        final PixmapRGBA pixmap = new PixmapRGBA(width, height);
        
        final TextureAtlasNode root = new TextureAtlasNode(0, 0, width - paddingLeft, height - paddingTop);
        regions = new HashMap<>(images.size());
        
        // Iterate all images to generate
        for(final Image<I> image: images){
            final TextureAtlasNode drawResult = root.insert(image.pixmap, paddingLeft, paddingTop, paddingRight, paddingBottom);
            if(drawResult == null)
                throw new Error("Insufficient atlas area");
            
            final int drawX = drawResult.getX() + paddingLeft;
            final int drawY = drawResult.getY() + paddingTop;
            final int drawWidth = image.pixmap.getWidth();
            final int drawHeight = image.pixmap.getHeight();
            
            pixmap.drawPixmap(image.pixmap, drawX, drawY);
            
            regions.put(image.identifier, new Region(
                (double) (drawX) / width,
                (double) (drawY) / height,
                (double) (drawX + drawWidth ) / width,
                (double) (drawY + drawHeight) / height
            ));
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
    
    
    public void put(I identifier, PixmapRGBA pixmap){
        images.add(new Image<>(pixmap, identifier));
    }
    
    public void put(I identifier, Resource res){
        put(identifier, PixmapIO.load(res));
    }
    
    public void put(I identifier, String path){
        put(identifier, Resource.internal(path));
    }
    

    public Map<I, Region> getRegions(){
        return regions;
    }
    
    public Region getRegion(I identifier){
        return regions.get(identifier);
    }

    
    public Texture getTexture(){
        return texture;
    }

    public int size(){
        return Math.max(regions.size(), images.size());
    }


    private static class Image<K>{

        public final PixmapRGBA pixmap;
        public final K identifier; // Indexing for regions
        public final int halfPerimeter;

        public Image(PixmapRGBA pixmap, K identifier){
            this.pixmap = pixmap;
            this.identifier = identifier;
            halfPerimeter = pixmap.getWidth() + pixmap.getHeight();
        }

    }

}
