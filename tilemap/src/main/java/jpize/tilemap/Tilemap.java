package jpize.tilemap;

import java.util.Collection;
import java.util.TreeMap;

public class Tilemap{

    private final TreeMap<String, TmLayer> layers; // layers from ID

    public Tilemap(){

        this.layers = new TreeMap<>();
    }

    public void addLayer(TmLayer layer){
        layers.put(layer.getID(), layer);
    }

    public Collection<TmLayer> getOrderedLayers(){

        return layers.values();
    }

}
