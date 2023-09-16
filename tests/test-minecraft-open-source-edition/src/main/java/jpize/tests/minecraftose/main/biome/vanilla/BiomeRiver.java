package jpize.tests.minecraftose.main.biome.vanilla;

import jpize.tests.minecraftose.main.biome.BiomeProperties;

public class BiomeRiver extends BiomeProperties{

    public BiomeRiver(){
        grassColor.set3(0.4, 0.7, 0.4);
        waterColor.set3(0, 0.5, 0.75);
        hillsMul = -0.5F;
        erosionMul = 0.1F;
    }

}