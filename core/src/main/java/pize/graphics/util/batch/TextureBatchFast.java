package pize.graphics.util.batch;

import pize.Pize;
import pize.files.Resource;
import pize.graphics.camera.Camera;
import pize.graphics.gl.BufUsage;
import pize.graphics.gl.Type;
import pize.graphics.mesh.*;
import pize.graphics.texture.Region;
import pize.graphics.texture.Texture;
import pize.graphics.texture.TextureRegion;
import pize.graphics.util.Shader;
import pize.math.vecmath.matrix.Matrix3f;
import pize.math.vecmath.matrix.Matrix4f;
import pize.math.vecmath.vector.Vec2f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL33.GL_MAX_TEXTURE_IMAGE_UNITS;
import static org.lwjgl.opengl.GL33.glGetInteger;
import static pize.graphics.mesh.QuadIndexBuffer.QUAD_INDICES;
import static pize.graphics.mesh.QuadIndexBuffer.QUAD_VERTICES;

public class TextureBatchFast extends Batch{

    public static int MAX_TEXTURE_SLOTS = glGetInteger(GL_MAX_TEXTURE_IMAGE_UNITS);

    private final GlVbo vbo;
    private final GlVao vao;
    private final QuadIndexBuffer ebo;
    private final Shader shader;

    private final Vec2f transformOrigin;
    private final Matrix3f transformMatrix, rotationMatrix, shearMatrix, scaleMatrix, flipMatrix;

    private int size, vertexOffset;
    private final int maxSize;
    private final float[] vertices;

    private final int[] texSlots;
    private final List<Texture> textures;

    private Matrix4f defaultView, defaultProjection;


    public TextureBatchFast(int maxSize, int maxTextures, boolean roundPosition){
        this.maxSize = maxSize;
        maxTextures = Math.min(Math.max(2, maxTextures), MAX_TEXTURE_SLOTS);

        // Shader

        String vs = new Resource("shader/batch/fast_batch.vert").readString().replace(
            "POS_FUNC", roundPosition ? "vec4(round(pos.x), round(pos.y), 0, pos.w)" : "pos"
        );

        String fs = new Resource("shader/batch/fast_batch.frag").readString().replace(
            "TEX_SLOTS", Integer.toString(maxTextures)
        );

        this.shader = new Shader(vs, fs);

        // Create VAO, VBO, EBO
        this.vao = new GlVao();
        this.vbo = new GlVbo();
        this.vbo.enableAttributes(new VertexAttr(2, Type.FLOAT), new VertexAttr(2, Type.FLOAT), new VertexAttr(4, Type.FLOAT, true), new VertexAttr(1, Type.FLOAT));
        this.ebo = new QuadIndexBuffer(maxSize);

        this.texSlots = new int[maxTextures];
        for(int i = 0; i < maxTextures; i++)
            this.texSlots[i] = i;

        this.vertices = new float[QUAD_VERTICES * maxSize * vbo.getVertexSize()];
        this.textures = new ArrayList<>();

        this.transformOrigin = new Vec2f(0.5);
        this.transformMatrix = new Matrix3f();
        this.rotationMatrix = new Matrix3f();
        this.shearMatrix = new Matrix3f();
        this.scaleMatrix = new Matrix3f();
        this.flipMatrix = new Matrix3f();
    }


    @Override
    public void draw(Texture texture, float x, float y, float width, float height){
        if(size + 1 >= maxSize)
            return;
        size++;

        if(!textures.contains(texture))
            textures.add(texture);

        addTexturedQuad(x, y, width, height, 0, 0, 1, 1, getTextureIndex(texture));
    }

    @Override
    public void draw(TextureRegion texReg, float x, float y, float width, float height){
        if(size + 1 >= maxSize)
            return;
        size++;

        Texture texture = texReg.getTexture();
        if(!textures.contains(texture))
            textures.add(texture);

        addTexturedQuad(x, y, width, height, texReg.u1(), texReg.v1(), texReg.u2(), texReg.v2(), getTextureIndex(texture));
    }

