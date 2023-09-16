package jpize.tests.minecraftose.client.block.vanilla;

import jpize.tests.minecraftose.client.block.Block;
import jpize.tests.minecraftose.client.block.model.BlockModel;
import jpize.tests.minecraftose.client.block.model.Face;
import jpize.tests.minecraftose.client.block.model.Quad;
import jpize.tests.minecraftose.client.block.shape.BlockCursor;
import jpize.tests.minecraftose.client.chunk.mesh.ChunkMeshType;
import jpize.tests.minecraftose.client.resources.GameResources;
import jpize.tests.minecraftose.main.Dir;
import jpize.tests.minecraftose.main.audio.BlockSoundPack;

public class Grass extends Block{

    public Grass(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        final BlockModel model = new BlockModel(ChunkMeshType.CUSTOM)
                .face(new Face(new Quad(0, 1, 0,  0, 0, 0,  1, 0, 1,  1, 1, 1), resources.getBlockRegion("grass")).enableGrassColoring())
                .face(new Face(new Quad(1, 1, 0,  1, 0, 0,  0, 0, 1,  0, 1, 1), resources.getBlockRegion("grass")).enableGrassColoring());

        newState()
                .setSolid(false)
                .setLightLevel(0)
                .setOpacity(0)
                .setTranslucent(false)
                .setSoundPack(BlockSoundPack.GRASS)
                .setFacing(Dir.NONE)
                .setModel(model)
                .setCollide(null)
                .setCursor(BlockCursor.SOLID);
    }
    
}