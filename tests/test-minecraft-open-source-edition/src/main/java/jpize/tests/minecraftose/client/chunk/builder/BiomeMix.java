package jpize.tests.minecraftose.client.chunk.builder;

import jpize.graphics.util.color.Color;
import jpize.tests.minecraftose.main.biome.Biome;

public class BiomeMix{

    public final Biome biome0, biome1, biome2, biome3;

    public BiomeMix(Biome biome0, Biome biome1, Biome biome2, Biome biome3){
        this.biome0 = biome0;
        this.biome1 = biome1;
        this.biome2 = biome2;
        this.biome3 = biome3;
    }

    public Color getGrassColor(){
        final Color color = new Color(0, 0, 0);
        int n = 0;
        if(biome0 != null){
            color.add3(biome0.getProps().getGrassColor());
            n++;
        }
        if(biome1 != null){
            color.add3(biome1.getProps().getGrassColor());
            n++;
        }
        if(biome2 != null){
            color.add3(biome2.getProps().getGrassColor());
            n++;
        }
        if(biome3 != null){
            color.add3(biome3.getProps().getGrassColor());
            n++;
        }
        return color.div3(n);
    }

    public Color getWaterColor(){
        final Color color = new Color(0, 0, 0);
        int n = 0;
        if(biome0 != null){
            color.add3(biome0.getProps().getWaterColor());
            n++;
        }
        if(biome1 != null){
            color.add3(biome1.getProps().getWaterColor());
            n++;
        }
        if(biome2 != null){
            color.add3(biome2.getProps().getWaterColor());
            n++;
        }
        if(biome3 != null){
            color.add3(biome3.getProps().getWaterColor());
            n++;
        }
        return color.div3(n);
    }

}
