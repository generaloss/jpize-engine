package pize.tests.minecraft.client.world.chunk.mesh;

import pize.context.Disposable;
import pize.graphics.gl.Type;
import pize.graphics.gl.Primitive;
import pize.graphics.vertex.*;
import pize.tests.minecraft.client.world.chunk.ChunkSection;
import pize.tests.minecraft.client.world.chunk.storage.SectionBlockContainer;

public class ChunkMesh implements Disposable{

    private final ChunkSection sectionOf;
    private final VertexArray vao;
    private final VertexBuffer vbo;
    private final ElementBuffer ebo;

    public ChunkMesh(ChunkSection sectionOf){
        this.sectionOf = sectionOf;
        vao = new VertexArray();
        vbo = new VertexBuffer();
        vbo.enableAttributes(new VertexAttr(3,Type.FLOAT));
        ebo = new ElementBuffer();
    }

    public void build(){
        SectionBlockContainer blocks = sectionOf.getBlocks();
    }

    public void render(int num){
        vao.drawElements(vbo.getVertexCount(),Primitive.TRIANGLES);
    }

    public VertexBuffer getVbo(){
        return vbo;
    }

    public ElementBuffer getEbo(){
        return ebo;
    }

    @Override
    public void dispose(){
        vao.dispose();
        vbo.dispose();
        ebo.dispose();
    }

}
