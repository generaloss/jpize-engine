package jpize.tests.mcose.client.block.vanilla;

import jpize.tests.mcose.client.block.Block;
import jpize.tests.mcose.client.block.model.BlockModel;
import jpize.tests.mcose.client.block.shape.BlockCollide;
import jpize.tests.mcose.client.block.shape.BlockCursor;
import jpize.tests.mcose.client.chunk.mesh.ChunkMeshType;
import jpize.tests.mcose.client.resources.GameResources;
import jpize.tests.mcose.main.Dir;
import jpize.tests.mcose.main.audio.BlockSoundPack;
import jpize.tests.mcose.main.chunk.ChunkUtils;

public class Sandstone extends Block{

    public Sandstone(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        final BlockModel model = new BlockModel(ChunkMeshType.SOLID)
            .sideXZFaces(resources.getBlockRegion("sandstone"))
            .nyFace(resources.getBlockRegion("sandstone_bottom"))
            .pyFace(resources.getBlockRegion("sandstone_top"));

        newState()
            .setSolid(true)
            .setLightLevel(0)
            .setOpacity(ChunkUtils.MAX_LIGHT_LEVEL)
            .setTranslucent(false)
            .setSoundPack(BlockSoundPack.STONE)
            .setFacing(Dir.NONE)
            .setModel(model)
            .setCollide(BlockCollide.SOLID)
            .setCursor(BlockCursor.SOLID);
    }

}