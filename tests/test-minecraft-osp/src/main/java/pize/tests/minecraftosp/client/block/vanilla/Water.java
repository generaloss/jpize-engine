package pize.tests.minecraftosp.client.block.vanilla;

import pize.tests.minecraftosp.client.block.BlockProperties;
import pize.tests.minecraftosp.client.block.model.BlockModel;
import pize.tests.minecraftosp.client.chunk.mesh.ChunkMeshType;
import pize.tests.minecraftosp.client.resources.GameResources;
import pize.tests.minecraftosp.main.Direction;

public class Water extends BlockProperties{

    public Water(int id){
        super(id);
    }

    @Override
    protected void load(GameResources resources){
        solid = true;
        lightLevel = 0;
        opacity = 1;
        translucent = false;
        soundPack = null;

        newState(
            Direction.NONE,
            new BlockModel(ChunkMeshType.TRANSLUCENT)
                .allFaces( resources.getBlockRegion("water"), (byte) 2),
            null,
            null
        );
    }

}
