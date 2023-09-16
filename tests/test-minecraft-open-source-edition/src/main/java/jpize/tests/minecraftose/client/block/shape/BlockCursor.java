package jpize.tests.minecraftose.client.block.shape;

import jpize.util.Disposable;
import jpize.gl.tesselation.GlPrimitive;
import jpize.gl.type.GlType;
import jpize.graphics.mesh.IndexedMesh;
import jpize.gl.vertex.GlVertexAttr;

public class BlockCursor implements Disposable{
    
    public final static BlockCursor SOLID = new SolidBlockCursor();
    public final static BlockCursor GRASS = new GrassBlockCursor();
    
    
    private final IndexedMesh mesh;
    
    public BlockCursor(float[] vertices, int[] indices){
        this.mesh = new IndexedMesh(new GlVertexAttr(3, GlType.FLOAT));
        this.mesh.getBuffer().setData(vertices);
        this.mesh.getIndexBuffer().setData(indices);
        this.mesh.setMode(GlPrimitive.LINES);
    }
    
    
    public void render(){
        mesh.render();
    }
    
    public IndexedMesh getMesh(){
        return mesh;
    }
    
    @Override
    public void dispose(){
        mesh.dispose();
    }
    
}
