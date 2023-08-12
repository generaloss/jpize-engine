package pize.tests.minecraftosp.client.block.model;

import pize.graphics.texture.Region;
import pize.graphics.util.color.Color;
import pize.graphics.util.color.IColor;
import pize.graphics.util.color.ImmutableColor;
import pize.math.vecmath.vector.Vec2f;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.minecraftosp.client.block.BlockRotation;

import java.util.List;

public class Face extends Quad{

    protected final Vec2f t1, t2, t3, t4;
    protected IColor color;
    protected byte faceData; // 0 - enable grass coloring, 1 - enable water coloring

    public Face(Face face){
        super(face);
        this.t1 = face.t1.copy();
        this.t2 = face.t2.copy();
        this.t3 = face.t3.copy();
        this.t4 = face.t4.copy();
        this.color = new ImmutableColor(face.color);
    }

    public Face(Quad quad, Vec2f t1, Vec2f t2, Vec2f t3, Vec2f t4, IColor color){
        super(quad);
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.color = color;
    }

    public Face(Vec3f p1, Vec3f p2, Vec3f p3, Vec3f p4, Vec2f t1, Vec2f t2, Vec2f t3, Vec2f t4, IColor color){
        this(
            new Quad(p1, p2, p3, p4),
            t1, t2, t3, t4,
            color
        );
    }

    public Face(Quad quad, Region region, IColor color){
        this(
            quad,
            new Vec2f(region.u1(), region.v1()),
            new Vec2f(region.u1(), region.v2()),
            new Vec2f(region.u2(), region.v2()),
            new Vec2f(region.u2(), region.v1()),
            color
        );
    }

    public Face(Quad quad, Region region){
        this(quad, region, Color.WHITE);
    }

    public Face(Vec3f p1, Vec3f p2, Vec3f p3, Vec3f p4, Region region, IColor color){
        this(
            new Quad(p1, p2, p3, p4),
            region,
            color
        );
    }


    public void putFloats(final List<Float> floatList,
                          float x, float y, float z,
                          IColor color,
                          float l1, float l2, float l3, float l4){

        final float p1x = p1.x + x; final float p1y = p1.y + y; final float p1z = p1.z + z; // p1
        final float p3x = p3.x + x; final float p3y = p3.y + y; final float p3z = p3.z + z; // p3

        // Triangle 1
        putVertex(floatList,  p1x     , p1y     , p1z     ,  t1.x, t1.y,  color,  l1); // 1       1 ------ 4
        putVertex(floatList,  p2.x + x, p2.y + y, p2.z + z,  t2.x, t2.y,  color,  l2); // 2       |  ╲╲    |
        putVertex(floatList,  p3x     , p3y     , p3z     ,  t3.x, t3.y,  color,  l3); // 3       |    ╲╲  |
        // Triangle 2                                                                             2 ------ 3
        putVertex(floatList,  p3x     , p3y     , p3z     ,  t3.x, t3.y,  color,  l3); // 3
        putVertex(floatList,  p4.x + x, p4.y + y, p4.z + z,  t4.x, t4.y,  color,  l4); // 4
        putVertex(floatList,  p1x     , p1y     , p1z     ,  t1.x, t1.y,  color,  l1); // 1
    }

    public void putFloatsFlipped(final List<Float> floatList,
                                 float x, float y, float z,
                                 IColor color,
                                 float l1, float l2, float l3, float l4){

        final float p4x = p4.x + x; final float p4y = p4.y + y; final float p4z = p4.z + z; // p4
        final float p2x = p2.x + x; final float p2y = p2.y + y; final float p2z = p2.z + z; // p2

        // Triangle 1
        putVertex(floatList,  p4x      , p4y    , p4z     ,  t4.x, t4.y,  color,  l4); // 4        1 ------ 4
        putVertex(floatList,  p1.x + x, p1.y + y, p1.z + z,  t1.x, t1.y,  color,  l1); // 1        |    ╱╱  |
        putVertex(floatList,  p2x     , p2y     , p2z     ,  t2.x, t2.y,  color,  l2); // 2        |  ╱╱    |
        // Triangle 2                                                                              2 ------ 3
        putVertex(floatList,  p2x     , p2y     , p2z     ,  t2.x, t2.y,  color,  l2); // 2
        putVertex(floatList,  p3.x + x, p3.y + y, p3.z + z,  t3.x, t3.y,  color,  l3); // 3
        putVertex(floatList,  p4x      , p4y    , p4z     ,  t4.x, t4.y,  color,  l4); // 4
    }

    public void putIntsPacked(final List<Integer> intList,
                              int x, int y, int z,
                              IColor color,
                              float l1, float l2, float l3, float l4){

        final float p1x = p1.x + x; final float p1y = p1.y + y; final float p1z = p1.z + z; // p1
        final float p3x = p3.x + x; final float p3y = p3.y + y; final float p3z = p3.z + z; // p3

        putVertexPacked(intList,  p1x     , p1y     , p1z     ,  t1.x, t1.y,  color,  l1); // 1
        putVertexPacked(intList,  p2.x + x, p2.y + y, p2.z + z,  t2.x, t2.y,  color,  l2); // 2
        putVertexPacked(intList,  p3x     , p3y     , p3z     ,  t3.x, t3.y,  color,  l3); // 3

        putVertexPacked(intList,  p3x     , p3y     , p3z     ,  t3.x, t3.y,  color,  l3); // 3
        putVertexPacked(intList,  p4.x + x, p4.y + y, p4.z + z,  t4.x, t4.y,  color,  l4); // 4
        putVertexPacked(intList,  p1x     , p1y     , p1z     ,  t1.x, t1.y,  color,  l1); // 1
    }

