package pize.tests.minecraftosp.client.block.vanilla;

import pize.tests.minecraftosp.client.block.Block;
import pize.tests.minecraftosp.client.block.model.BlockModel;
import pize.tests.minecraftosp.client.block.shape.BlockCollide;
import pize.tests.minecraftosp.client.block.shape.BlockCursor;
import pize.tests.minecraftosp.client.chunk.mesh.ChunkMeshType;
import pize.tests.minecraftosp.client.resources.GameResources;
import pize.tests.minecraftosp.main.Dir;
import pize.tests.minecraftosp.main.audio.BlockSoundPack;
import pize.tests.minecraftosp.main.chunk.ChunkUtils;

public class Dirt extends Block{
    
    public Dirt(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        final BlockModel model = new BlockModel(ChunkMeshType.SOLID)
                .allFaces(resources.getBlockRegion("dirt"));

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
