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

    public void add(int id, Texture texture){
        pages.put(id, texture);
    }

    public Texture get(int id){
        return pages.get(id);
    }

    public boolean has(int id){
        return pages.containsKey(id);
    }

    @Override
    public void dispose(){
        for(Texture page: pages.values())
            page.dispose();
    }

}
