package pize.tests.minecraftosp.client.block.vanilla;

import pize.graphics.util.color.ImmutableColor;
import pize.tests.minecraftosp.client.block.BlockProperties;
import pize.tests.minecraftosp.client.block.model.BlockModel;
import pize.tests.minecraftosp.client.block.shape.BlockCollide;
import pize.tests.minecraftosp.client.block.shape.BlockCursor;
import pize.tests.minecraftosp.client.chunk.mesh.ChunkMeshType;
import pize.tests.minecraftosp.client.resources.GameResources;
import pize.tests.minecraftosp.main.Direction;
import pize.tests.minecraftosp.main.audio.BlockSoundPack;
import pize.tests.minecraftosp.main.chunk.ChunkUtils;

public class GrassBlock extends BlockProperties {

    public static final ImmutableColor COLOR = new ImmutableColor(0.3, 0.55, 0.15, 1);
    
    public GrassBlock(int id){
        super(id);
    }

    @Override
    protected void load(GameResources resources){
        solid = true;
        lightLevel = 0;
        opacity = ChunkUtils.MAX_LIGHT_LEVEL;
        translucent = false;
        soundPack = BlockSoundPack.GRASS;

        newState(
            Direction.NONE,
            new BlockModel(ChunkMeshType.SOLID)
                .sideXZFaces(resources.getBlockRegion("grass_block_side"))
                .sideXZFaces(resources.getBlockRegion("grass_block_side_overlay"), COLOR)
                .pyFace(resources.getBlockRegion("grass_block_top"), COLOR)
                .nyFace(resources.getBlockRegion("dirt")),
            BlockCollide.SOLID,
            BlockCursor.SOLID
        );
    }

}
