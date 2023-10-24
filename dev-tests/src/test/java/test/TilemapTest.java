package test;

import jpize.Jpize;
import jpize.io.context.JpizeApplication;
import jpize.tilemap.SimpleTmLayer;
import jpize.tilemap.Tilemap;
import jpize.tilemap.TmLayer;

import java.util.Collection;

public class TilemapTest extends JpizeApplication{

    public void init(){
        Tilemap tilemap = new Tilemap();
        tilemap.addLayer(new SimpleTmLayer("Engine", 45));
        tilemap.addLayer(new SimpleTmLayer("Pizza", -3));
        tilemap.addLayer(new SimpleTmLayer("Java", 17));

        Collection<TmLayer> layers = tilemap.getOrderedLayers();
        for(TmLayer layer: layers){
            System.out.println(layer.getOrder() + ") " + layer.getID());
        }

        Jpize.exit();
    }

}
