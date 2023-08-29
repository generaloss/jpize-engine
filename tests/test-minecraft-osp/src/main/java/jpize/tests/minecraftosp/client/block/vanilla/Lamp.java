package jpize.tests.minecraftosp.client.block.vanilla;

import jpize.tests.minecraftosp.client.block.Block;
import jpize.tests.minecraftosp.client.block.model.BlockModel;
import jpize.tests.minecraftosp.client.block.shape.BlockCollide;
import jpize.tests.minecraftosp.client.block.shape.BlockCursor;
import jpize.tests.minecraftosp.client.chunk.mesh.ChunkMeshType;
import jpize.tests.minecraftosp.client.resources.GameResources;
import jpize.tests.minecraftosp.main.Dir;
import jpize.tests.minecraftosp.main.audio.BlockSoundPack;
import jpize.tests.minecraftosp.main.chunk.ChunkUtils;

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
