package pize.tests.minecraftosp.client.block.vanilla;

import pize.tests.minecraftosp.client.block.BlockProperties;
import pize.tests.minecraftosp.client.block.model.BlockModel;
import pize.tests.minecraftosp.client.block.model.Face;
import pize.tests.minecraftosp.client.block.model.Quad;
import pize.tests.minecraftosp.client.block.shape.BlockCursor;
import pize.tests.minecraftosp.client.chunk.mesh.ChunkMeshType;
import pize.tests.minecraftosp.client.resources.GameResources;
import pize.tests.minecraftosp.main.Direction;
import pize.tests.minecraftosp.main.audio.BlockSoundPack;

public class Grass extends BlockProperties {

    public Grass(int id){
        super(id);
    }

    @Override
    protected void load(GameResources resources){
        solid = false;
        lightLevel = 0;
        opacity = 0;
        translucent = false;
        soundPack = BlockSoundPack.GRASS;

        newState(
            Direction.NONE,
            new BlockModel(ChunkMeshType.CUSTOM)
                .face(new Face(new Quad(0, 1, 0,  0, 0, 0,  1, 0, 1,  1, 1, 1), resources.getBlockRegion("grass")).enableGrassColoring())
                .face(new Face(new Quad(1, 1, 0,  1, 0, 0,  0, 0, 1,  0, 1, 1), resources.getBlockRegion("grass")).enableGrassColoring()),
            null,
            BlockCursor.SOLID
        );
    }
    
}