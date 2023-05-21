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

    private final List<AtlasImage> images;
    private Region[] textureRegions;
    private Texture texture;

    public TextureAtlas(){
        images = new ArrayList<>();
    }


    public void generate(int width, int height){
        images.sort(Comparator.comparingInt(image->image.area));

        Pixmap atlasPixmap = new Pixmap(width, height);

        TextureAtlasNode root = new TextureAtlasNode(0, 0, width, height);
        textureRegions = new Region[images.size()];

        for(int i = images.size() - 1; i >= 0; i--){
            AtlasImage image = images.get(i);

            TextureAtlasNode node = root.insert(image.pixmap, 0);
            if(node == null)
                throw new Error("Size of atlas is too small");

            atlasPixmap.drawPixmap(image.pixmap, node.rect.x, node.rect.y);

            textureRegions[image.id] = new Region(
                (double) node.rect.x / width,
                (double) node.rect.y / height,
                (double) (node.rect.x + node.rect.width) / width,
                (double) (node.rect.y + node.rect.height) / height
            );
        }

        images.clear();

        texture = new Texture(atlasPixmap);
    }


    public int put(String path){
        int id = images.size();
        images.add(new AtlasImage(PixmapIO.load(path), id));
        return id;
    }
    
    public int put(Resource res){
        int id = images.size();
        images.add(new AtlasImage(PixmapIO.load(res), id));
        return id;
    }

    public Region getRegion(int textureID){
        return textureRegions[textureID];
    }

    public Texture getTexture(){
        return texture;
    }

    public int size(){
        return textureRegions.length;
    }


    private static class AtlasImage{

        public final Pixmap pixmap;
        public final int id;
        public final int area;

        public AtlasImage(Pixmap pixmap, int id){
            this.pixmap = pixmap;
            this.id = id;
            area = pixmap.getWidth() * pixmap.getHeight();
        }

    }

}
