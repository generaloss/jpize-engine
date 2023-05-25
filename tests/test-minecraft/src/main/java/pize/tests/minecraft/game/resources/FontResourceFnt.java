package pize.tests.minecraft.game.resources;

import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.tests.minecraft.utils.log.Logger;

public class FontResourceFnt extends Resource<BitmapFont>{

    private BitmapFont font;
    private boolean loaded;

    public FontResourceFnt(String location){
        super(location);
    }

    @Override
    public void loadResource(){
        try{
            font = FontLoader.loadFnt(getLocation());
            loaded = true;
        }catch(RuntimeException e){
            loaded = false;
            Logger.instance().warn(e.getLocalizedMessage());
        }
    }

    @Override
    public void reloadResource(){
        BitmapFont oldFont = font;
        loadResource();
        oldFont.dispose();
    }

    @Override
    public boolean isLoaded(){
        return loaded;
    }

    @Override
    public BitmapFont getResource(){
        return font;
    }

    @Override
    public void dispose(){
        font.dispose();
    }

}