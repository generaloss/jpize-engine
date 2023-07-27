package pize.tests.voxelgame.client.chunk.mesh;

public class ChunkMeshTranslucentCullingOn extends ChunkMeshPackedCullingOn{
    
    @Override
    public ChunkMeshType getType(){
        return ChunkMeshType.TRANSLUCENT;
    }

}
