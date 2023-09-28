package jpize.tests.mcose.client.block.vanilla;

import jpize.graphics.util.color.Color;
import jpize.tests.mcose.client.block.Block;
import jpize.tests.mcose.client.block.model.BlockModel;
import jpize.tests.mcose.client.block.model.Face;
import jpize.tests.mcose.client.block.model.Quad;
import jpize.tests.mcose.client.block.shape.BlockCollide;
import jpize.tests.mcose.client.block.shape.BlockCursor;
import jpize.tests.mcose.client.chunk.mesh.ChunkMeshType;
import jpize.tests.mcose.client.resources.GameResources;
import jpize.tests.mcose.main.Dir;
import jpize.tests.mcose.main.audio.BlockSoundPack;

public class Cactus extends Block{

    public Cactus(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        final float sideFaceTranslate = 1F / 16;

        final BlockModel model = new BlockModel(ChunkMeshType.CUSTOM)
                .face(new Face(Quad.getNxQuad().translate( sideFaceTranslate, 0, 0), resources.getBlockRegion("cactus_side")).color(0.8))
                .face(new Face(Quad.getPxQuad().translate(-sideFaceTranslate, 0, 0), resources.getBlockRegion("cactus_side")).color(0.8))
                .nyFace(resources.getBlockRegion("cactus_bottom"), new Color(0.6, 0.6, 0.6))
                .pyFace(resources.getBlockRegion("cactus_top"))
                .face(new Face(Quad.getNzQuad().translate(0, 0,  sideFaceTranslate), resources.getBlockRegion("cactus_side")).color(0.7))
                .face(new Face(Quad.getPzQuad().translate(0, 0, -sideFaceTranslate), resources.getBlockRegion("cactus_side")).color(0.7));

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