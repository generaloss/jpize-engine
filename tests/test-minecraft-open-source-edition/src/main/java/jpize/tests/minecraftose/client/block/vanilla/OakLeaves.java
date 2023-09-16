package jpize.tests.minecraftose.client.block.vanilla;

import jpize.graphics.util.color.ImmutableColor;
import jpize.tests.minecraftose.client.block.Block;
import jpize.tests.minecraftose.client.block.model.BlockModel;
import jpize.tests.minecraftose.client.block.shape.BlockCollide;
import jpize.tests.minecraftose.client.block.shape.BlockCursor;
import jpize.tests.minecraftose.client.chunk.mesh.ChunkMeshType;
import jpize.tests.minecraftose.client.resources.GameResources;
import jpize.tests.minecraftose.main.Dir;
import jpize.tests.minecraftose.main.audio.BlockSoundPack;
import jpize.tests.minecraftose.main.chunk.ChunkUtils;

public class OakLeaves extends Block{

    public static final ImmutableColor COLOR = new ImmutableColor(0.35, 0.75, 0.3);

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