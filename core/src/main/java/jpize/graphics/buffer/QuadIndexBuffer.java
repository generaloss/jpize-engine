package jpize.graphics.buffer;

import jpize.gl.buffer.GlBufUsage;
import jpize.gl.type.GlType;

public class QuadIndexBuffer extends IndexBuffer{

    public static int QUAD_INDICES = 6;
    public static int QUAD_VERTICES = 4;

    private int maxQuads;

    public QuadIndexBuffer(int maxQuads, GlBufUsage bufferUsage){
        super.setDefaultUsage(bufferUsage);
        setMaxQuads(maxQuads, bufferUsage);
    }

    public QuadIndexBuffer(int maxQuads){
        this(maxQuads, GlBufUsage.STATIC_DRAW);
    }

    public void setMaxQuads(int maxQuads, GlBufUsage bufferUsage){
        this.maxQuads = maxQuads;

        // Allocate GL buffer
        final int typeSize = GlType.INT.getSize();
        super.allocateData(maxQuads * QUAD_INDICES * typeSize, bufferUsage);
        final int[] quadIndices = new int[QUAD_INDICES];

        // Set data
        for(int i = 0; i < maxQuads; i++){
            final int indexQuadOffset = QUAD_INDICES * i * typeSize;
            final int vertexQuadOffset = QUAD_VERTICES * i;

            // Triangle 1
            quadIndices[0] = vertexQuadOffset;
            quadIndices[1] = vertexQuadOffset + 1;
            quadIndices[2] = vertexQuadOffset + 2;
            // Triangle 2
            quadIndices[3] = vertexQuadOffset + 2;
            quadIndices[4] = vertexQuadOffset + 3;
            quadIndices[5] = vertexQuadOffset;

            super.setSubData(indexQuadOffset, quadIndices);
        }
    }

    public int getMaxQuads(){
        return maxQuads;
    }
    
}
