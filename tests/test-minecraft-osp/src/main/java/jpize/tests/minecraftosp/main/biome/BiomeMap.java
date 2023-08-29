package jpize.tests.minecraftosp.main.biome;

import java.util.Arrays;

import static jpize.tests.minecraftosp.main.chunk.ChunkUtils.*;

public class BiomeMap{

    protected final Biome[] biomes;

    public BiomeMap(){
        biomes = new Biome[AREA];
        Arrays.fill(biomes, Biome.SEA);
    }


    public Biome getBiome(int x, int z){
        return biomes[getIndex(x, z)];
    }

    public void setBiome(int x, int z, Biome biome){
        biomes[getIndex(x, z)] = biome;
    }


    public Biome[] getValues(){
        return biomes;
    }

    public void setValues(Biome[] biomes){
        System.arraycopy(biomes, 0, this.biomes, 0, biomes.length);
    }

}
