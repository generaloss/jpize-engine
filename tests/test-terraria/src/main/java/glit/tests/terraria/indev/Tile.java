package glit.tests.terraria.indev;

import glit.graphics.texture.Texture;

public class Tile extends ITile{

    private TileTextures textures;

    public Tile(String id, Texture texture, TextureType textureType){
        super(id);

        textures = TileTextures.generate(texture, textureType);
    }

}
