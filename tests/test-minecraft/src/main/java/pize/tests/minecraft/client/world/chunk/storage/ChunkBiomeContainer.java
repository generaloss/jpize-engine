package pize.tests.minecraft.client.world.chunk.storage;

import pize.tests.minecraft.server.world.gen.biome.BiomeType;

import static pize.tests.minecraft.client.world.chunk.utils.IChunk.*;

public class ChunkBiomeContainer extends ChunkData{

    private final BiomeType[] biomes;

    public ChunkBiomeContainer(){
        biomes = new BiomeType[AREA];
    }


    public BiomeType get(int x,int z){
        return biomes[getIndex(x,z)];
    }

    public void set(int x,int z,BiomeType biome){
        biomes[getIndex(x,z)] = biome;
    }


    public BiomeType[] array(){
        return biomes;
    }

}
