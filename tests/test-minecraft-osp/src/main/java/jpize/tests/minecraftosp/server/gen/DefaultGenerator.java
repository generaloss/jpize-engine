package jpize.tests.minecraftosp.server.gen;

import jpize.math.Mathc;
import jpize.math.Maths;
import jpize.math.function.FastNoiseLite;
import jpize.math.util.Random;
import jpize.tests.minecraftosp.client.block.Block;
import jpize.tests.minecraftosp.client.block.Blocks;
import jpize.tests.minecraftosp.main.biome.Biome;
import jpize.tests.minecraftosp.main.biome.BiomeMap;
import jpize.tests.minecraftosp.main.chunk.storage.Heightmap;
import jpize.tests.minecraftosp.main.chunk.storage.HeightmapType;
import jpize.tests.minecraftosp.server.chunk.ServerChunk;
import jpize.tests.minecraftosp.server.gen.pool.BlockPool;
import jpize.tests.minecraftosp.server.gen.structure.Cactus;
import jpize.tests.minecraftosp.server.gen.structure.Tower;
import jpize.tests.minecraftosp.server.gen.structure.Tree;

import static jpize.tests.minecraftosp.main.chunk.ChunkUtils.*;

public class DefaultGenerator extends ChunkGenerator{
    
    private static final int SEA_LEVEL = 120;

    private final FastNoiseLite
        continentalnessNoise, erosionNoise, peaksValleysNoise, temperatureNoise, humidityNoise, riverNoise;

    private final Random random;
    

    private DefaultGenerator(){
        continentalnessNoise = new FastNoiseLite();
        erosionNoise = new FastNoiseLite();
        peaksValleysNoise = new FastNoiseLite();
        temperatureNoise = new FastNoiseLite();
        humidityNoise = new FastNoiseLite();
        riverNoise = new FastNoiseLite();

        continentalnessNoise.setFrequency(0.002F); // FIX
        continentalnessNoise.setFractalType(FastNoiseLite.FractalType.FBm);
        continentalnessNoise.setFractalOctaves(12);

        humidityNoise.setFrequency(0.0004F); // FIX
        humidityNoise.setFractalType(FastNoiseLite.FractalType.FBm);
        humidityNoise.setFractalOctaves(14);
        
        erosionNoise.setFrequency(0.0009F); // FIX
        erosionNoise.setFractalType(FastNoiseLite.FractalType.FBm);
        erosionNoise.setFractalOctaves(9);

        peaksValleysNoise.setFrequency(0.02F);
        peaksValleysNoise.setFractalType(FastNoiseLite.FractalType.FBm);
        peaksValleysNoise.setFractalOctaves(8);

        temperatureNoise.setFractalType(FastNoiseLite.FractalType.FBm);
        temperatureNoise.setFrequency(0.0008F); // FIX
        temperatureNoise.setFractalOctaves(14);

        riverNoise.setFractalType(FastNoiseLite.FractalType.FBm);
        riverNoise.setFractalOctaves(10);
        riverNoise.setFrequency(0.0005F); // FIX

        random = new Random();
    }


