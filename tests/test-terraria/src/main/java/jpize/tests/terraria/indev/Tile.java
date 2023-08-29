package jpize.tests.terraria.indev;

import jpize.graphics.texture.Texture;

public class Tile extends ITile{

    private TileTextures textures;

    public Tile(String id, Texture texture, TextureType textureType){
        super(id);

        textures = TileTextures.generate(texture, textureType);
    }

}
