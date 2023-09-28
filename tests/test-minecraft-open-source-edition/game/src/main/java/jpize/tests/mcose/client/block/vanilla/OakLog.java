package jpize.tests.mcose.client.block.vanilla;

import jpize.tests.mcose.client.block.Block;
import jpize.tests.mcose.client.block.BlockRotation;
import jpize.tests.mcose.client.block.model.BlockModel;
import jpize.tests.mcose.client.block.shape.BlockCollide;
import jpize.tests.mcose.client.block.shape.BlockCursor;
import jpize.tests.mcose.client.chunk.mesh.ChunkMeshType;
import jpize.tests.mcose.client.resources.GameResources;
import jpize.tests.mcose.main.Dir;
import jpize.tests.mcose.main.audio.BlockSoundPack;
import jpize.tests.mcose.main.chunk.ChunkUtils;

public class OakLog extends Block{
    
    public OakLog(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        final BlockModel model = new BlockModel(ChunkMeshType.SOLID)
            .sideXZFaces(resources.getBlockRegion("oak_log"))
            .yFaces(resources.getBlockRegion("oak_log_top"));

        newState()
                .setSolid(true)
                .setLightLevel(0)
                .setOpacity(ChunkUtils.MAX_LIGHT_LEVEL)
                .setTranslucent(false)
                .setSoundPack(BlockSoundPack.WOOD)
                .setFacing(Dir.POSITIVE_Y)
                .setModel(model)
                .setCollide(BlockCollide.SOLID)
                .setCursor(BlockCursor.SOLID);

        newState()
                .setSolid(true)
                .setLightLevel(0)
                .setOpacity(ChunkUtils.MAX_LIGHT_LEVEL)
                .setTranslucent(false)
                .setSoundPack(BlockSoundPack.WOOD)
                .setFacing(Dir.NEGATIVE_Z)
                .setModel(model.rotated(BlockRotation.X90))
                .setCollide(BlockCollide.SOLID)
                .setCursor(BlockCursor.SOLID);

        newState()
                .setSolid(true)
                .setLightLevel(0)
                .setOpacity(ChunkUtils.MAX_LIGHT_LEVEL)
                .setTranslucent(false)
                .setSoundPack(BlockSoundPack.WOOD)
                .setFacing(Dir.POSITIVE_X)
                .setModel(model.rotated(BlockRotation.Z90))
                .setCollide(BlockCollide.SOLID)
                .setCursor(BlockCursor.SOLID);
    }
    
}