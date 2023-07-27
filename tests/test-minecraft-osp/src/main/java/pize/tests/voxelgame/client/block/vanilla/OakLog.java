package pize.tests.voxelgame.client.block.vanilla;

import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.BlockRotation;
import pize.tests.voxelgame.client.block.model.BlockModel;
import pize.tests.voxelgame.client.block.shape.BlockCollide;
import pize.tests.voxelgame.client.block.shape.BlockCursor;
import pize.tests.voxelgame.client.chunk.mesh.ChunkMeshType;
import pize.tests.voxelgame.client.resources.GameResources;
import pize.tests.voxelgame.main.Direction;
import pize.tests.voxelgame.main.audio.BlockSoundPack;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.MAX_LIGHT_LEVEL;

public class OakLog extends BlockProperties{
    
    public OakLog(int id){
        super(id);
    }

    @Override
    protected void load(GameResources resources){
        solid = true;
        lightLevel = 0;
        opacity = MAX_LIGHT_LEVEL;
        translucent = false;
        soundPack = BlockSoundPack.WOOD;

        final BlockModel model = new BlockModel(ChunkMeshType.SOLID)
            .sideXZFaces(resources.getBlockRegion("oak_log"))
            .yFaces(resources.getBlockRegion("oak_log_top"));

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

        // newState(
        //     Direction.NONE,
        //     model.rotated(BlockRotation.X90),
        //     BlockCollide.SOLID,
        //     BlockCursor.SOLID
        // );
    }
    
}