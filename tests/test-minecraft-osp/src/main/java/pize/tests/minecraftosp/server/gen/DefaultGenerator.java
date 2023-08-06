package pize.tests.minecraftosp.server.gen;

import pize.math.Mathc;
import pize.math.Maths;
import pize.math.function.FastNoiseLite;
import pize.math.util.Random;
import pize.tests.minecraftosp.client.block.Blocks;
import pize.tests.minecraftosp.main.chunk.storage.Heightmap;
import pize.tests.minecraftosp.main.chunk.storage.HeightmapType;
import pize.tests.minecraftosp.server.chunk.ServerChunk;
import pize.tests.minecraftosp.server.gen.structure.Moai;
import pize.tests.minecraftosp.server.gen.structure.Tree;

import static pize.tests.minecraftosp.main.chunk.ChunkUtils.*;

public class DefaultGenerator implements ChunkGenerator{
    
    private static final int OCEAN_LEVEL = 120;
    
    private final Tree tree;
    private final Moai moai;
    private final FastNoiseLite
        continentalnessNoise, erosionNoise, peaksValleysNoise, temperatureNoise, humidityNoise;

    private final Random random;
    

    private DefaultGenerator(){
        continentalnessNoise = new FastNoiseLite();
        erosionNoise = new FastNoiseLite();
        peaksValleysNoise = new FastNoiseLite();
        temperatureNoise = new FastNoiseLite();
        humidityNoise = new FastNoiseLite();

        continentalnessNoise.setFrequency(0.002F);
        continentalnessNoise.setFractalType(FastNoiseLite.FractalType.FBm);
        continentalnessNoise.setFractalOctaves(7);
        
        erosionNoise.setFrequency(0.0009F);
        erosionNoise.setFractalType(FastNoiseLite.FractalType.FBm);
        erosionNoise.setFractalOctaves(6);

        peaksValleysNoise.setFrequency(0.02F);
        peaksValleysNoise.setFractalType(FastNoiseLite.FractalType.FBm);
        peaksValleysNoise.setFractalOctaves(5);

        tree = new Tree();
        moai = new Moai();

        random = new Random();
    }

    @Override
    public void generate(ServerChunk chunk){
        final int baseX = chunk.getPosition().x * SIZE;
        final int baseZ = chunk.getPosition().z * SIZE;
        random.setSeed(baseX + baseZ * 31L);

        final int seed = chunk.getLevel().getConfiguration().getSeed();
        
        continentalnessNoise.setSeed(seed);
        erosionNoise.setSeed(seed);
        peaksValleysNoise.setSeed(seed);
        temperatureNoise.setSeed(seed);
        humidityNoise.setSeed(seed);

        final Heightmap heightmapSurface = chunk.getHeightMap(HeightmapType.SURFACE);
        
        // Stopwatch timer = new Stopwatch().start();
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
                
                for(int y = height; y < OCEAN_LEVEL; y++)
                    if(chunk.getBlockID(lx, y, lz) == Blocks.AIR.getID())
                        chunk.setBlockFast(lx, y, lz, Blocks.WATER.getDefaultData());
            }
        }

        heightmapSurface.update();
        
        for(int lx = 0; lx < SIZE; lx++){
            final int x = lx + baseX;
            for(int lz = 0; lz < SIZE; lz++){
                final int z = lz + baseZ;

                final int height = heightmapSurface.getHeight(lx, lz);
                
                if(chunk.getBlockProps(lx, height, lz).getID() == Blocks.WATER.getID())
                    continue;

                final int stoneLevel = Maths.random(
                    Maths.round((float) height / HEIGHT_IDX * 2),
                    Maths.round((float) height / HEIGHT_IDX * 5)
                );

                for(int y = height - stoneLevel; y < height; y++)
                    chunk.setBlockFast(lx, y, lz, Blocks.DIRT.getDefaultData());

                if(height > OCEAN_LEVEL + 3 + 5 * peaksValleysNoise.getNoise(x, z)){
                    chunk.setBlockFast(lx, height, lz, Blocks.GRASS_BLOCK.getDefaultData());

                    if(random.randomBoolean(0.1))
                        chunk.setBlock(lx, height + 1, lz, Blocks.GRASS.getDefaultData());
                    else if(random.randomBoolean(0.03 - continentalnessNoise.getNoise(x, z) / 50))
                        tree.generate(chunk, x, height + 1, z, random);
                    else if(random.randomBoolean(0.00004))
                        moai.generate(chunk, x, height - 1, z, random);

                }else if(height > OCEAN_LEVEL - 6 + 6 * peaksValleysNoise.getNoise(x, z))
                    chunk.setBlockFast(lx, height, lz, Blocks.SAND.getDefaultData());
                else
                    chunk.setBlockFast(lx, height, lz, Blocks.DIRT.getDefaultData());
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
