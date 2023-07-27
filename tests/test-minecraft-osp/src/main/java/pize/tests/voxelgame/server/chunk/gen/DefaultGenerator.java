package pize.tests.voxelgame.server.chunk.gen;

import pize.math.Mathc;
import pize.math.Maths;
import pize.math.function.FastNoiseLite;
import pize.math.util.Random;
import pize.tests.voxelgame.client.block.Blocks;
import pize.tests.voxelgame.main.chunk.storage.Heightmap;
import pize.tests.voxelgame.main.chunk.storage.HeightmapType;
import pize.tests.voxelgame.server.chunk.ServerChunk;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.*;

public class DefaultGenerator implements ChunkGenerator{
    
    private static final int OCEAN_LEVEL = 120;
    
    
    private final FastNoiseLite
        continentalnessNoise, erosionNoise, peaksValleysNoise, temperatureNoise, humidityNoise;

    private final Random random = new Random(22854);
    

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
    }

    @Override
    public void generate(ServerChunk chunk){
        final int baseX = SIZE * chunk.getPosition().x;
        final int baseZ = SIZE * chunk.getPosition().z;
        
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
                
                final float erosion = erosionNoise.getNoise(x, z) * Maths.Sqrt2 * 0.5F; // [0, 1]
                final float continentalness = Mathc.pow((continentalnessNoise.getNoise(x, z) - 0.4), 3);
                final int height = Maths.round(continentalness * 32 + 128);

                for(int y = 0; y < height; y++)
                    chunk.setBlockFast(lx, y, lz, Blocks.STONE.getDefaultData());

                final float yRange = HEIGHT_IDX - height;
                for(int y = height; y < HEIGHT; y++){
                    final float heightK = (y - height) / yRange; // [0, 1]
                    final float continentalness3D = continentalnessNoise.getNoise(x, y, z) * Maths.Sqrt3 * 0.5F + 1; // [0, 1]

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
            for(int lz = 0; lz < SIZE; lz++){
                final int height = heightmapSurface.getHeight(lx, lz);
                
                if(chunk.getBlockProps(lx, height, lz).getID() == Blocks.WATER.getID())
                    continue;

                final int stoneLevel = Maths.random(
                    Maths.round((float) height / HEIGHT_IDX * 2),
                    Maths.round((float) height / HEIGHT_IDX * 5)
                );

                for(int y = height - stoneLevel; y < height; y++)
                    chunk.setBlockFast(lx, y, lz, Blocks.DIRT.getDefaultData());

                if(height > OCEAN_LEVEL + 3)
                    chunk.setBlockFast(lx, height, lz, Blocks.GRASS_BLOCK.getDefaultData());
                else if(height > OCEAN_LEVEL - 6)
                    chunk.setBlockFast(lx, height, lz, Blocks.SAND.getDefaultData());
                else
                    chunk.setBlockFast(lx, height, lz, Blocks.DIRT.getDefaultData());
            }
        }

        chunk.getHeightMap(HeightmapType.HIGHEST).update();
    }
    
    @Override
    public void generateDecorations(ServerChunk chunk){
        final int baseX = SIZE * chunk.getPosition().x;
        final int baseZ = SIZE * chunk.getPosition().z;

        final Heightmap heightmapSurface = chunk.getHeightMap(HeightmapType.SURFACE);
        final Heightmap heightmapHighest = chunk.getHeightMap(HeightmapType.HIGHEST);

        for(int lx = 0; lx < SIZE; lx++){
            final int x = lx + baseX;
            
            for(int lz = 0; lz < SIZE; lz++){
                final int z = lz + baseZ;

                final int height = heightmapSurface.getHeight(lx, lz);

                if(chunk.getBlockID(lx, height, lz) != Blocks.GRASS_BLOCK.getID())
                    continue;
                
                final boolean spawnTree = random.randomBoolean(0.01);
                final boolean generateGrass = random.randomBoolean(0.2);

                if(generateGrass){
                    if(chunk.setBlockFast(lx, height + 1, lz, Blocks.GRASS.getDefaultData()))
                        heightmapHighest.update(lx, lz);
                }else if(spawnTree)
                    spawnTreeGlobal(chunk, x, height + 1, z);
            }
        }
    }

    private void spawnTreeGlobal(ServerChunk chunk, int x, int y, int z){
        final int logHeight = Math.round(Maths.map(continentalnessNoise.getNoise(x, z), -Maths.Sqrt2 * 0.5F, Maths.Sqrt2 * 0.5F, 5, 7));

        for(int ly = 0; ly < logHeight; ly++)
            chunk.getLevel().setBlock(x, y + ly, z, Blocks.OAK_LOG.getDefaultData());

        final int peak = y+logHeight;

        // Верхний 1
        chunk.getLevel().setBlock(x  , peak  , z  , Blocks.OAK_LEAVES.getDefaultData());

        // Окружающие ствол дерева 1х4
        chunk.getLevel().setBlock(x-1, peak  , z  , Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x-1, peak-1, z  , Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x-1, peak-2, z  , Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x-1, peak-3, z  , Blocks.OAK_LEAVES.getDefaultData());

        chunk.getLevel().setBlock(x+1, peak  , z  , Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x+1, peak-1, z  , Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x+1, peak-2, z  , Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x+1, peak-3, z  , Blocks.OAK_LEAVES.getDefaultData());

        chunk.getLevel().setBlock(x  , peak  , z-1, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x  , peak-1, z-1, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x  , peak-2, z-1, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x  , peak-3, z-1, Blocks.OAK_LEAVES.getDefaultData());

        chunk.getLevel().setBlock(x  , peak  , z+1, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x  , peak-1, z+1, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x  , peak-2, z+1, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x  , peak-3, z+1, Blocks.OAK_LEAVES.getDefaultData());

        // Другие 1х3
        chunk.getLevel().setBlock(x-1, peak-1, z-1, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x-1, peak-2, z-1, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x-1, peak-3, z-1, Blocks.OAK_LEAVES.getDefaultData());

        chunk.getLevel().setBlock(x-1, peak-1, z+1, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x-1, peak-2, z+1, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x-1, peak-3, z+1, Blocks.OAK_LEAVES.getDefaultData());

        chunk.getLevel().setBlock(x+1, peak-1, z-1, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x+1, peak-2, z-1, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x+1, peak-3, z-1, Blocks.OAK_LEAVES.getDefaultData());

        chunk.getLevel().setBlock(x+1, peak-1, z+1, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x+1, peak-2, z+1, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x+1, peak-3, z+1, Blocks.OAK_LEAVES.getDefaultData());

        // Другие по краям 3х2
        chunk.getLevel().setBlock(x-2, peak-2, z-1, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x-2, peak-2, z  , Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x-2, peak-2, z+1, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x-2, peak-3, z-1, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x-2, peak-3, z  , Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x-2, peak-3, z+1, Blocks.OAK_LEAVES.getDefaultData());

        chunk.getLevel().setBlock(x+2, peak-2, z-1, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x+2, peak-2, z  , Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x+2, peak-2, z+1, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x+2, peak-3, z-1, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x+2, peak-3, z  , Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x+2, peak-3, z+1, Blocks.OAK_LEAVES.getDefaultData());

        chunk.getLevel().setBlock(x-1, peak-2, z-2, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x  , peak-2, z-2, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x+1, peak-2, z-2, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x-1, peak-3, z-2, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x  , peak-3, z-2, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x+1, peak-3, z-2, Blocks.OAK_LEAVES.getDefaultData());

        chunk.getLevel().setBlock(x-1, peak-2, z+2, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x  , peak-2, z+2, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x+1, peak-2, z+2, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x-1, peak-3, z+2, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x  , peak-3, z+2, Blocks.OAK_LEAVES.getDefaultData());
        chunk.getLevel().setBlock(x+1, peak-3, z+2, Blocks.OAK_LEAVES.getDefaultData());
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
