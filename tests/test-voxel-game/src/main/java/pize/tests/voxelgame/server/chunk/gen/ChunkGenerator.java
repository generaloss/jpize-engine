package pize.tests.voxelgame.server.chunk.gen;

import pize.tests.voxelgame.server.chunk.ServerChunk;

import java.util.HashMap;
import java.util.Map;

public interface ChunkGenerator{
    
    Map<String, ChunkGenerator> fromString = new HashMap<>();
    
    static ChunkGenerator fromString(String name){
        if(fromString.size() == 0){
            fromString.put("default", DefaultGenerator.getInstance());
            fromString.put("flat", FlatGenerator.getInstance());
        }
        System.out.println("GGGGGGGG: " + fromString.get(name));
        return fromString.get(name);
    }
    

    void generate(ServerChunk chunk);

}
