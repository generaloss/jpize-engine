package pize.tests.minecraft.game.resources;

import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontCharset;
import pize.graphics.font.FontLoader;
import pize.tests.minecraft.utils.log.Logger;

public class FontResourceTtf extends Resource<BitmapFont>{

    private BitmapFont font;
    private final int size;
    private final FontCharset charset;
    private boolean loaded;

    public FontResourceTtf(String location,int size,FontCharset charset){
        super(location);
        this.size = size;
        this.charset = charset;
    }

    public FontResourceTtf(String location,int size){
        this(location,size,FontCharset.DEFAULT);
    }

    @Override
    public void loadResource(){
        try{
            font = FontLoader.loadTrueType(getLocation(),size,charset);
            loaded = true;
        }catch(Throwable e){
            loaded = false;
            Logger.instance().error(e.getLocalizedMessage());
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