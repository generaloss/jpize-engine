package jpize.tests.minecraftose.client.block.vanilla;

import jpize.tests.minecraftose.client.block.Block;
import jpize.tests.minecraftose.client.block.model.BlockModel;
import jpize.tests.minecraftose.client.block.shape.BlockCollide;
import jpize.tests.minecraftose.client.block.shape.BlockCursor;
import jpize.tests.minecraftose.client.chunk.mesh.ChunkMeshType;
import jpize.tests.minecraftose.client.resources.GameResources;
import jpize.tests.minecraftose.main.Dir;
import jpize.tests.minecraftose.main.audio.BlockSoundPack;
import jpize.tests.minecraftose.main.chunk.ChunkUtils;

public class Lamp extends Block{

    public Lamp(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        final BlockModel modelOn = new BlockModel(ChunkMeshType.SOLID).allFaces(resources.getBlockRegion("redstone_lamp_on"));
        final BlockModel modelOff = new BlockModel(ChunkMeshType.SOLID).allFaces(resources.getBlockRegion("redstone_lamp"));

        newState()
                .setSolid(true)
                .setLightLevel(ChunkUtils.MAX_LIGHT_LEVEL)
                .setOpacity(ChunkUtils.MAX_LIGHT_LEVEL)
                .setTranslucent(false)
                .setSoundPack(BlockSoundPack.GLASS)
                .setFacing(Dir.NONE)
                .setModel(modelOn)
                .setCollide(BlockCollide.SOLID)
                .setCursor(BlockCursor.SOLID);

        newState()
                .setSolid(true)
                .setLightLevel(0)
                .setOpacity(ChunkUtils.MAX_LIGHT_LEVEL)
                .setTranslucent(false)
                .setSoundPack(BlockSoundPack.GLASS)
                .setFacing(Dir.NONE)
                .setModel(modelOff)
                .setCollide(BlockCollide.SOLID)
                .setCursor(BlockCursor.SOLID);
    }
    
}
