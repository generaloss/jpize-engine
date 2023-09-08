package jpize.tests.minecraftosp.main.biome.vanilla;

import jpize.tests.minecraftosp.main.biome.BiomeProperties;

public class BiomeIceRiver extends BiomeProperties{

    public BiomeIceRiver(){
        grassColor.set3(0.4, 0.7, 0.4);
        waterColor.set3(0, 0.05, 0.9);
        hillsMul = -0.5F;
        erosionMul = 0.1F;
    }

}