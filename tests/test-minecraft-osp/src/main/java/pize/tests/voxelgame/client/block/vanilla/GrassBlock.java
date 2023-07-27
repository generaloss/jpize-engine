package pize.tests.voxelgame.client.block.vanilla;

import pize.graphics.util.color.ImmutableColor;
import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.model.BlockModel;
import pize.tests.voxelgame.client.block.shape.BlockCollide;
import pize.tests.voxelgame.client.block.shape.BlockCursor;
import pize.tests.voxelgame.client.chunk.mesh.ChunkMeshType;
import pize.tests.voxelgame.client.resources.GameResources;
import pize.tests.voxelgame.main.Direction;
import pize.tests.voxelgame.main.audio.BlockSoundPack;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.MAX_LIGHT_LEVEL;

public class GrassBlock extends BlockProperties{

    public static final ImmutableColor COLOR = new ImmutableColor(0.3, 0.55, 0.15, 1);
    
    public GrassBlock(int id){
        super(id);
    }

    @Override
    protected void load(GameResources resources){
        solid = true;
        lightLevel = 0;
        opacity = MAX_LIGHT_LEVEL;
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
