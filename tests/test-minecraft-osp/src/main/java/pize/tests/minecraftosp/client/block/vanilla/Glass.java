package pize.tests.minecraftosp.client.block.vanilla;

import pize.tests.minecraftosp.client.block.Block;
import pize.tests.minecraftosp.client.block.model.BlockModel;
import pize.tests.minecraftosp.client.block.shape.BlockCollide;
import pize.tests.minecraftosp.client.block.shape.BlockCursor;
import pize.tests.minecraftosp.client.chunk.mesh.ChunkMeshType;
import pize.tests.minecraftosp.client.resources.GameResources;
import pize.tests.minecraftosp.main.Dir;
import pize.tests.minecraftosp.main.audio.BlockSoundPack;

public class Glass extends Block{

    public Glass(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        final BlockModel model = new BlockModel(ChunkMeshType.SOLID)
                .allFaces(resources.getBlockRegion("glass"));

        newState()
                .setSolid(true)
                .setLightLevel(0)
                .setOpacity(4)
                .setTranslucent(false)
                .setSoundPack(BlockSoundPack.GLASS)
                .setFacing(Dir.NONE)
                .setModel(model)
                .setCollide(BlockCollide.SOLID)
                .setCursor(BlockCursor.SOLID);
    }

}