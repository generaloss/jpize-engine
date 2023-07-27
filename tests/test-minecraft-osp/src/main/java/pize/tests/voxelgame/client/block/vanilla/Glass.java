package pize.tests.voxelgame.client.block.vanilla;

import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.model.BlockModel;
import pize.tests.voxelgame.client.block.shape.BlockCollide;
import pize.tests.voxelgame.client.block.shape.BlockCursor;
import pize.tests.voxelgame.client.chunk.mesh.ChunkMeshType;
import pize.tests.voxelgame.client.resources.GameResources;
import pize.tests.voxelgame.main.Direction;
import pize.tests.voxelgame.main.audio.BlockSoundPack;

public class Glass extends BlockProperties{

    public Glass(int id){
        super(id);
    }

    @Override
    protected void load(GameResources resources){
        solid = true;
        lightLevel = 0;
        opacity = 4;
        translucent = false;
        soundPack = BlockSoundPack.GLASS;

        newState(
            Direction.NONE,
            new BlockModel(ChunkMeshType.SOLID)
                .allFaces(resources.getBlockRegion("glass")),
            BlockCollide.SOLID,
            BlockCursor.SOLID
        );
    }

}