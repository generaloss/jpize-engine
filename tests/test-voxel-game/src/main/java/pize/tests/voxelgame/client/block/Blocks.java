package pize.tests.voxelgame.client.block;

import pize.tests.voxelgame.client.block.vanilla.*;

import java.util.HashMap;
import java.util.Map;

public class Blocks{
    
    public static void init(){ }
    
    private final static Map<Integer, BlockProperties> fromID = new HashMap<>();
    
    public static BlockProperties fromID(int id){
        return fromID.get(id);
    }
    
    public static BlockProperties register(BlockProperties properties){
        fromID.put(properties.getID(), properties);
        return properties;
    }
    
    public static final BlockProperties VOID_AIR    = register(new VoidAir(-1)  );
    public static final BlockProperties AIR         = register(new Air(0)       );
    public static final BlockProperties DIRT        = register(new Dirt(1)      );
    public static final BlockProperties GRASS_BLOCK = register(new GrassBlock(2));
    public static final BlockProperties STONE       = register(new Stone(3)     );
    public static final BlockProperties GLASS       = register(new Glass(4)     );
    public static final BlockProperties OAK_LOG     = register(new OakLog(5)    );
    public static final BlockProperties LAMP        = register(new Lamp(6)      );
    public static final BlockProperties GRASS       = register(new Grass(7)     );
    
}
