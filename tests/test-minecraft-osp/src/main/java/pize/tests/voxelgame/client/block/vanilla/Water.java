package pize.tests.voxelgame.client.block.vanilla;

import pize.graphics.util.color.ImmutableColor;
import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.model.BlockModel;
import pize.tests.voxelgame.client.block.shape.BlockCollide;
import pize.tests.voxelgame.client.block.shape.BlockCursor;
import pize.tests.voxelgame.client.chunk.mesh.ChunkMeshType;
import pize.tests.voxelgame.client.resources.GameResources;
import pize.tests.voxelgame.main.Direction;

public class Water extends BlockProperties{

    public static final ImmutableColor COLOR = new ImmutableColor(0, 0.2, 0.9, 1);


    public Water(int id){
        super(id);
    }

    @Override
    protected void load(GameResources resources){
        solid = true;
        lightLevel = 0;
        opacity = 0;
        translucent = false;
        soundPack = null;

        newState(
            Direction.NONE,
            new BlockModel(ChunkMeshType.TRANSLUCENT)
                .allFaces( resources.getBlockRegion("water"), COLOR),
            BlockCollide.SOLID,
            BlockCursor.SOLID
        );
    }

}
