package jpize.tests.minecraftosp.main.biome.vanilla;

import jpize.tests.minecraftosp.main.biome.BiomeProperties;

public class BiomeRiver extends BiomeProperties{

    public BiomeRiver(){
        grassColor.set(0.4, 0.7, 0.4);
        waterColor.set(0, 0.15, 0.9);
        hillsMul = -0.5F;
        erosionMul = 0.1F;
    }

}