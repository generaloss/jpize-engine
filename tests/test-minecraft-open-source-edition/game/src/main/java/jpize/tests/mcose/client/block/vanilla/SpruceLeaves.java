package jpize.tests.mcose.client.block.vanilla;

import jpize.graphics.util.color.ImmutableColor;
import jpize.tests.mcose.client.block.Block;
import jpize.tests.mcose.client.block.model.BlockModel;
import jpize.tests.mcose.client.block.shape.BlockCollide;
import jpize.tests.mcose.client.block.shape.BlockCursor;
import jpize.tests.mcose.client.chunk.mesh.ChunkMeshType;
import jpize.tests.mcose.client.resources.GameResources;
import jpize.tests.mcose.main.Dir;
import jpize.tests.mcose.main.audio.BlockSoundPack;
import jpize.tests.mcose.main.chunk.ChunkUtils;

public class SpruceLeaves extends Block{

    public static final ImmutableColor COLOR = new ImmutableColor(0, 0.6, 0.3, 1);

    public SpruceLeaves(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        final BlockModel model = new BlockModel(ChunkMeshType.SOLID)
                .allFaces(resources.getBlockRegion("spruce_leaves"), COLOR)
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