package jpize.graphics.font.glyph;

import java.util.HashMap;
import java.util.Map;

public class GlyphMap{

    private final Map<Integer, Glyph> map;

    public GlyphMap(){
         this.map = new HashMap<>();
    }

    public void add(Glyph glyph){
        map.put(glyph.code, glyph);
    }

    public Glyph get(int code){
        return map.get(code);
    }

    public boolean has(int code){
        return map.containsKey(code);
    }

}
