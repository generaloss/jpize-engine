package pize.tests.minecraftosp.server.level;

import pize.math.vecmath.vector.Vec2f;
import pize.tests.minecraftosp.main.level.LevelConfiguration;
import pize.tests.minecraftosp.server.chunk.gen.ChunkGenerator;

public class ServerLevelConfiguration extends LevelConfiguration{
    
    private ChunkGenerator generator;
    private Vec2f spawn;
    private int seed;
    
    
    public void load(String name, int seed, ChunkGenerator generator){
        super.load(name);
        
        this.seed = seed;
        this.generator = generator;
        this.spawn = new Vec2f(-717, -952);
    }
    
    public ChunkGenerator getGenerator(){
        return generator;
    }
    
    public Vec2f getWorldSpawn(){
        return spawn;
    }
    
    public void setWorldSpawn(double x, double z){
        spawn.set(x, z);
    }
    
    public int getSeed(){
        return seed;
    }
    
}