    @Override
    public void draw(Texture texture, float x, float y, float width, float height, Region region){
        if(size + 1 >= maxSize)
            return;
        size++;

        if(!textures.contains(texture))
            textures.add(texture);

        addTexturedQuad(x, y, width, height, region.u1(), region.v1(), region.u2(), region.v2(), getTextureIndex(texture));
    }

    @Override
    public void draw(TextureRegion texReg, float x, float y, float width, float height, Region region){
        if(size + 1 >= maxSize)
            return;
        size++;

        Texture texture = texReg.getTexture();
        if(!textures.contains(texture))
            textures.add(texture);

        Region regionInRegion = Region.calcRegionInRegion(texReg, region);

        addTexturedQuad(
            x, y, width, height,
            regionInRegion.u1(),
            regionInRegion.v1(),
            regionInRegion.u2(),
            regionInRegion.v2(),
            getTextureIndex(texture)
        );
    }


    public void end(Matrix4f projection, Matrix4f view){
        shader.bind();
        shader.setUniform("u_projection", projection);
        shader.setUniform("u_view", view);
        shader.setUniform("u_textures", texSlots); // Need a Uniform Buffer !!!

        for(int i = 0; i < textures.size(); i++)
            textures.get(i).bind(i + 1);

        vbo.setData(vertices, BufUsage.STREAM_DRAW);
        vao.drawElements(size * QUAD_INDICES);

        // Reset
        size = 0;
        vertexOffset = 0;
        textures.clear();
    }

    public void end(Camera cam){
        end(cam.getProjection(), cam.getView());
    }

    public void end(){
        if(defaultView == null) defaultView = new Matrix4f();
        if(defaultProjection == null) defaultProjection = new Matrix4f();

        end(defaultProjection.toOrthographic(0, 0, Pize.getWidth(), Pize.getHeight()), defaultView);
    }


    private int getTextureIndex(Texture texture){
        int texIndex = 0;

        for(int i = 0; i < textures.size(); i++)
            if(textures.get(i).equals(texture)){
                texIndex = i + 1;
                break;
            }

        return texIndex;
    }

    private void addTexturedQuad(float x, float y, float width, float height, float u1, float v1, float u2, float v2, int texIndex){
        Vec2f origin = new Vec2f(width * transformOrigin.x, height * transformOrigin.y);

        transformMatrix.set( rotationMatrix.getMul(scaleMatrix.getMul(shearMatrix.getMul(flipMatrix))) );

        Vec2f vertex1 = new Vec2f(0,     0     ).sub(origin) .mul(transformMatrix) .add(origin).add(x, y);
        Vec2f vertex2 = new Vec2f(width, 0     ).sub(origin) .mul(transformMatrix) .add(origin).add(x, y);
        Vec2f vertex3 = new Vec2f(width, height).sub(origin) .mul(transformMatrix) .add(origin).add(x, y);
        Vec2f vertex4 = new Vec2f(0,     height).sub(origin) .mul(transformMatrix) .add(origin).add(x, y);

        addVertex(vertex1.x, vertex1.y, u1, v2, texIndex);
        addVertex(vertex2.x, vertex2.y, u2, v2, texIndex);
        addVertex(vertex3.x, vertex3.y, u2, v1, texIndex);
        addVertex(vertex4.x, vertex4.y, u1, v1, texIndex);
    }

    private void addVertex(float x, float y, float s, float t, int texIndex){
        vertices[vertexOffset    ] = x;
        vertices[vertexOffset + 1] = y;

        vertices[vertexOffset + 2] = s;
        vertices[vertexOffset + 3] = t;

        vertices[vertexOffset + 4] = color.r();
        vertices[vertexOffset + 5] = color.g();
        vertices[vertexOffset + 6] = color.b();
        vertices[vertexOffset + 7] = color.a();

        vertices[vertexOffset + 8] = texIndex;

        vertexOffset += vbo.getVertexSize();
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