    @Override
    public void generate(ServerChunk chunk){
        final int seed = chunk.getLevel().getConfiguration().getSeed();
        final int baseX = chunk.getPosition().x * SIZE;
        final int baseZ = chunk.getPosition().z * SIZE;

        continentalnessNoise.setSeed(seed);
        erosionNoise.setSeed(seed);
        peaksValleysNoise.setSeed(seed);
        temperatureNoise.setSeed(seed);
        humidityNoise.setSeed(seed);
        riverNoise.setSeed(seed);
        random.setSeed(seed | (baseX + baseZ * 31L));

        final Heightmap heightmapUnderwaterSurface = chunk.getHeightMap(HeightmapType.UNDERWATER_SURFACE);
        final Heightmap heightmapSurface = chunk.getHeightMap(HeightmapType.SURFACE);
        final BiomeMap biomes = chunk.getBiomes();

        /** GENERATE SURFACE */

        for(int lx = 0; lx < SIZE; lx++){
            final int x = lx + baseX;
            for(int lz = 0; lz < SIZE; lz++){
                final int z = lz + baseZ;

                final float erosion = erosionNoise.getUnitNoise(x, z); // [0, 1]
                final float continentalness = Mathc.pow((continentalnessNoise.getNoise(x, z) - 0.4), 3);
                final int height = Maths.round(continentalness * 32 + 128);

                for(int y = 0; y < height; y++)
                    chunk.setBlockFast(lx, y, lz, Blocks.STONE);

                final float yRange = HEIGHT_IDX - height;
                for(int y = height; y < HEIGHT; y++){
                    final float heightK = (y - height) / yRange; // [0, 1]
                    final float continentalness3D = continentalnessNoise.getUnitNoise(x, y, z); // [0, 1]

                    if(Maths.quintic(erosion * continentalness3D) * 1.2F > heightK)
                        chunk.setBlockFast(lx, y, lz, Blocks.STONE);
                    else
                        break;
                }
            }
        }

        heightmapSurface.update();
        heightmapUnderwaterSurface.updateFrom(heightmapSurface);

        /** BIOMES */

        for(int lx = 0; lx < SIZE; lx++){
            final int x = lx + baseX;
            for(int lz = 0; lz < SIZE; lz++){
                final int z = lz + baseZ;

                final float temperature = temperatureNoise.getNoise(x, z) / 2 + 0.5F;
                final float height = heightmapSurface.getHeight(lx, lz);
                final float humidity = humidityNoise.getNoise(x, z) / 2 + 0.5F;
                final float river = riverNoise.getNoise(x, z) / 2 + 0.5F;

                final Biome biome;
                if(height < SEA_LEVEL){ // OCEAN
                    if(temperature < 0.3)
                        biome = Biome.ICE_SEA;
                    else
                        biome = Biome.SEA;

                }else if(river > 0.49 && river < 0.5){ // RIVER
                    if(temperature < 0.3)
                        biome = Biome.ICE_RIVER;
                    else
                        biome = Biome.RIVER;

                }else if(height <= SEA_LEVEL + 7 * peaksValleysNoise.getNoise(x, z)){ // BEACH
                    if(temperature < 0.3)
                        biome = Biome.SNOWY_BEACH;
                    else
                        biome = Biome.BEACH;

                }else{ // SURFACE
                    if(temperature < 0.4){
                        if(humidity < 0.8)
                            biome = Biome.FOREST;
                        else if(humidity < 0.5)
                            biome = Biome.TAIGA;
                        else// if(humidity < 1)
                            biome = Biome.SNOWY_TAIGA;

                    }else if(temperature < 0.7){
                        if(humidity < 0.5)
                            biome = Biome.SAVANNA;
                        else if(humidity < 0.8)
                            biome = Biome.FOREST;
                        else// if(humidity < 1)
                            biome = Biome.TAIGA;

                    }else{// if(temperature < 1)
                        if(humidity < 0.7)
                            biome = Biome.DESERT;
                        else if(humidity < 0.4)
                            biome = Biome.WINDSWEPT_HILLS;
                        else// if(humidity < 1)
                            biome = Biome.FOREST;
                    }
                }

                biomes.setBiome(lx, lz, biome);
            }
        }

        /** SURFACE */

        for(int lx = 0; lx < SIZE; lx++){
            for(int lz = 0; lz < SIZE; lz++){
                final int height = heightmapSurface.getHeight(lx, lz);
                final Biome biome = biomes.getBiome(lx, lz);

                if(chunk.getBlock(lx, height, lz) == Blocks.WATER)
                    continue;

                // SELECT SURFACE BLOCKS
                final Block surfaceBlock = switch(biome){
                    case DESERT, BEACH, SNOWY_BEACH -> Blocks.SAND;
                    case SNOWY_TAIGA -> Blocks.SNOWY_GRASS_BLOCK;
                    case RIVER, ICE_RIVER, SEA -> Blocks.DIRT;
                    default -> Blocks.GRASS_BLOCK;
                };
                final Block subsurfaceBlock = switch(biome){
                    case DESERT, BEACH, SNOWY_BEACH -> Blocks.SAND;
                    default -> Blocks.DIRT;
                };

                // SET SURFACE BLOCKS
                chunk.setBlockFast(lx, height, lz, surfaceBlock);

                final int subsurfaceLayerHeight = random.random(2, 5);
                for(int y = height - subsurfaceLayerHeight; y < height; y++)
                    chunk.setBlockFast(lx, y, lz, subsurfaceBlock);

                // WATER, ICE
                switch(biome){
                    case RIVER -> chunk.setBlockFast(lx, height, lz, Blocks.WATER);
                    case ICE_RIVER -> chunk.setBlockFast(lx, height, lz, Blocks.ICE);

                    case SEA -> {
                        for(int y = height; y <= SEA_LEVEL; y++)
                            if(chunk.getBlock(lx, y, lz) == Blocks.AIR)
                                chunk.setBlockFast(lx, y, lz, Blocks.WATER);
                    }

                    case ICE_SEA -> {
                        for(int y = height; y <= SEA_LEVEL; y++)
                            if(chunk.getBlock(lx, y, lz) == Blocks.AIR)
                                chunk.setBlockFast(lx, y, lz, Blocks.ICE);
                    }
                }

            }
        }

        /** GRASS */

        for(int lx = 0; lx < SIZE; lx++){
            for(int lz = 0; lz < SIZE; lz++){
                final int height = heightmapSurface.getHeight(lx, lz);
                final Biome biome = biomes.getBiome(lx, lz);

                switch(biome){
                    case FOREST -> {
                        if(random.randomBoolean(0.05))
                            chunk.setBlockFast(lx, height + 1, lz, Blocks.GRASS);
                    }
                    case TAIGA, SNOWY_TAIGA -> {
                        if(random.randomBoolean(0.005))
                            chunk.setBlockFast(lx, height + 1, lz, Blocks.GRASS);
                    }
                }

            }
        }

    }

