package pize.tests.voxelgame.client.block;

import pize.tests.voxelgame.Minecraft;
import pize.tests.voxelgame.client.block.vanilla.*;
import pize.tests.voxelgame.client.resources.GameResources;

import java.util.HashMap;
import java.util.Map;

public class Blocks{

    private final static Map<Byte, BlockProperties> fromID = new HashMap<>();
    
    public static BlockProperties fromID(byte blockID){
        return fromID.get(blockID);
    }
    
    public static BlockProperties register(BlockProperties properties){
        fromID.put(properties.getID(), properties);
        return properties;
    }

    public static void init(Minecraft session){
        final GameResources resources = session.getRenderer().getSession().getResources();
        for(BlockProperties blockProperties: fromID.values())
            blockProperties.load(resources);
    }
    
    public static final BlockProperties VOID_AIR    = register(new VoidAir   (-1 ));
    public static final BlockProperties AIR         = register(new Air       ( 0 ));
    public static final BlockProperties DIRT        = register(new Dirt      ( 1 ));
    public static final BlockProperties GRASS_BLOCK = register(new GrassBlock( 2 ));
    public static final BlockProperties STONE       = register(new Stone     ( 3 ));
    public static final BlockProperties GLASS       = register(new Glass     ( 4 ));
    public static final BlockProperties OAK_LOG     = register(new OakLog    ( 5 ));
    public static final BlockProperties LAMP        = register(new Lamp      ( 6 ));
    public static final BlockProperties GRASS       = register(new Grass     ( 7 ));
    public static final BlockProperties OAK_LEAVES  = register(new OakLeaves ( 8 ));
    public static final BlockProperties WATER       = register(new Water     ( 9 ));
    public static final BlockProperties SAND        = register(new Sand      ( 10));

}
