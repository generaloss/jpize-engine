package jpize.graphics.buffer;

import jpize.gl.buffer.GlVertexBuffer;
import jpize.gl.type.GlType;
import jpize.gl.vertex.GlVertexAttr;

public class VertexBuffer extends GlVertexBuffer{

    private int vertexSize, vertexBytes;

    public VertexBuffer(){
        bind();
    }


    public void enableAttributes(GlVertexAttr... attributes){
        for(GlVertexAttr attribute: attributes){
            vertexSize += attribute.getCount();
            vertexBytes += attribute.getCount() * attribute.getType().getSize();
        }

        int pointer = 0;
        for(byte i = 0; i < attributes.length; i++){
            final GlVertexAttr attribute = attributes[i];
            
            final int count = attribute.getCount();
            final GlType type = attribute.getType();
            
            super.vertexAttribPointer(i, count, type, attribute.isNormalize(), vertexSize * type.getSize(), pointer);
            super.enableVertexAttribArray(i);

            pointer += count * type.getSize();
        }
    }

    public int getVertexSize(){
        return vertexSize;
    }
    
    public int getVertexBytes(){
        return vertexBytes;
    }

    public int getVertexCount(){
        return getSize() / vertexSize;
    }

}