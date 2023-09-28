package jpize.tests.mcose.client.block.vanilla;

import jpize.graphics.texture.Region;
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
import jpize.tests.mcose.main.chunk.ChunkUtils;

public class OakPlanksStairs extends Block{

    public OakPlanksStairs(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        final Region region = resources.getBlockRegion("oak_planks");
        final Region halfRegion = region.copy().extrude(1, 0.5F);
        final Region quarterRegion = region.copy().extrude(0.5F, 0.5F);

        final BlockModel model = new BlockModel(ChunkMeshType.CUSTOM)
            .setFacesTransparentForNeighbors(false, true, true, true, false, true)
            .nyFace(region)
            .pzFace(region)
            .face(new Face(Quad.getNzQuad().scale(1, 0.5, 1), halfRegion))
            .face(new Face(Quad.getNxQuad().scale(1, 0.5, 1), halfRegion))
            .face(new Face(Quad.getPxQuad().scale(1, 0.5, 1), halfRegion))
            .face(new Face(Quad.getNxQuad().scale(1, 0.5, 0.5).translate(0, 0.5F, 0.5F), quarterRegion))
            .face(new Face(Quad.getPxQuad().scale(1, 0.5, 0.5).translate(0, 0.5F, 0.5F), quarterRegion));

        newState()
            .setSolid(false)
            .setLightLevel(0)
            .setOpacity(ChunkUtils.MAX_LIGHT_LEVEL)
            .setTranslucent(false)
            .setSoundPack(BlockSoundPack.WOOD)
            .setFacing(Dir.NEGATIVE_Z)
            .setModel(model)
            .setCollide(BlockCollide.SOLID)
            .setCursor(BlockCursor.SOLID);

    }

}
