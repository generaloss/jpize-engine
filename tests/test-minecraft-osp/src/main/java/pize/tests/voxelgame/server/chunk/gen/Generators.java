package pize.tests.voxelgame.server.chunk.gen;

import java.util.HashMap;
import java.util.Map;

public class Generators{

    private static final Map<String, ChunkGenerator> fromID = new HashMap<>();

    public static void register(ChunkGenerator generator){
        fromID.put(generator.getID(), generator);
    }

    public static ChunkGenerator fromID(String generatorID){
        if(fromID.size() == 0){
            register(DefaultGenerator.getInstance());
            register(FlatGenerator.getInstance());
            register(IslandsGenerator.getInstance());
        }

        return fromID.get(generatorID);
    }

}
