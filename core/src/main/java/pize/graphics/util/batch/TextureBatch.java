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
import pize.graphics.util.TextureUtils;
import pize.graphics.util.batch.scissor.Scissor;
import pize.graphics.util.color.IColor;
import pize.graphics.mesh.ElementBuffer;
import pize.graphics.mesh.VertexArray;
import pize.graphics.mesh.VertexAttr;
import pize.graphics.mesh.VertexBuffer;
import pize.math.vecmath.matrix.Matrix3f;
import pize.math.vecmath.matrix.Matrix4f;
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

        addTexturedQuad(x, y, width, height, 0, 0, 1, 1, color.r(), color.g(), color.b(), color.a());
        size++;
    }
    
    public void draw(Texture texture, float x, float y, float width, float height, float r, float g, float b, float a){
        if(size + 1 >= batchSize)
            end();
        
        if(texture != lastTexture){
            end();
            lastTexture = texture;
        }
        
        addTexturedQuad(x, y, width, height, 0, 0, 1, 1, r, g, b, a);
        size++;
    }
    
    public void drawQuad(double r, double g, double b, double a, float x, float y, float width, float height){
        draw(TextureUtils.quadTexture(), x, y, width, height, (float) r, (float) g, (float) b, (float) a);
    }
    
    public void drawQuad(IColor color, float x, float y, float width, float height){
        drawQuad(color.r(), color.g(), color.b(), color.a(), x, y, width, height);
    }
    
    public void drawQuad(double alpha, float x, float y, float width, float height){
        drawQuad(0, 0, 0, alpha, x, y, width, height);
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

        addTexturedQuad(x, y, width, height, texReg.u1(), texReg.v1(), texReg.u2(), texReg.v2(), color.r(), color.g(), color.b(), color.a());
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

        addTexturedQuad(x, y, width, height, region.u1(), region.v1(), region.u2(), region.v2(), color.r(), color.g(), color.b(), color.a());
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
            regionInRegion.u1(),
            regionInRegion.v1(),
            regionInRegion.u2(),
            regionInRegion.v2(),
            color.r(),
            color.g(),
            color.b(),
            color.a()
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
    

    private void addTexturedQuad(float x, float y, float width, float height, float u1, float v1, float u2, float v2, float r, float g, float b, float a){
        final Vec2f origin = new Vec2f(width * transformOrigin.x, height * transformOrigin.y);

        transformMatrix.set( rotationMatrix.getMul(scaleMatrix.getMul(shearMatrix.getMul(flipMatrix))) );

        final Vec2f vertex1 = new Vec2f(0,     0     ).sub(origin) .mul(transformMatrix) .add(origin).add(x, y);
        final Vec2f vertex2 = new Vec2f(width, 0     ).sub(origin) .mul(transformMatrix) .add(origin).add(x, y);
        final Vec2f vertex3 = new Vec2f(width, height).sub(origin) .mul(transformMatrix) .add(origin).add(x, y);
        final Vec2f vertex4 = new Vec2f(0,     height).sub(origin) .mul(transformMatrix) .add(origin).add(x, y);

        addVertex(vertex1.x, vertex1.y, u1, v2, r, g, b, a);
        addVertex(vertex2.x, vertex2.y, u2, v2, r, g, b, a);
        addVertex(vertex3.x, vertex3.y, u2, v1, r, g, b, a);
        addVertex(vertex4.x, vertex4.y, u1, v1, r, g, b, a);
    }

    private void addVertex(float x, float y, float s, float t, float r, float g, float b, float a){
        vertices[vertexOffset    ] = x;
        vertices[vertexOffset + 1] = y;

        vertices[vertexOffset + 2] = s;
        vertices[vertexOffset + 3] = t;

        vertices[vertexOffset + 4] = r;
        vertices[vertexOffset + 5] = g;
        vertices[vertexOffset + 6] = b;
        vertices[vertexOffset + 7] = a;

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
