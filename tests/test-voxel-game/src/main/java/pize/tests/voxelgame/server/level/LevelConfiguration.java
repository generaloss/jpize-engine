package pize.tests.voxelgame.server.level;

import pize.tests.voxelgame.server.chunk.gen.ChunkGenerator;
import pize.math.vecmath.vector.Vec2f;

public class LevelConfiguration{
    
    private String name;
    private ChunkGenerator generator;
    private Vec2f spawn;
    
    public void load(String name, ChunkGenerator generator){
        this.name = name;
        this.generator = generator;
        this.spawn = new Vec2f(25.5, 25.5);
    }
    
    
    public String getName(){
        return name;
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
    
}
