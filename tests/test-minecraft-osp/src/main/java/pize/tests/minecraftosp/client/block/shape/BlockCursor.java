package pize.tests.minecraftosp.client.block.shape;

import pize.app.Disposable;
import pize.graphics.gl.Primitive;
import pize.graphics.gl.Type;
import pize.graphics.mesh.IndexedMesh;
import pize.graphics.mesh.VertexAttr;

public class BlockCursor implements Disposable{
    
    public final static BlockCursor SOLID = new SolidBlockCursor();
    public final static BlockCursor GRASS = new GrassBlockCursor();
    
    
    private final IndexedMesh mesh;
    
    public BlockCursor(float[] vertices, int[] indices){
        this.mesh = new IndexedMesh(new VertexAttr(3, Type.FLOAT));
        this.mesh.getBuffer().setData(vertices);
        this.mesh.getIndexBuffer().setData(indices);
        this.mesh.setMode(Primitive.LINES);
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
