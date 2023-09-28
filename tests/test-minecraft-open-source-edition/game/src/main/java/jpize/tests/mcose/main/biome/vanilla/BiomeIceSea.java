package jpize.tests.mcose.main.biome.vanilla;

import jpize.tests.mcose.main.biome.BiomeProperties;

public class BiomeIceSea extends BiomeProperties{

    public BiomeIceSea(){
        grassColor.set3(0.4, 0.7, 0.4);
        waterColor.set3(0, 0.4, 0.9);
        hillsMul = -1;
        erosionMul = 0.3F;
    }

}