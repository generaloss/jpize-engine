package pize.tests.minecraftosp.client.block;

import pize.tests.minecraftosp.Minecraft;
import pize.tests.minecraftosp.client.block.vanilla.*;
import pize.tests.minecraftosp.client.resources.GameResources;

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
        System.out.println("[Resources] Load Blocks");
        final GameResources resources = session.getRenderer().getSession().getResources();
        for(BlockProperties blockProperties: fromID.values())
            blockProperties.load(resources);
    }
    
    public static final BlockProperties VOID_AIR          = register(new VoidAir        (-1 ));
    public static final BlockProperties AIR               = register(new Air            ( 0 ));
    public static final BlockProperties DIRT              = register(new Dirt           ( 1 ));
    public static final BlockProperties GRASS_BLOCK       = register(new GrassBlock     ( 2 ));
    public static final BlockProperties STONE             = register(new Stone          ( 3 ));
    public static final BlockProperties GLASS             = register(new Glass          ( 4 ));
    public static final BlockProperties OAK_LOG           = register(new OakLog         ( 5 ));
    public static final BlockProperties LAMP              = register(new Lamp           ( 6 ));
    public static final BlockProperties GRASS             = register(new Grass          ( 7 ));
    public static final BlockProperties OAK_LEAVES        = register(new OakLeaves      ( 8 ));
    public static final BlockProperties WATER             = register(new Water          ( 9 ));
    public static final BlockProperties SAND              = register(new Sand           ( 10));
    public static final BlockProperties SPRUCE_LOG        = register(new SpruceLog      ( 11));
    public static final BlockProperties SPRUCE_LEAVES     = register(new SpruceLeaves   ( 12));
    public static final BlockProperties ICE               = register(new Ice            ( 13));
    public static final BlockProperties SNOWY_GRASS_BLOCK = register(new SnowyGrassBlock( 14 ));
    public static final BlockProperties BIRCH_LOG         = register(new BirchLog       ( 15));
    public static final BlockProperties BIRCH_LEAVES      = register(new BirchLeaves    ( 16));

}
