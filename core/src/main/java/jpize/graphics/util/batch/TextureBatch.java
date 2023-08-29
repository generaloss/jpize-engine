package jpize.graphics.util.batch;

import jpize.Jpize;
import jpize.util.Disposable;
import jpize.files.Resource;
import jpize.graphics.camera.Camera;
import jpize.gl.buffer.GlBufUsage;
import jpize.gl.type.GlType;
import jpize.graphics.mesh.QuadMesh;
import jpize.gl.vertex.GlVertexAttr;
import jpize.graphics.texture.Region;
import jpize.graphics.texture.Texture;
import jpize.graphics.texture.TextureRegion;
import jpize.graphics.util.Shader;
import jpize.graphics.util.TextureUtils;
import jpize.graphics.util.batch.scissor.Scissor;
import jpize.graphics.util.color.Color;
import jpize.graphics.util.color.IColor;
import jpize.math.vecmath.matrix.Matrix3f;
import jpize.math.vecmath.matrix.Matrix4f;
import jpize.math.vecmath.vector.Vec2f;

import static jpize.graphics.mesh.QuadIndexBuffer.QUAD_INDICES;
import static jpize.graphics.mesh.QuadIndexBuffer.QUAD_VERTICES;

public class TextureBatch implements Disposable{

    private final QuadMesh mesh;
    private final Shader shader;
    private final Color color;

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

    public TextureBatch(int maxSize){
        this.batchSize = maxSize;

        // Shader
        this.shader = new Shader(
            new Resource("shader/batch/batch.vert").readString(),
            new Resource("shader/batch/batch.frag").readString()
        );

        // Mesh
        this.mesh = new QuadMesh(
                maxSize,
                new GlVertexAttr(2, GlType.FLOAT),
                new GlVertexAttr(2, GlType.FLOAT),
                new GlVertexAttr(4, GlType.FLOAT)
        );

        this.vertices = new float[QUAD_VERTICES * maxSize * mesh.getBuffer().getVertexSize()];

        this.color = new Color();
        this.transformOrigin = new Vec2f(0.5);
        this.transformMatrix = new Matrix3f();
        this.rotationMatrix = new Matrix3f();
        this.shearMatrix = new Matrix3f();
        this.scaleMatrix = new Matrix3f();
        this.flipMatrix = new Matrix3f();
    }
    
    
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

        begin(projectionMatrix.toOrthographic(0, 0, Jpize.getWidth(), Jpize.getHeight()), viewMatrix);
    }


    public void end(){
        if(lastTexture == null || size == 0)
            return;

        // Shader
        final Shader usedShader = customShader == null ? shader : customShader;

        usedShader.bind();
        usedShader.setUniform("u_projection", projectionMatrix);
        usedShader.setUniform("u_view", viewMatrix);
        usedShader.setUniform("u_texture", lastTexture);

        // Render
        mesh.getBuffer().setData(vertices, GlBufUsage.STREAM_DRAW);
        mesh.render(size * QUAD_INDICES);

        // Reset
        size = 0;
        vertexOffset = 0;
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

        vertexOffset += mesh.getBuffer().getVertexSize();
    }
    
    
    public Scissor getScissor(){
        return scissor;
    }


    public Vec2f getTransformOrigin(){
        return transformOrigin;
    }

    public void setTransformOrigin(double x, double y){
        transformOrigin.set(x, y);
    }

    public void rotate(float angle){
        rotationMatrix.toRotated(angle);
    }

    public void shear(float angleX, float angleY){
        shearMatrix.toSheared(angleX, angleY);
    }

    public void scale(float scale){
        scaleMatrix.toScaled(scale);
    }

    public void scale(float x, float y){
        scaleMatrix.toScaled(x, y);
    }

    public void flip(boolean x, boolean y){
        flipMatrix.val[Matrix3f.m00] = y ? 1 : -1;
        flipMatrix.val[Matrix3f.m11] = x ? 1 : -1;
    }


    public void resetColor(){
        color.set(1, 1, 1, 1F);
    }

    public void setColor(IColor color){
        this.color.set(color);
    }

    public void setColor(double r, double g, double b, double a){
        color.set(r, g, b, a);
    }

    public Color getColor(){
        return color;
    }

    public void setAlpha(double a){
        color.setA((float) a);
    }


    public int size(){
        return size;
    }

    @Override
    public void dispose(){
        shader.dispose();
        mesh.dispose();
    }

}