    @Override
    public void decorate(ServerChunk chunk){
        final BlockPool pool = chunk.getLevel().getBlockPool();
        pool.setGeneratingChunk(chunk);

        final int seed = chunk.getLevel().getConfiguration().getSeed();
        final int baseX = chunk.getPosition().x * SIZE;
        final int baseZ = chunk.getPosition().z * SIZE;

        continentalnessNoise.setSeed(seed);
        erosionNoise.setSeed(seed);
        peaksValleysNoise.setSeed(seed);
        temperatureNoise.setSeed(seed);
        humidityNoise.setSeed(seed);
        riverNoise.setSeed(seed);
        random.setSeed(seed | (baseX + baseZ * 31L));

        // final Heightmap heightmapUnderwaterSurface = chunk.getHeightMap(HeightmapType.UNDERWATER_SURFACE);
        final Heightmap heightmapSurface = chunk.getHeightMap(HeightmapType.SURFACE);
        final BiomeMap biomes = chunk.getBiomes();

        /** STRUCTURES */

        for(int lx = 0; lx < SIZE; lx++){
            final int x = lx + baseX;
            for(int lz = 0; lz < SIZE; lz++){
                final int z = lz + baseZ;
                final int height = heightmapSurface.getHeight(lx, lz);
                final Biome biome = biomes.getBiome(lx, lz);

                switch(biome){
                    case DESERT -> Cactus.generate(pool, x, height + 1, z, random);

                    case FOREST -> {
                        if(random.randomBoolean(0.02)){
                            if(random.randomBoolean(0.7))
                                Tree.generateOakTree(pool, x, height + 1, z, random);
                            else
                                Tree.generateBirchTree(pool, x, height + 1, z, random);
                        }
                    }

                    case TAIGA -> {
                        if(random.randomBoolean(0.03))
                            Tree.generateSpruceTree(pool, x, height + 1, z, random);

                        else if(random.randomBoolean(0.0005))
                            Tower.generate(pool, x, height, z, random);
                    }

                    case SNOWY_TAIGA -> {
                        if(random.randomBoolean(0.005))
                            Tree.generateSpruceTree(pool, x, height + 1, z, random);

                        else if(random.randomBoolean(0.0005))
                            Tower.generate(pool, x, height, z, random);
                    }
                }

            }
        }

    }
    
    
    private static DefaultGenerator instance;

    public static DefaultGenerator getInstance(){
        if(instance == null)
            instance = new DefaultGenerator();

        return instance;
    }

    @Override
    public String getID(){
        return "default";
    }

}
