package jpize.tests.minecraftose.main.biome.vanilla;

import jpize.tests.minecraftose.main.biome.BiomeProperties;

public class BiomeSea extends BiomeProperties{

    public BiomeSea(){
        grassColor.set3(0.4, 0.7, 0.4);
        waterColor.set3(0, 0.5, 0.75); // 0 0.4 0.9
        hillsMul = -1;
        erosionMul = 0.3F;
    }

}