    public void putIntsPackedFlipped(final List<Integer> intList,
                                     int x, int y, int z,
                                     IColor color,
                                     float l1, float l2, float l3, float l4){

        final float p4x = p4.x + x; final float p4y = p4.y + y; final float p4z = p4.z + z; // p4
        final float p2x = p2.x + x; final float p2y = p2.y + y; final float p2z = p2.z + z; // p2

        putVertexPacked(intList,  p4x      , p4y    , p4z     ,  t4.x, t4.y,  color,  l4); // 4
        putVertexPacked(intList,  p1.x + x, p1.y + y, p1.z + z,  t1.x, t1.y,  color,  l1); // 1
        putVertexPacked(intList,  p2x     , p2y     , p2z     ,  t2.x, t2.y,  color,  l2); // 2

        putVertexPacked(intList,  p2x     , p2y     , p2z     ,  t2.x, t2.y,  color,  l2); // 2
        putVertexPacked(intList,  p3.x + x, p3.y + y, p3.z + z,  t3.x, t3.y,  color,  l3); // 3
        putVertexPacked(intList,  p4x      , p4y    , p4z     ,  t4.x, t4.y,  color,  l4); // 4
    }


    private void putVertex(final List<Float> floatList,
                           float x, float y, float z,
                           float u, float v,
                           IColor color,
                           float light){
        floatList.add(x);
        floatList.add(y);
        floatList.add(z);

        floatList.add(this.color.r() * color.r() * light);
        floatList.add(this.color.g() * color.g() * light);
        floatList.add(this.color.b() * color.b() * light);
        floatList.add(this.color.a() * color.a());

        floatList.add(u);
        floatList.add(v);
    }

    private void putVertexPacked(final List<Integer> intList,
                                 float x, float y, float z,
                                 float u, float v,
                                 IColor color,
                                 float light){
        final int mulU = 512 / 16;
        final int mulV = 512 / 16;

        // Packed position
        final int positionPacked = (
            ((int) x      ) | // 5 bit
            ((int) y << 5 ) | // 9 bit
            ((int) z << 14) | // 5 bit

            ((int) (u * mulU) << 19) | // 6 bit
            ((int) (v * mulV) << 25)   // 6 bit
        );
        intList.add(positionPacked); // x, y, z, u, v

        // Packed color
        final int colorPacked = (
            ((int) (this.color.r() * color.r() * light * 255)      ) | // 8 bit
            ((int) (this.color.g() * color.g() * light * 255) << 8 ) | // 8 bit
            ((int) (this.color.b() * color.b() * light * 255) << 16) | // 8 bit
            ((int) (this.color.a() * color.a()         * 255) << 24)   // 8 bit
        );
        intList.add(colorPacked); // r, g, b, a
    }


    public Face copy(){
        return new Face(this);
    }

    public Face rotated(BlockRotation rotation){
        final Vec3f rp1 = p1.copy().sub(0.5).mul(rotation.getMatrix()).add(0.5);
        final Vec3f rp2 = p2.copy().sub(0.5).mul(rotation.getMatrix()).add(0.5);
        final Vec3f rp3 = p3.copy().sub(0.5).mul(rotation.getMatrix()).add(0.5);
        final Vec3f rp4 = p4.copy().sub(0.5).mul(rotation.getMatrix()).add(0.5);

        if(p1 == rp2){
            return new Face(rp2, rp3, rp4, rp1, t1.copy(), t2.copy(), t3.copy(), t4.copy(), color);
        }else if(p1 == rp3){
            return new Face(rp3, rp4, rp1, rp2, t1.copy(), t2.copy(), t3.copy(), t4.copy(), color);
        }else if(p1 == rp4){
            return new Face(rp4, rp1, rp2, rp3, t1.copy(), t2.copy(), t3.copy(), t4.copy(), color);
        }else
            return new Face(rp1, rp2, rp3, rp4, t1.copy(), t2.copy(), t3.copy(), t4.copy(), color);
    }


    public Face setFaceData(byte faceData){
        this.faceData = faceData;
        return this;
    }

    private void enableFaceDataBit(int index){
        faceData |= (byte) (1 << index);
    }

    private boolean getFaceDataBit(int index){
        return ((faceData >> index) & 1) == 1;
    }


    public boolean isGrassColoring(){
        return getFaceDataBit(0);
    }

    public Face enableGrassColoring(){
        enableFaceDataBit(0);
        return this;
    }


    public boolean isWaterColoring(){
        return getFaceDataBit(1);
    }

    public Face enableWaterColoring(){
        enableFaceDataBit(1);
        return this;
    }

}
