package pize.tests.minecraftosp.server.gen;

import pize.math.Mathc;
import pize.math.Maths;
import pize.math.function.FastNoiseLite;
import pize.math.util.Random;
import pize.tests.minecraftosp.client.block.Blocks;
import pize.tests.minecraftosp.main.biome.Biome;
import pize.tests.minecraftosp.main.biome.BiomeMap;
import pize.tests.minecraftosp.main.chunk.storage.Heightmap;
import pize.tests.minecraftosp.main.chunk.storage.HeightmapType;
import pize.tests.minecraftosp.server.chunk.ServerChunk;
import pize.tests.minecraftosp.server.gen.structure.Moai;
import pize.tests.minecraftosp.server.gen.structure.Tree;
import pize.util.time.Stopwatch;

import static pize.tests.minecraftosp.main.chunk.ChunkUtils.*;

public class DefaultGenerator implements ChunkGenerator{
    
    private static final int OCEAN_LEVEL = 120;
    
    private final Tree tree;
    private final Moai moai;
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
        riverNoise.setFractalOctaves(6);
        riverNoise.setFrequency(0.0005F); // FIX

        random = new Random();

        tree = new Tree();
        moai = new Moai();
    }


    @Override
    public void generate(ServerChunk chunk){
        Stopwatch timer = new Stopwatch().start();

        final Heightmap heightmapSurface = chunk.getHeightMap(HeightmapType.SURFACE);

        final int seed = chunk.getLevel().getConfiguration().getSeed();
        continentalnessNoise.setSeed(seed);
        erosionNoise.setSeed(seed);
        peaksValleysNoise.setSeed(seed);
        temperatureNoise.setSeed(seed);
        humidityNoise.setSeed(seed);
        riverNoise.setSeed(seed);

        final int baseX = chunk.getPosition().x * SIZE;
        final int baseZ = chunk.getPosition().z * SIZE;
        random.setSeed(baseX + baseZ * 31L);
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
                    chunk.setBlockFast(lx, y, lz, Blocks.STONE.getDefaultData());

                final float yRange = HEIGHT_IDX - height;
                for(int y = height; y < HEIGHT; y++){
                    final float heightK = (y - height) / yRange; // [0, 1]
                    final float continentalness3D = continentalnessNoise.getUnitNoise(x, y, z); // [0, 1]

                    if(Maths.quintic(erosion * continentalness3D) * 1.2F > heightK)
                        chunk.setBlockFast(lx, y, lz, Blocks.STONE.getDefaultData());
                }
            }
        }

        heightmapSurface.update();

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
                if(height < OCEAN_LEVEL){ // OCEAN
                    if(temperature < 0.3)
                        biome = Biome.ICE_SEA;
                    else
                        biome = Biome.SEA;

                }else if(river > 0.49 && river < 0.5){ // RIVER
                    if(temperature < 0.3)
                        biome = Biome.ICE_RIVER;
                    else
                        biome = Biome.RIVER;

                }else if(height <= OCEAN_LEVEL + 7 * peaksValleysNoise.getNoise(x, z)){ // BEACH
                    if(temperature < 0.3)
                        biome = Biome.SNOWY_BEACH;
                    else
                        biome = Biome.BEACH;

                }else{ // SURFACE
                    if(temperature < 0.4){
                        if(humidity < 0.4)
                            biome = Biome.TAIGA;
                        else
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
                        else
                            biome = Biome.WINDSWEPT_HILLS;
                    }
                }

                biomes.setBiome(lx, lz, biome);
            }
        }

        /** SURFACE DECORATE */

        for(int lx = 0; lx < SIZE; lx++){
            final int x = lx + baseX;
            for(int lz = 0; lz < SIZE; lz++){
                final int z = lz + baseZ;

                final int height = heightmapSurface.getHeight(lx, lz);
                final Biome biome = biomes.getBiome(lx, lz);

                if(chunk.getBlockProps(lx, height, lz).getID() == Blocks.WATER.getID())
                    continue;

                final int stoneLevel = Maths.random(
                    Maths.round((float) height / HEIGHT_IDX * 2),
                    Maths.round((float) height / HEIGHT_IDX * 5)
                );

                for(int y = height - stoneLevel; y < height; y++)
                    chunk.setBlockFast(lx, y, lz, Blocks.DIRT.getDefaultData());

                if(biome == Biome.BEACH || biome == Biome.SNOWY_BEACH)
                    chunk.setBlockFast(lx, height, lz, Blocks.SAND.getDefaultData());
                else if(biome == Biome.SEA || biome == Biome.ICE_SEA)
                    chunk.setBlockFast(lx, height, lz, Blocks.DIRT.getDefaultData());
                else{
                    if(biome == Biome.DESERT){
                        chunk.setBlockFast(lx, height, lz, Blocks.SAND.getDefaultData());
                        continue;
                    }else if(biome == Biome.RIVER){
                        chunk.setBlockFast(lx, height, lz, Blocks.WATER.getDefaultData());
                        continue;
                    }else if(biome == Biome.ICE_RIVER){
                        chunk.setBlockFast(lx, height, lz, Blocks.ICE.getDefaultData());
                        continue;
                    }

                    if(biome == Biome.SNOWY_TAIGA){
                        chunk.setBlockFast(lx, height, lz, Blocks.SNOWY_GRASS_BLOCK.getDefaultData());

                        if(random.randomBoolean(0.005))
                            chunk.setBlock(lx, height + 1, lz, Blocks.GRASS.getDefaultData());
                    }else{
                        chunk.setBlockFast(lx, height, lz, Blocks.GRASS_BLOCK.getDefaultData());

                        if(random.randomBoolean(0.1))
                            chunk.setBlock(lx, height + 1, lz, Blocks.GRASS.getDefaultData());
                    }

                    if(random.randomBoolean(0.02) && biome == Biome.FOREST){
                        if(random.randomBoolean(0.7))
                            tree.generateOakTree(chunk, x, height + 1, z, random);
                        else
                            tree.generateBirchTree(chunk, x, height + 1, z, random);

                    }else if(random.randomBoolean(0.03) && biome == Biome.TAIGA)
                        tree.generateSpruceTree(chunk, x, height + 1, z, random);
                    else if(random.randomBoolean(0.005) && biome == Biome.SNOWY_TAIGA)
                        tree.generateSpruceTree(chunk, x, height + 1, z, random);

                    else if(random.randomBoolean(0.00004))
                        moai.generate(chunk, x, height - 1, z, random);

                }

                for(int y = height; y <= OCEAN_LEVEL; y++)
                    if(chunk.getBlockID(lx, y, lz) == Blocks.AIR.getID()){
                        if(biome == Biome.SEA)
                            chunk.setBlockFast(lx, y, lz, Blocks.WATER.getDefaultData());
                        else
                            chunk.setBlockFast(lx, y, lz, Blocks.ICE.getDefaultData());
                    }
            }
        }

        // [Debug]: chunk.getLevel().getChunkManager().getBlockPool().setBlock(baseX - 2 , 255, baseZ + 2 , Blocks.LAMP.getDefaultData());
        // [Debug]: chunk.getLevel().getChunkManager().getBlockPool().setBlock(baseX - 2 , 255, baseZ + 13 , Blocks.LAMP.getDefaultData());
        // [Debug]: chunk.getLevel().getChunkManager().getBlockPool().setBlock(baseX + 17, 255, baseZ + 2 , Blocks.LAMP.getDefaultData());
        // [Debug]: chunk.getLevel().getChunkManager().getBlockPool().setBlock(baseX + 17, 255, baseZ + 13 , Blocks.LAMP.getDefaultData());
        // [Debug]: chunk.getLevel().getChunkManager().getBlockPool().setBlock(baseX + 2 , 255, baseZ - 2 , Blocks.LAMP.getDefaultData());
        // [Debug]: chunk.getLevel().getChunkManager().getBlockPool().setBlock(baseX + 13 , 255, baseZ - 2 , Blocks.LAMP.getDefaultData());
        // [Debug]: chunk.getLevel().getChunkManager().getBlockPool().setBlock(baseX + 2 , 255, baseZ + 17, Blocks.LAMP.getDefaultData());
        // [Debug]: chunk.getLevel().getChunkManager().getBlockPool().setBlock(baseX + 13 , 255, baseZ + 17, Blocks.LAMP.getDefaultData());

        chunk.getHeightMap(HeightmapType.HIGHEST).update();
        chunk.getHeightMap(HeightmapType.OPAQUE).update();
        chunk.getHeightMap(HeightmapType.SURFACE).update();

        System.out.println("GENERATED IN: " + timer.getMillis());
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
