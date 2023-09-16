package jpize.tests.minecraftose.main.biome.vanilla;

import jpize.tests.minecraftose.main.biome.BiomeProperties;

public class BiomeBeach extends BiomeProperties{

    public BiomeBeach(){
        grassColor.set3(0.7, 0.8, 0.1);
        waterColor.set3(0, 0.5, 0.75);
        hillsMul = 1;
        erosionMul = 0.3F;
    }

}