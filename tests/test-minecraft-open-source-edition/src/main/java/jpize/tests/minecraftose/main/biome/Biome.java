package jpize.tests.minecraftose.main.biome;

import jpize.tests.minecraftose.main.biome.vanilla.*;

public enum Biome{

    SEA            (new BiomeSea()           ),
    ICE_SEA        (new BiomeIceSea()        ),
    RIVER          (new BiomeRiver()         ),
    ICE_RIVER      (new BiomeIceRiver()      ),
    DESERT         (new BiomeDesert()        ),
    BEACH          (new BiomeBeach()         ),
    SNOWY_BEACH    (new BiomeSnowyBeach()    ),
    FOREST         (new BiomeForest()        ),
    SAVANNA        (new BiomeSavanna()       ),
    TAIGA          (new BiomeTaiga()         ),
    SNOWY_TAIGA    (new BiomeSnowyTaiga()    ),
    WINDSWEPT_HILLS(new BiomeWindsweptHills()),
    ;


    private final BiomeProperties biome;

    Biome(BiomeProperties biome){
        this.biome = biome;
    }

    public BiomeProperties getProps(){
        return biome;
    }

}
