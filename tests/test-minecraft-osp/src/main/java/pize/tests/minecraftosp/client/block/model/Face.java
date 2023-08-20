package pize.tests.minecraftosp.client.block.model;

import pize.graphics.texture.Region;
import pize.graphics.util.color.Color;
import pize.graphics.util.color.IColor;
import pize.graphics.util.color.ImmutableColor;
import pize.math.vecmath.vector.Vec2f;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.minecraftosp.client.block.BlockRotation;
import pize.tests.minecraftosp.client.chunk.mesh.ChunkMesh;

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
        this.faceData = face.faceData;
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


    public void putFace(final ChunkMesh mesh,
                        float x, float y, float z,
                        IColor color,
                        float ao1, float ao2, float ao3, float ao4,
                        float sl1, float sl2, float sl3, float sl4,
                        float bl1, float bl2, float bl3, float bl4){

        if(ao2 + ao4 > ao1 + ao3)
            putFloatsFlipped(mesh,  x, y, z,  color,  ao1, ao2, ao3, ao4,  sl1, sl2, sl3, sl4,  bl1, bl2, bl3, bl4);
        else
            putFloats(mesh,  x, y, z,  color,  ao1, ao2, ao3, ao4,  sl1, sl2, sl3, sl4,  bl1, bl2, bl3, bl4);
    }

    public void putFloats(final ChunkMesh mesh,
                          float x, float y, float z,
                          IColor color,
                          float ao1, float ao2, float ao3, float ao4,
                          float sl1, float sl2, float sl3, float sl4,
                          float bl1, float bl2, float bl3, float bl4){

        final float r = this.color.r() * color.r();
        final float g = this.color.g() * color.g();
        final float b = this.color.b() * color.b();
        final float a = this.color.a() * color.a();

        final float p1x = pos[0].x + x; final float p1y = pos[0].y + y; final float p1z = pos[0].z + z;
        final float p2x = pos[1].x + x; final float p2y = pos[1].y + y; final float p2z = pos[1].z + z;
        final float p3x = pos[2].x + x; final float p3y = pos[2].y + y; final float p3z = pos[2].z + z;
        final float p4x = pos[3].x + x; final float p4y = pos[3].y + y; final float p4z = pos[3].z + z;

        // Triangle 1
        mesh.vertex(p1x, p1y, p1z,  t1.x, t1.y,  r, g, b, a,  ao1, sl1, bl1); // 1     1 ----- 4
        mesh.vertex(p2x, p2y, p2z,  t2.x, t2.y,  r, g, b, a,  ao2, sl2, bl2); // 2     |  ╲    |
        mesh.vertex(p3x, p3y, p3z,  t3.x, t3.y,  r, g, b, a,  ao3, sl3, bl3); // 3     |    ╲  |
        // Triangle 2                                                                  2 ----- 3
        mesh.vertex(p3x, p3y, p3z,  t3.x, t3.y,  r, g, b, a,  ao3, sl3, bl3); // 3
        mesh.vertex(p4x, p4y, p4z,  t4.x, t4.y,  r, g, b, a,  ao4, sl4, bl4); // 4
        mesh.vertex(p1x, p1y, p1z,  t1.x, t1.y,  r, g, b, a,  ao1, sl1, bl1); // 1
    }

    private void putFloatsFlipped(final ChunkMesh mesh,
                                 float x, float y, float z,
                                 IColor color,
                                 float ao1, float ao2, float ao3, float ao4,
                                 float sl1, float sl2, float sl3, float sl4,
                                 float bl1, float bl2, float bl3, float bl4){

        final float r = this.color.r() * color.r();
        final float g = this.color.g() * color.g();
        final float b = this.color.b() * color.b();
        final float a = this.color.a() * color.a();

        final float p1x = pos[0].x + x; final float p1y = pos[0].y + y; final float p1z = pos[0].z + z;
        final float p2x = pos[1].x + x; final float p2y = pos[1].y + y; final float p2z = pos[1].z + z;
        final float p3x = pos[2].x + x; final float p3y = pos[2].y + y; final float p3z = pos[2].z + z;
        final float p4x = pos[3].x + x; final float p4y = pos[3].y + y; final float p4z = pos[3].z + z;

        // Triangle 1
        mesh.vertex(p4x, p4y, p4z,  t4.x, t4.y,  r, g, b, a,  ao4, sl4, bl4); // 4     1 ----- 4
        mesh.vertex(p1x, p1y, p1z,  t1.x, t1.y,  r, g, b, a,  ao1, sl1, bl1); // 1     |    ╱  |
        mesh.vertex(p2x, p2y, p2z,  t2.x, t2.y,  r, g, b, a,  ao2, sl2, bl2); // 2     |  ╱    |
        // Triangle 2                                                                  2 ----- 3
        mesh.vertex(p2x, p2y, p2z,  t2.x, t2.y,  r, g, b, a,  ao2, sl2, bl2); // 2
        mesh.vertex(p3x, p3y, p3z,  t3.x, t3.y,  r, g, b, a,  ao3, sl3, bl3); // 3
        mesh.vertex(p4x, p4y, p4z,  t4.x, t4.y,  r, g, b, a,  ao4, sl4, bl4); // 4
    }


    public Face copy(){
        return new Face(this);
    }

    public Face rotated(BlockRotation rotation){
        final Vec3f rp1 = pos[0].copy().sub(0.5).mul(rotation.getMatrix()).add(0.5);
        final Vec3f rp2 = pos[1].copy().sub(0.5).mul(rotation.getMatrix()).add(0.5);
        final Vec3f rp3 = pos[2].copy().sub(0.5).mul(rotation.getMatrix()).add(0.5);
        final Vec3f rp4 = pos[3].copy().sub(0.5).mul(rotation.getMatrix()).add(0.5);

        final Face face = new Face(rp1, rp2, rp3, rp4, t1.copy(), t2.copy(), t3.copy(), t4.copy(), color);;

        return face;
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
