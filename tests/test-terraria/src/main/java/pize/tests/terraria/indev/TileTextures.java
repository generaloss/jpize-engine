package pize.tests.terraria.indev;

import pize.graphics.texture.Texture;

public class TileTextures{

    private final Texture texture;

    private TileTextures(Texture texture){
        this.texture = texture;
    }



    public static TileTextures generate(Texture texture, TextureType type){
        TileTextures tileTextures = new TileTextures(texture);

        if(type == TextureType.TILE_DEFAULT){

        }

        return tileTextures;
    }

}
