package pize.tests.voxelgame.client.chunk.mesh;

import pize.Pize;
import pize.graphics.gl.BufferUsage;

public class ChunkMeshStack{
    
    private ChunkSolidMesh solid;
    private ChunkCustomMesh custom;
    
    public ChunkMeshStack(){
        Pize.execSync(()->{
            solid = new ChunkSolidMesh();
            custom = new ChunkCustomMesh();
        });
    }
    
    public void setDataSolid(int[] data){
        if(solid == null && data.length == 0)
            return;
        
        solid.getBuffer().setData(data, BufferUsage.STATIC_DRAW);
    }
    
    public void setDataCustom(float[] data){
        if(custom == null && data.length == 0)
            return;
        
        custom.getBuffer().setData(data, BufferUsage.STATIC_DRAW);
    }
    
    
    public ChunkSolidMesh getSolid(){
        return solid;
    }
    
    public ChunkCustomMesh getCustom(){
        return custom;
    }
    
    
    public void dispose(){
        if(solid != null)
            solid.dispose();

        if(custom != null)
            custom.dispose();
    }
    
}
