package jpize.tests.minecraftosp.client.block.vanilla;

import jpize.tests.minecraftosp.client.block.Block;
import jpize.tests.minecraftosp.client.block.model.BlockModel;
import jpize.tests.minecraftosp.client.chunk.mesh.ChunkMeshType;
import jpize.tests.minecraftosp.client.resources.GameResources;
import jpize.tests.minecraftosp.main.Dir;

public class Water extends Block{

    public Water(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        final BlockModel model = new BlockModel(ChunkMeshType.TRANSLUCENT)
                .allFaces( resources.getBlockRegion("water"), (byte) 2);

        newState()
                .setSolid(true)
                .setLightLevel(0)
                .setOpacity(1)
                .setTranslucent(false)
                .setSoundPack(null)
                .setFacing(Dir.NONE)
                .setModel(model)
                .setCollide(null)
                .setCursor(null);
    }

}
