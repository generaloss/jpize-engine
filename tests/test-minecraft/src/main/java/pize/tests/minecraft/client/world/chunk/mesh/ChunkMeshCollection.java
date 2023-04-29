package pize.tests.minecraft.client.world.chunk.mesh;

import pize.context.Disposable;
import pize.tests.minecraft.client.world.chunk.ChunkSection;

public class ChunkMeshCollection implements Disposable{

    private final ChunkMesh solid;
    private final ChunkMesh water;

    public ChunkMeshCollection(ChunkSection sectionOf){
        solid = new ChunkMesh(sectionOf);
        water = new ChunkMesh(sectionOf);
    }

    public void generateMeshes(){

    }

    public ChunkMesh getSolid(){
        return solid;
    }

    public ChunkMesh getWater(){
        return water;
    }

    @Override
    public void dispose(){
        solid.dispose();
        water.dispose();
    }

}
