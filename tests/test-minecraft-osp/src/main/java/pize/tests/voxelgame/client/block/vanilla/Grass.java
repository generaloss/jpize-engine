package pize.tests.voxelgame.client.block.vanilla;

import pize.graphics.util.color.ImmutableColor;
import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.model.BlockModel;
import pize.tests.voxelgame.client.block.model.Face;
import pize.tests.voxelgame.client.block.model.Quad;
import pize.tests.voxelgame.client.block.shape.BlockCursor;
import pize.tests.voxelgame.client.chunk.mesh.ChunkMeshType;
import pize.tests.voxelgame.client.resources.GameResources;
import pize.tests.voxelgame.main.Direction;
import pize.tests.voxelgame.main.audio.BlockSoundPack;

public class Grass extends BlockProperties{

    public static final ImmutableColor COLOR = new ImmutableColor(0.3, 0.55, 0.15, 1);

    
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
            new BlockModel(ChunkMeshType.SOLID)
                .face(new Face(new Quad(0, 1, 0,  0, 0, 0,  1, 0, 1,  1, 1, 1), resources.getBlockRegion("grass"), COLOR))
                .face(new Face(new Quad(1, 1, 0,  1, 0, 0,  0, 0, 1,  0, 1, 1), resources.getBlockRegion("grass"), COLOR)),
            null,
            BlockCursor.SOLID
        );
    }
    
}