package jpize.graphics.util.batch;

import jpize.Jpize;
import jpize.files.Resource;
import jpize.gl.type.GlType;
import jpize.gl.vertex.GlVertexAttr;
import jpize.graphics.camera.Camera;
import jpize.graphics.mesh.QuadMesh;
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
import jpize.util.Disposable;

import static jpize.graphics.buffer.QuadIndexBuffer.QUAD_INDICES;
import static jpize.graphics.buffer.QuadIndexBuffer.QUAD_VERTICES;

public class TextureBatch implements Disposable{

    // Tesselation
    private final QuadMesh mesh;
    private final Shader shader;
    private final Color color;
    private Texture lastTexture;
    // Custom
    private Matrix4f projectionMat, viewMat;
    private Shader customShader;
    // Data
    private final int maxSize, vertexBytes;
    private int size, vertexBufferOffset;
    public final float[] vertexData;
    // Transform
    private final Vec2f transformOrigin;
    private final Matrix3f transformMat, rotationMat, shearMat, scaleMat, flipMat;
    private final Scissor scissor;

    public TextureBatch(int maxSize){
        this.maxSize = maxSize;
        this.color = new Color();
        this.scissor = new Scissor(this);
        this.transformOrigin = new Vec2f(0.5);

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

        // Get vertex size
        final int vertexSize = mesh.getBuffer().getVertexSize();
        this.vertexBytes = mesh.getBuffer().getVertexBytes();

        // Allocate buffers
        this.vertexData = new float[vertexSize];
        this.mesh.getBuffer().allocateData(QUAD_VERTICES * maxSize * vertexBytes);

        // Matrices
        this.transformMat = new Matrix3f();
        this.rotationMat = new Matrix3f();
        this.shearMat = new Matrix3f();
        this.scaleMat = new Matrix3f();
        this.flipMat = new Matrix3f();
    }

    public TextureBatch(){
        this(1024);
    }


    private void addVertex(float x, float y, float s, float t, float r, float g, float b, float a){
        vertexData[0] = x;
        vertexData[1] = y;

        vertexData[2] = s;
        vertexData[3] = t;

        vertexData[4] = r;
        vertexData[5] = g;
        vertexData[6] = b;
        vertexData[7] = a;

        mesh.getBuffer().setSubData(vertexBufferOffset, vertexData);
        vertexBufferOffset += vertexBytes;
    }

    private void addTexturedQuad(float x, float y, float width, float height, float u1, float v1, float u2, float v2, float r, float g, float b, float a){
        final Vec2f origin = new Vec2f(width * transformOrigin.x, height * transformOrigin.y);

        transformMat.set( rotationMat.getMul(scaleMat.getMul(shearMat.getMul(flipMat))) );

        final Vec2f vertex1 = new Vec2f(0,     0     ).sub(origin) .mul(transformMat) .add(origin).add(x, y);
        final Vec2f vertex2 = new Vec2f(width, 0     ).sub(origin) .mul(transformMat) .add(origin).add(x, y);
        final Vec2f vertex3 = new Vec2f(width, height).sub(origin) .mul(transformMat) .add(origin).add(x, y);
        final Vec2f vertex4 = new Vec2f(0,     height).sub(origin) .mul(transformMat) .add(origin).add(x, y);

        addVertex(vertex1.x, vertex1.y, u1, v2, r, g, b, a);
        addVertex(vertex2.x, vertex2.y, u2, v2, r, g, b, a);
        addVertex(vertex3.x, vertex3.y, u2, v1, r, g, b, a);
        addVertex(vertex4.x, vertex4.y, u1, v1, r, g, b, a);
    }


    public void begin(Matrix4f projection, Matrix4f view){
        this.projectionMat = projection;
        this.viewMat = view;
    }

    public void begin(Camera camera){
        begin(camera.getProjection(), camera.getView());
    }

