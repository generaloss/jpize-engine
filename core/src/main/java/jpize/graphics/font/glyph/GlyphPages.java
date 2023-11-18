package jpize.graphics.font.glyph;

import jpize.graphics.texture.Texture;
import jpize.util.Disposable;

import java.util.HashMap;
import java.util.Map;

public class GlyphPages implements Disposable{

    private final Map<Integer, Texture> pages;

    public GlyphPages(){
        this.pages = new HashMap<>();
    }

    public void add(int pageID, Texture texture){
        pages.put(pageID, texture);
    }

    public Texture get(int pageID){
        return pages.get(pageID);
    }

    public boolean has(int pageID){
        return pages.containsKey(pageID);
    }

    @Override
    public void dispose(){
        for(Texture page: pages.values())
            page.dispose();
    }

}
