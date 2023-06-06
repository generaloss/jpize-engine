package pize.graphics.util.batch;

import pize.Pize;
import pize.files.Resource;
import pize.graphics.camera.Camera;
import pize.graphics.gl.BufferUsage;
import pize.graphics.gl.Type;
import pize.graphics.texture.Region;
import pize.graphics.texture.Texture;
import pize.graphics.texture.TextureRegion;
import pize.graphics.util.Shader;
import pize.graphics.util.batch.scissor.Scissor;
import pize.graphics.vertex.ElementBuffer;
import pize.graphics.vertex.VertexArray;
import pize.graphics.vertex.VertexAttr;
import pize.graphics.vertex.VertexBuffer;
import pize.math.vecmath.matrix.Matrix3f;
import pize.math.vecmath.matrix.Matrix4f;
import pize.math.vecmath.point.Point2f;
import pize.math.vecmath.tuple.Tuple2f;
import pize.math.vecmath.vector.Vec2f;

public class TextureBatch extends Batch{

    private final VertexBuffer vbo;
    private final VertexArray vao;
    private final ElementBuffer ebo;
    private final Shader shader;

    private final Vec2f transformOrigin;
    private final Matrix3f transformMatrix, rotationMatrix, shearMatrix, scaleMatrix, flipMatrix;

    private int size, vertexOffset;
    private final int batchSize;
    private final float[] vertices;

    private Texture lastTexture;
    private Matrix4f projectionMatrix, viewMatrix;
    private Shader customShader;
    
    private final Scissor scissor = new Scissor(this);

    public TextureBatch(){
        this(256);
    }

    public TextureBatch(int batchSize){
        this.batchSize = batchSize;

        // Shader
        shader = new Shader(
            new Resource("shader/batch/batch.vert").readString(),
            new Resource("shader/batch/batch.frag").readString()
        );

        { // Create VAO, VBO, EBO
            vao = new VertexArray();

            vbo = new VertexBuffer();
            vbo.enableAttributes(new VertexAttr(2, Type.FLOAT), new VertexAttr(2, Type.FLOAT), new VertexAttr(4, Type.FLOAT, false));

            ebo = new ElementBuffer();

            int[] indices = new int[QUAD_INDICES * batchSize];
            for(int i = 0; i < batchSize; i++){
                int indexQuadOffset = QUAD_INDICES * i;
                int vertexQuadOffset = QUAD_VERTICES * i;

                indices[indexQuadOffset    ] = vertexQuadOffset;
                indices[indexQuadOffset + 1] = vertexQuadOffset + 1;
                indices[indexQuadOffset + 2] = vertexQuadOffset + 2;

                indices[indexQuadOffset + 3] = vertexQuadOffset + 2;
                indices[indexQuadOffset + 4] = vertexQuadOffset + 3;
                indices[indexQuadOffset + 5] = vertexQuadOffset;
            }
            ebo.setData(indices, BufferUsage.STATIC_DRAW);
        }

        vertices = new float[QUAD_VERTICES * batchSize * vbo.getVertexSize()];

        transformOrigin = new Vec2f(0.5);
        transformMatrix = new Matrix3f();
        rotationMatrix = new Matrix3f();
        shearMatrix = new Matrix3f();
        scaleMatrix = new Matrix3f();
        flipMatrix = new Matrix3f();
    }


    @Override
    public void draw(Texture texture, float x, float y, float width, float height){
        if(size + 1 >= batchSize)
            end();

        if(texture != lastTexture){
            end();
            lastTexture = texture;
        }

        addTexturedQuad(x, y, width, height, 0, 0, 1, 1);
        size++;
    }

    @Override
    public void draw(TextureRegion texReg, float x, float y, float width, float height){
        if(size + 1 >= batchSize)
            end();
        
        final Texture texture = texReg.getTexture();
        if(texture != lastTexture){
            end();
            lastTexture = texture;
        }

        addTexturedQuad(x, y, width, height, texReg.u1f(), texReg.v1f(), texReg.u2f(), texReg.v2f());
        size++;
    }

    @Override
    public void draw(Texture texture, float x, float y, float width, float height, Region region){
        if(size + 1 >= batchSize)
            end();

        if(texture != lastTexture){
            end();
            lastTexture = texture;
        }

        addTexturedQuad(x, y, width, height, region.u1f(), region.v1f(), region.u2f(), region.v2f());
        size++;
    }

