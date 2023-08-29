package jpize.tests.minecraftosp.client.block.vanilla;

import jpize.graphics.util.color.ImmutableColor;
import jpize.tests.minecraftosp.client.block.Block;
import jpize.tests.minecraftosp.client.block.model.BlockModel;
import jpize.tests.minecraftosp.client.block.shape.BlockCollide;
import jpize.tests.minecraftosp.client.block.shape.BlockCursor;
import jpize.tests.minecraftosp.client.chunk.mesh.ChunkMeshType;
import jpize.tests.minecraftosp.client.resources.GameResources;
import jpize.tests.minecraftosp.main.Dir;
import jpize.tests.minecraftosp.main.audio.BlockSoundPack;
import jpize.tests.minecraftosp.main.chunk.ChunkUtils;

public class OakLeaves extends Block{

    public static final ImmutableColor COLOR = new ImmutableColor(0.25, 0.8, 0.15);

    public OakLeaves(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        final BlockModel model = new BlockModel(ChunkMeshType.SOLID)
                .allFaces(resources.getBlockRegion("oak_leaves"), COLOR)
                .setFacesTransparentForNeighbors(true);

        newState()
                .setSolid(true)
                .setLightLevel(0)
                .setOpacity(ChunkUtils.MAX_LIGHT_LEVEL)
                .setTranslucent(false)
                .setSoundPack(BlockSoundPack.GRASS)
                .setFacing(Dir.NONE)
                .setModel(model)
                .setCollide(BlockCollide.SOLID)
                .setCursor(BlockCursor.SOLID);
    }

}