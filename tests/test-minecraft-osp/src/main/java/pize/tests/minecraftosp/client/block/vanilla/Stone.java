package pize.tests.minecraftosp.client.block.vanilla;

import pize.tests.minecraftosp.client.block.BlockProperties;
import pize.tests.minecraftosp.client.block.model.BlockModel;
import pize.tests.minecraftosp.client.block.shape.BlockCollide;
import pize.tests.minecraftosp.client.block.shape.BlockCursor;
import pize.tests.minecraftosp.client.chunk.mesh.ChunkMeshType;
import pize.tests.minecraftosp.client.resources.GameResources;
import pize.tests.minecraftosp.main.Direction;
import pize.tests.minecraftosp.main.audio.BlockSoundPack;
import pize.tests.minecraftosp.main.chunk.ChunkUtils;

public class Stone extends BlockProperties {
    
    public Stone(int id){
        super(id);
    }

    @Override
    protected void load(GameResources resources){
        solid = true;
        lightLevel = 0;
        opacity = ChunkUtils.MAX_LIGHT_LEVEL;
        translucent = false;
        soundPack = BlockSoundPack.STONE;

        newState(
            Direction.NONE,
            new BlockModel(ChunkMeshType.SOLID)
                .allFaces(resources.getBlockRegion("stone")),
            BlockCollide.SOLID,
            BlockCursor.SOLID
        );
    }
    
}
