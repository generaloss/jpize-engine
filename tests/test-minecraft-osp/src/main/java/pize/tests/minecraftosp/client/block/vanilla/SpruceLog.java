package pize.tests.minecraftosp.client.block.vanilla;

import pize.tests.minecraftosp.client.block.BlockProperties;
import pize.tests.minecraftosp.client.block.BlockRotation;
import pize.tests.minecraftosp.client.block.model.BlockModel;
import pize.tests.minecraftosp.client.block.shape.BlockCollide;
import pize.tests.minecraftosp.client.block.shape.BlockCursor;
import pize.tests.minecraftosp.client.chunk.mesh.ChunkMeshType;
import pize.tests.minecraftosp.client.resources.GameResources;
import pize.tests.minecraftosp.main.Direction;
import pize.tests.minecraftosp.main.audio.BlockSoundPack;
import pize.tests.minecraftosp.main.chunk.ChunkUtils;

public class SpruceLog extends BlockProperties{

    public SpruceLog(int id){
        super(id);
    }

    @Override
    protected void load(GameResources resources){
        solid = true;
        lightLevel = 0;
        opacity = ChunkUtils.MAX_LIGHT_LEVEL;
        translucent = false;
        soundPack = BlockSoundPack.WOOD;

        final BlockModel model = new BlockModel(ChunkMeshType.SOLID)
            .sideXZFaces(resources.getBlockRegion("spruce_log"))
            .yFaces(resources.getBlockRegion("spruce_log_top"));

        newState(
            Direction.NONE,
            model,
            BlockCollide.SOLID,
            BlockCursor.SOLID
        );

        newState(
            Direction.NONE,
            model.rotated(BlockRotation.Z90),
            BlockCollide.SOLID,
            BlockCursor.SOLID
        );

        newState(
            Direction.NONE,
            model.rotated(BlockRotation.X90),
            BlockCollide.SOLID,
            BlockCursor.SOLID
        );
    }

}