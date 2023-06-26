package pize.tests.voxelgame.server.chunk.gen;

import pize.math.Mathc;
import pize.math.Maths;
import pize.math.function.FastNoiseLite;
import pize.math.util.Random;
import pize.tests.voxelgame.base.chunk.storage.HeightmapType;
import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.server.chunk.ServerChunk;
import pize.tests.voxelgame.server.level.ServerLevel;

import static pize.tests.voxelgame.base.chunk.ChunkUtils.HEIGHT;
import static pize.tests.voxelgame.base.chunk.ChunkUtils.SIZE;

public class DefaultGenerator implements ChunkGenerator{
    
    private static final int OCEAN_LEVEL = 120;
    
    
    private final FastNoiseLite
        continentalnessNoise, erosionNoise, peaksValleysNoise, temperatureNoise, humidityNoise;
    
    private final FastNoiseLite noiseLight = new FastNoiseLite();
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
        
        erosionNoise.setFrequency(0.002F);
        erosionNoise.setFractalType(FastNoiseLite.FractalType.FBm);
        erosionNoise.setFractalOctaves(6);
        
        noiseLight.setFrequency(0.03F);
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
        
        // Stopwatch timer = new Stopwatch().start();
        for(int lx = 0; lx < SIZE; lx++){
            final int x = lx + baseX;
            for(int lz = 0; lz < SIZE; lz++){
                final int z = lz + baseZ;
                
                final float erosion = (erosionNoise.getNoise(x, z) + 1) * 0.5F;
                final float continentalness = Mathc.pow((continentalnessNoise.getNoise(x, z) - 0.3), 3);
                final int height = Maths.round(continentalness * 32 + 128);
                
                for(int y = 0; y < height; y++)
                    chunk.setBlockFast(lx, y, lz, Block.STONE.getDefaultState());
                
                for(int y = height; y < HEIGHT; y++){
                    final float continentalness3D = (continentalnessNoise.getNoise(x, y, z) + 1);
                    if(continentalness3D < Mathc.pow(erosion * 0.8, (float) y / HEIGHT))
                        chunk.setBlockFast(lx, y, lz, Block.STONE.getDefaultState());
                }
                
                for(int y = height; y < OCEAN_LEVEL; y++){
                    if(chunk.getBlockID(lx, y, lz) == Block.AIR.ID)
                        chunk.setBlockFast(lx, y, lz, Block.GLASS.getDefaultState());
                }
                
            }
        }
        
        chunk.getHeightMap(HeightmapType.SURFACE).update();
        
        for(int lx = 0; lx < SIZE; lx++){
            for(int lz = 0; lz < SIZE; lz++){
                
                int height = chunk.getHeightMap(HeightmapType.SURFACE).getHeight(lx, lz);
                if(height >= OCEAN_LEVEL)
                    chunk.setBlockFast(lx, height, lz, Block.GRASS_BLOCK.getDefaultState());
            }
        }
        
        for(int lx = 0; lx < SIZE; lx++){
            final int x = lx + baseX;
            
            for(int lz = 0; lz < SIZE; lz++){
                final int z = lz + baseZ;
                
                for(int y = 0; y < HEIGHT; y++)
                    chunk.setLight(lx, y, lz, Maths.round((noiseLight.getNoise(x, y, z) + 1) / 2 * 15));
            }
        }
        // System.out.println("Gen: " + timer.getMillis());
    }
    
    @Override
    public void generateDecorations(ServerChunk chunk){
        if(true)
            return;
        
        final int baseX = SIZE * chunk.getPosition().x;
        final int baseZ = SIZE * chunk.getPosition().z;
        
        for(int lx = 0; lx < SIZE; lx++){
            final int x = lx + baseX;
            
            for(int lz = 0; lz < SIZE; lz++){
                final int z = lz + baseZ;
                
                boolean spawnTree = random.randomBoolean(0.03);
                if(spawnTree){
                    final int y = chunk.getHeightMap(HeightmapType.SURFACE).getHeight(lx, lz);
                    
                    spawnTree(chunk.getLevel(), x, y + 1, z);
                }
            }
        }
    }
    
    private void spawnTree(ServerLevel level, int x, int y, int z){
        final int logHeight = Math.round(Maths.map(continentalnessNoise.getNoise(x, z), -1, 1, 4, 8));
        
        for(int ly = 0; ly < logHeight; ly++){
            level.setBlock(x, y + ly, z, Block.OAK_LOG.getDefaultState());
        }
    }
    
    
    private static DefaultGenerator instance;

    public static DefaultGenerator getInstance(){
        if(instance == null)
            instance = new DefaultGenerator();

        return instance;
    }

}
