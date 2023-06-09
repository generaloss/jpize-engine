package pize.tests.voxelgame.server.world;

import pize.tests.voxelgame.server.chunk.gen.ChunkGenerator;
import pize.math.vecmath.vector.Vec2f;

public class WorldConfiguration{
    
    private String name;
    private ChunkGenerator generator;
    private boolean skyLight;
    private Vec2f spawn;
    
    public void load(String name, ChunkGenerator generator, boolean skyLight){
        this.name = name;
        this.generator = generator;
        this.skyLight = skyLight;
        this.spawn = new Vec2f();
    }
    
    
    public String getName(){
        return name;
    }
    
    public ChunkGenerator getGenerator(){
        return generator;
    }
    
    public boolean isSkyLight(){
        return skyLight;
    }
    
    public Vec2f getSpawn(){
        return spawn;
    }
    
}