    @Override
    public void draw(TextureRegion texReg, float x, float y, float width, float height, Region region){
        if(size + 1 >= batchSize)
            end();

        final Texture texture = texReg.getTexture();
        if(texture != lastTexture){
            end();
            lastTexture = texture;
        }
        
        final Region regionInRegion = Region.calcRegionInRegion(texReg, region);

        addTexturedQuad(
            x, y, width, height,
            regionInRegion.u1f(),
            regionInRegion.v1f(),
            regionInRegion.u2f(),
            regionInRegion.v2f()
        );

        size++;
    }


    public void begin(Matrix4f projection, Matrix4f view){
        projectionMatrix = projection;
        viewMatrix = view;
    }

    public void begin(Camera camera){
        begin(camera.getProjection(), camera.getView());
    }

    public void begin(){
        if(viewMatrix == null) viewMatrix = new Matrix4f();
        if(projectionMatrix == null) projectionMatrix = new Matrix4f();

        begin(projectionMatrix.toOrthographic(0, 0, Pize.getWidth(), Pize.getHeight()), viewMatrix);
    }


    public int end(){
        if(lastTexture == null || size == 0)
            return -1;

        final Shader usedShader = customShader == null ? shader : customShader;

        usedShader.bind();
        usedShader.setUniform("u_projection", projectionMatrix);
        usedShader.setUniform("u_view", viewMatrix);
        usedShader.setUniform("u_texture", lastTexture);

        vbo.setData(vertices, BufferUsage.STREAM_DRAW);
        vao.drawElements(size * QUAD_INDICES);
        
        // Reset
        final int sizeResult = size;
        
        size = 0;
        vertexOffset = 0;
    
        return sizeResult;
    }
    
    public void useShader(Shader shader){
        customShader = shader;
    }
    

    private void addTexturedQuad(float x, float y, float width, float height, float u1, float v1, float u2, float v2){
        final Vec2f origin = new Vec2f(width * transformOrigin.x, height * transformOrigin.y);

        transformMatrix.set( rotationMatrix.getMul(scaleMatrix.getMul(shearMatrix.getMul(flipMatrix))) );

        final Tuple2f vertex1 = new Point2f(0,     0     ).sub(origin) .mul(transformMatrix) .add(origin).add(x, y);
        final Tuple2f vertex2 = new Point2f(width, 0     ).sub(origin) .mul(transformMatrix) .add(origin).add(x, y);
        final Tuple2f vertex3 = new Point2f(width, height).sub(origin) .mul(transformMatrix) .add(origin).add(x, y);
        final Tuple2f vertex4 = new Point2f(0,     height).sub(origin) .mul(transformMatrix) .add(origin).add(x, y);

        addVertex(vertex1.x, vertex1.y, u1, v2);
        addVertex(vertex2.x, vertex2.y, u2, v2);
        addVertex(vertex3.x, vertex3.y, u2, v1);
        addVertex(vertex4.x, vertex4.y, u1, v1);
    }

    private void addVertex(float x, float y, float s, float t){
        vertices[vertexOffset    ] = x;
        vertices[vertexOffset + 1] = y;

        vertices[vertexOffset + 2] = s;
        vertices[vertexOffset + 3] = t;

        vertices[vertexOffset + 4] = color.r();
        vertices[vertexOffset + 5] = color.g();
        vertices[vertexOffset + 6] = color.b();
        vertices[vertexOffset + 7] = color.a();

        vertexOffset += vbo.getVertexSize();
    }
    
    
    public Scissor getScissor(){
        return scissor;
    }


    @Override
    public Vec2f getTransformOrigin(){
        return transformOrigin;
    }

    @Override
    public void setTransformOrigin(double x, double y){
        transformOrigin.set(x, y);
    }

    @Override
    public void rotate(float angle){
        rotationMatrix.toRotated(angle);
    }

    @Override
    public void shear(float angleX, float angleY){
        shearMatrix.toSheared(angleX, angleY);
    }

    @Override
    public void scale(float scale){
        scaleMatrix.toScaled(scale);
    }

    @Override
    public void scale(float x, float y){
        scaleMatrix.toScaled(x, y);
    }

    @Override
    public void flip(boolean x, boolean y){
        flipMatrix.val[Matrix3f.m00] = y ? 1 : -1;
        flipMatrix.val[Matrix3f.m11] = x ? 1 : -1;
    }


    @Override
    public int size(){
        return size;
    }

    @Override
    public void dispose(){
        shader.dispose();
        vbo.dispose();
        vao.dispose();
        ebo.dispose();
    }

}
