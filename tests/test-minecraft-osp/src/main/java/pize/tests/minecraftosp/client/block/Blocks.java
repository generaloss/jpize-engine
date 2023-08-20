package pize.tests.minecraftosp.client.block;

import pize.tests.minecraftosp.client.block.air.Air;
import pize.tests.minecraftosp.client.block.air.VoidAir;
import pize.tests.minecraftosp.client.block.vanilla.*;
import pize.tests.minecraftosp.main.registry.Registry;

public class Blocks{

    public static final Block VOID_AIR          = new VoidAir        (-1);
    public static final Block AIR               = new Air            ( 0);

    public static final Block DIRT              = new Dirt           (1 );
    public static final Block GRASS_BLOCK       = new GrassBlock     (2 );
    public static final Block STONE             = new Stone          (3 );

    public static final Block OAK_LOG           = new OakLog         (4 );
    public static final Block OAK_LEAVES        = new OakLeaves      (5 );
    public static final Block SPRUCE_LOG        = new SpruceLog      (6 );
    public static final Block SPRUCE_LEAVES     = new SpruceLeaves   (7 );
    public static final Block BIRCH_LOG         = new BirchLog       (8 );
    public static final Block BIRCH_LEAVES      = new BirchLeaves    (9 );

    public static final Block CACTUS            = new Cactus         (10);
    public static final Block GRASS             = new Grass          (11);
    public static final Block SAND              = new Sand           (12);
    public static final Block WATER             = new Water          (13);
    public static final Block ICE               = new Ice            (14);
    public static final Block SNOWY_GRASS_BLOCK = new SnowyGrassBlock(15);

    public static final Block GLASS             = new Glass          (16);
    public static final Block LAMP              = new Lamp           (17);


    public static void register(){
        Registry.Block. register(VOID_AIR         );
        Registry.Block. register(AIR              );
        Registry.Block. register(DIRT             );
        Registry.Block. register(GRASS_BLOCK      );
        Registry.Block. register(STONE            );
        Registry.Block. register(OAK_LOG          );
        Registry.Block. register(OAK_LEAVES       );
        Registry.Block. register(SPRUCE_LOG       );
        Registry.Block. register(SPRUCE_LEAVES    );
        Registry.Block. register(BIRCH_LOG        );
        Registry.Block. register(BIRCH_LEAVES     );
        Registry.Block. register(CACTUS           );
        Registry.Block. register(GRASS            );
        Registry.Block. register(SAND             );
        Registry.Block. register(WATER            );
        Registry.Block. register(ICE              );
        Registry.Block. register(SNOWY_GRASS_BLOCK);
        Registry.Block. register(GLASS            );
        Registry.Block. register(LAMP             );
    }

}
