package pize.tests.voxelgame.client.chunk.mesh;

import pize.app.Disposable;

public class ChunkMeshStack implements Disposable{
    
    private final ChunkMeshPackedCullingOn packed;
    private final ChunkMeshCullingOff custom;
    private final ChunkMeshTranslucentCullingOn translucent;

    public ChunkMeshStack(){
        packed = new ChunkMeshPackedCullingOn();
        custom = new ChunkMeshCullingOff();
        translucent = new ChunkMeshTranslucentCullingOn();
    }
    
    public ChunkMeshPackedCullingOn getPacked(){
        return packed;
    }
    
    public ChunkMeshCullingOff getCustom(){
        return custom;
    }

    public ChunkMeshTranslucentCullingOn getTranslucent(){
        return translucent;
    }

    @Override
    public void dispose(){
        packed.dispose();
        custom.dispose();
        translucent.dispose();
    }
    
}