    public void begin(){
        if(viewMat == null) viewMat = new Matrix4f();
        if(projectionMat == null) projectionMat = new Matrix4f();

        begin(projectionMat.toOrthographic(0, 0, Jpize.getWidth(), Jpize.getHeight()), viewMat);
    }

    public void end(){
        flush();
    }

    private void flush(){
        if(lastTexture == null || size == 0 || projectionMat == null)
            return;

        // Shader
        final Shader usedShader = (customShader != null) ? customShader : shader;

        usedShader.bind();
        usedShader.setUniform("u_projection", projectionMat);
        usedShader.setUniform("u_view", viewMat);
        usedShader.setUniform("u_texture", lastTexture);

        // Render
        mesh.render(size * QUAD_INDICES);
        System.out.println(" flush: " + size);

        // Reset
        size = 0;
        vertexBufferOffset = 0;
    }

    public void useShader(Shader shader){
        customShader = shader;
    }


    public void draw(Texture texture, float x, float y, float width, float height){
        if(size == maxSize)
            flush();

        if(texture != lastTexture){
            flush();
            lastTexture = texture;
        }

        addTexturedQuad(x, y, width, height, 0, 0, 1, 1, color.r(), color.g(), color.b(), color.a());

        size++;
    }
    
    public void draw(TextureRegion textureRegion, float x, float y, float width, float height){
        if(size == maxSize)
            flush();

        final Texture texture = textureRegion.getTexture();
        if(texture != lastTexture){
            flush();
            lastTexture = texture;
        }

        addTexturedQuad(x, y, width, height,
                textureRegion.u1(), textureRegion.v1(), textureRegion.u2(), textureRegion.v2(),
                color.r(), color.g(), color.b(), color.a());

        size++;
    }

    public void draw(Texture texture, float x, float y, float width, float height, Region region){
        if(size == maxSize)
            flush();

        if(texture != lastTexture){
            flush();
            lastTexture = texture;
        }

        addTexturedQuad(x, y, width, height,
                region.u1(), region.v1(), region.u2(), region.v2(),
                color.r(), color.g(), color.b(), color.a());

        size++;
    }

    public void draw(TextureRegion texReg, float x, float y, float width, float height, Region region){
        if(size == maxSize)
            flush();

        final Texture texture = texReg.getTexture();
        if(texture != lastTexture){
            flush();
            lastTexture = texture;
        }

        final Region regionInRegion = Region.calcRegionInRegion(texReg, region);

        addTexturedQuad(x, y, width, height,
            regionInRegion.u1(), regionInRegion.v1(), regionInRegion.u2(), regionInRegion.v2(),
            color.r(), color.g(), color.b(), color.a());

        size++;
    }

    public void draw(Texture texture, float x, float y, float width, float height, float r, float g, float b, float a){
        if(size == maxSize)
            flush();

        if(texture != lastTexture){
            flush();
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


    public int size(){
        return size;
    }


    public Scissor getScissor(){
        return scissor;
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

    public void setColor(double r, double g, double b){
        color.set(r, g, b, 1F);
    }

    public Color getColor(){
        return color;
    }

    public void setAlpha(double a){
        color.setA((float) a);
    }


    public Vec2f getTransformOrigin(){
        return transformOrigin;
    }

    public void setTransformOrigin(double x, double y){
        transformOrigin.set(x, y);
    }

    public void rotate(float angle){
        rotationMat.toRotated(angle);
    }

    public void shear(float angleX, float angleY){
        shearMat.toSheared(angleX, angleY);
    }

    public void scale(float scale){
        scaleMat.toScaled(scale);
    }

    public void scale(float x, float y){
        scaleMat.toScaled(x, y);
    }

    public void flip(boolean x, boolean y){
        flipMat.val[Matrix3f.m00] = y ? 1 : -1;
        flipMat.val[Matrix3f.m11] = x ? 1 : -1;
    }


    @Override
    public void dispose(){
        shader.dispose();
        mesh.dispose();
    }

}
