package glit.graphics.util.batch;

import glit.Glit;
import glit.files.FileHandle;
import glit.graphics.camera.Camera;
import glit.graphics.gl.BufferUsage;
import glit.graphics.gl.Type;
import glit.graphics.texture.Region;
import glit.graphics.texture.Texture;
import glit.graphics.texture.TextureRegion;
import glit.graphics.util.Shader;
import glit.graphics.vertex.ElementBuffer;
import glit.graphics.vertex.VertexArray;
import glit.graphics.vertex.VertexAttr;
import glit.graphics.vertex.VertexBuffer;
import glit.math.vecmath.matrix.Matrix3f;
import glit.math.vecmath.matrix.Matrix4f;
import glit.math.vecmath.point.Point2f;
import glit.math.vecmath.tuple.Tuple2f;
import glit.math.vecmath.vector.Vec2f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL33.GL_MAX_TEXTURE_IMAGE_UNITS;
import static org.lwjgl.opengl.GL33.glGetInteger;

public class TextureBatchFast extends Batch{

    public static int MAX_TEXTURE_SLOTS = glGetInteger(GL_MAX_TEXTURE_IMAGE_UNITS);

    private final VertexBuffer vbo;
    private final VertexArray vao;
    private final ElementBuffer ebo;
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

        String vs = new FileHandle("shader/batch/batch.vert").readString().replace(
            "POS_FUNC", roundPosition ? "vec4(round(pos.x), round(pos.y), 0, pos.w)" : "pos"
        );

        String fs = new FileHandle("shader/batch/batch.frag").readString().replace(
            "TEX_SLOTS", Integer.toString(maxTextures)
        );

        shader = new Shader(vs, fs);

        { // Create VAO, VBO, EBO
            vao = new VertexArray();

            vbo = new VertexBuffer();
            vbo.enableAttributes(new VertexAttr(2, Type.FLOAT), new VertexAttr(2, Type.FLOAT), new VertexAttr(4, Type.FLOAT, true), new VertexAttr(1, Type.FLOAT));

            ebo = new ElementBuffer();

            int[] indices = new int[QUAD_INDICES * maxSize];
            for(int i = 0; i < maxSize; i++){
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

        this.texSlots = new int[maxTextures];
        for(int i = 0; i < maxTextures; i++)
            this.texSlots[i] = i;

        vertices = new float[QUAD_VERTICES * maxSize * vbo.getVertexSize()];
        textures = new ArrayList<>();

        transformOrigin = new Vec2f(0.5);
        transformMatrix = new Matrix3f();
        rotationMatrix = new Matrix3f();
        shearMatrix = new Matrix3f();
        scaleMatrix = new Matrix3f();
        flipMatrix = new Matrix3f();
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

        addTexturedQuad(x, y, width, height, texReg.u1f(), texReg.v1f(), texReg.u2f(), texReg.v2f(), getTextureIndex(texture));
    }

    @Override
    public void draw(Texture texture, float x, float y, float width, float height, Region region){
        if(size + 1 >= maxSize)
            return;
        size++;

        if(!textures.contains(texture))
            textures.add(texture);

        addTexturedQuad(x, y, width, height, region.u1f(), region.v1f(), region.u2f(), region.v2f(), getTextureIndex(texture));
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
            regionInRegion.u1f(),
            regionInRegion.v1f(),
            regionInRegion.u2f(),
            regionInRegion.v2f(),
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

        vbo.setData(vertices, BufferUsage.STREAM_DRAW);
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

        end(defaultProjection.toOrthographic(0, 0, Glit.getWidth(), Glit.getHeight()), defaultView);
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

        Tuple2f vertex1 = new Point2f(0,     0     ).sub(origin) .mul(transformMatrix) .add(origin).add(x, y);
        Tuple2f vertex2 = new Point2f(width, 0     ).sub(origin) .mul(transformMatrix) .add(origin).add(x, y);
        Tuple2f vertex3 = new Point2f(width, height).sub(origin) .mul(transformMatrix) .add(origin).add(x, y);
        Tuple2f vertex4 = new Point2f(0,     height).sub(origin) .mul(transformMatrix) .add(origin).add(x, y);

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