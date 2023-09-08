package jpize.tests.minecraftosp.main.biome.vanilla;

import jpize.tests.minecraftosp.main.biome.BiomeProperties;

public class BiomeDesert extends BiomeProperties{

    public BiomeDesert(){
        grassColor.set3(0.7, 0.8, 0.1);
        waterColor.set3(1, 1, 0.7);
        hillsMul = 1;
        erosionMul = 0.3F;
    }

}
