package pize.tests.minecraftosp.client.block.vanilla;

import pize.tests.minecraftosp.client.block.Block;
import pize.tests.minecraftosp.client.block.model.BlockModel;
import pize.tests.minecraftosp.client.block.model.Face;
import pize.tests.minecraftosp.client.block.model.Quad;
import pize.tests.minecraftosp.client.block.shape.BlockCollide;
import pize.tests.minecraftosp.client.block.shape.BlockCursor;
import pize.tests.minecraftosp.client.chunk.mesh.ChunkMeshType;
import pize.tests.minecraftosp.client.resources.GameResources;
import pize.tests.minecraftosp.main.Dir;
import pize.tests.minecraftosp.main.audio.BlockSoundPack;

public class Cactus extends Block{

    public Cactus(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        final float sideFaceTranslate = 1F / 16;

        final BlockModel model = new BlockModel(ChunkMeshType.CUSTOM)
                .face(new Face(Quad.getNxQuad().translate( sideFaceTranslate, 0, 0), resources.getBlockRegion("cactus_side")))
                .face(new Face(Quad.getPxQuad().translate(-sideFaceTranslate, 0, 0), resources.getBlockRegion("cactus_side")))
                .nyFace(resources.getBlockRegion("cactus_bottom"))
                .pyFace(resources.getBlockRegion("cactus_top"))
                .face(new Face(Quad.getNzQuad().translate(0, 0,  sideFaceTranslate), resources.getBlockRegion("cactus_side")))
                .face(new Face(Quad.getPzQuad().translate(0, 0, -sideFaceTranslate), resources.getBlockRegion("cactus_side")));

        newState()
                .setSolid(true)
                .setLightLevel(0)
                .setOpacity(0)
                .setTranslucent(false)
                .setSoundPack(BlockSoundPack.WOOD)
                .setFacing(Dir.NONE)
                .setModel(model)
                .setCollide(BlockCollide.CACTUS)
                .setCursor(BlockCursor.SOLID);
    }

}