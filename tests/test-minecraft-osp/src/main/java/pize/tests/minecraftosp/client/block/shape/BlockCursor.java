package pize.tests.minecraftosp.client.block.shape;

import pize.app.Disposable;
import pize.graphics.gl.BufferUsage;
import pize.graphics.gl.Primitive;
import pize.graphics.gl.Type;
import pize.graphics.mesh.Mesh;
import pize.graphics.mesh.VertexAttr;

public class BlockCursor implements Disposable{
    
    public final static BlockCursor SOLID = new SolidBlockCursor();
    public final static BlockCursor GRASS = new GrassBlockCursor();
    
    
    private final Mesh mesh;
    
    public BlockCursor(float[] vertices, int[] indices){
        this.mesh = new Mesh(new VertexAttr(3, Type.FLOAT));
        this.mesh.setRenderMode(Primitive.LINES);
        
        this.mesh.setVertices(vertices, BufferUsage.STATIC_DRAW);
        this.mesh.setIndices(indices, BufferUsage.STATIC_DRAW);
    }
    
    
    public void render(){
        mesh.render();
    }
    
    public Mesh getMesh(){
        return mesh;
    }
    
    @Override
    public void dispose(){
        mesh.dispose();
    }
    
}