package pize.tests.minecraftosp.client.block.vanilla;

import pize.graphics.util.color.ImmutableColor;
import pize.tests.minecraftosp.client.block.BlockProperties;
import pize.tests.minecraftosp.client.block.model.BlockModel;
import pize.tests.minecraftosp.client.chunk.mesh.ChunkMeshType;
import pize.tests.minecraftosp.client.resources.GameResources;
import pize.tests.minecraftosp.main.Direction;

public class Water extends BlockProperties {

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
            null,
            null
        );
    }

}
