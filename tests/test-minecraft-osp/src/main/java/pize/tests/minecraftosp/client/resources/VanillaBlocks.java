package pize.tests.minecraftosp.client.resources;

public class VanillaBlocks{

    private static final String BLOCK_DIR = "texture/block/old-minecraft/";

    public static void register(GameResources resources){
        System.out.println("[Resources]: Load Block Atlas");

        resources.registerBlockRegion(BLOCK_DIR, "grass_block_side");
        resources.registerBlockRegion(BLOCK_DIR, "grass_block_snow");
        resources.registerBlockRegion(BLOCK_DIR, "grass_block_side_overlay");
        resources.registerBlockRegion(BLOCK_DIR, "grass_block_top");
        resources.registerBlockRegion(BLOCK_DIR, "dirt");
        resources.registerBlockRegion(BLOCK_DIR, "stone");
        resources.registerBlockRegion(BLOCK_DIR, "grass");
        resources.registerBlockRegion(BLOCK_DIR, "glass");
        resources.registerBlockRegion(BLOCK_DIR, "birch_log");
        resources.registerBlockRegion(BLOCK_DIR, "birch_log_top");
        resources.registerBlockRegion(BLOCK_DIR, "birch_leaves");
        resources.registerBlockRegion(BLOCK_DIR, "oak_log");
        resources.registerBlockRegion(BLOCK_DIR, "oak_log_top");
        resources.registerBlockRegion(BLOCK_DIR, "oak_leaves");
        resources.registerBlockRegion(BLOCK_DIR, "spruce_log");
        resources.registerBlockRegion(BLOCK_DIR, "spruce_log_top");
        resources.registerBlockRegion(BLOCK_DIR, "spruce_leaves");
        resources.registerBlockRegion(BLOCK_DIR, "redstone_lamp_on");
        resources.registerBlockRegion(BLOCK_DIR, "redstone_lamp");
        resources.registerBlockRegion(BLOCK_DIR, "water");
        resources.registerBlockRegion(BLOCK_DIR, "sand");
        resources.registerBlockRegion(BLOCK_DIR, "ice");
        resources.registerBlockRegion(BLOCK_DIR, "snow");
        resources.registerBlockRegion(BLOCK_DIR, "cactus_side");
        resources.registerBlockRegion(BLOCK_DIR, "cactus_top");
        resources.registerBlockRegion(BLOCK_DIR, "cactus_bottom");
    }

}
