package jpize.tests.minecraftose.client.block.model;

import jpize.graphics.texture.Region;
import jpize.graphics.util.color.Color;
import jpize.graphics.util.color.IColor;
import jpize.math.vecmath.vector.Vec2f;
import jpize.math.vecmath.vector.Vec3f;
import jpize.tests.minecraftose.client.block.BlockRotation;
import jpize.tests.minecraftose.client.chunk.mesh.ChunkMesh;

public class Face extends Quad{

    protected final Vec2f t1, t2, t3, t4;
    protected Color color;
    protected byte faceData; // 0 - enable grass coloring, 1 - enable water coloring

    public Face(Face face){
        super(face);
        this.t1 = face.t1.copy();
        this.t2 = face.t2.copy();
        this.t3 = face.t3.copy();
        this.t4 = face.t4.copy();
        this.color = face.color.copy();
        this.faceData = face.faceData;
    }

    public Face(Quad quad, Vec2f t1, Vec2f t2, Vec2f t3, Vec2f t4, IColor color){
        super(quad);
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.color = new Color(color);
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
                        IColor col1, IColor col2, IColor col3, IColor col4,
                        float ao1, float ao2, float ao3, float ao4,
                        float sl1, float sl2, float sl3, float sl4,
                        float bl1, float bl2, float bl3, float bl4){

        if(ao2 * sl2 * bl2  +  ao4 * sl4 * bl4  >  ao1 * sl1 * bl1  +  ao3 * sl3 * bl3)
            putFloatsFlipped(mesh,  x, y, z,  col1, col2, col3, col4,  ao1, ao2, ao3, ao4,  sl1, sl2, sl3, sl4,  bl1, bl2, bl3, bl4);
        else
            putFloats(mesh,  x, y, z,  col1, col2, col3, col4,  ao1, ao2, ao3, ao4,  sl1, sl2, sl3, sl4,  bl1, bl2, bl3, bl4);
    }

    public void putFloats(final ChunkMesh mesh,
                          float x, float y, float z,
                          IColor col1, IColor col2, IColor col3, IColor col4,
                          float ao1, float ao2, float ao3, float ao4,
                          float sl1, float sl2, float sl3, float sl4,
                          float bl1, float bl2, float bl3, float bl4){

        final float r1 = this.color.r() * col1.r();
        final float g1 = this.color.g() * col1.g();
        final float b1 = this.color.b() * col1.b();
        final float a1 = this.color.a() * col1.a();

        final float r2 = this.color.r() * col2.r();
        final float g2 = this.color.g() * col2.g();
        final float b2 = this.color.b() * col2.b();
        final float a2 = this.color.a() * col2.a();

        final float r3 = this.color.r() * col3.r();
        final float g3 = this.color.g() * col3.g();
        final float b3 = this.color.b() * col3.b();
        final float a3 = this.color.a() * col3.a();

        final float r4 = this.color.r() * col4.r();
        final float g4 = this.color.g() * col4.g();
        final float b4 = this.color.b() * col4.b();
        final float a4 = this.color.a() * col4.a();

        final float p1x = pos[0].x + x; final float p1y = pos[0].y + y; final float p1z = pos[0].z + z;
        final float p2x = pos[1].x + x; final float p2y = pos[1].y + y; final float p2z = pos[1].z + z;
        final float p3x = pos[2].x + x; final float p3y = pos[2].y + y; final float p3z = pos[2].z + z;
        final float p4x = pos[3].x + x; final float p4y = pos[3].y + y; final float p4z = pos[3].z + z;

        // Triangle 1
        mesh.vertex(p1x, p1y, p1z,  t1.x, t1.y,  r1, g1, b1, a1,  ao1, sl1, bl1); // 1     1 ----- 4
        mesh.vertex(p2x, p2y, p2z,  t2.x, t2.y,  r2, g2, b2, a2,  ao2, sl2, bl2); // 2     |  ╲    |
        mesh.vertex(p3x, p3y, p3z,  t3.x, t3.y,  r3, g3, b3, a3,  ao3, sl3, bl3); // 3     |    ╲  |
        // Triangle 2                                                                      2 ----- 3
        mesh.vertex(p3x, p3y, p3z,  t3.x, t3.y,  r3, g3, b3, a3,  ao3, sl3, bl3); // 3
        mesh.vertex(p4x, p4y, p4z,  t4.x, t4.y,  r4, g4, b4, a4,  ao4, sl4, bl4); // 4
        mesh.vertex(p1x, p1y, p1z,  t1.x, t1.y,  r1, g1, b1, a1,  ao1, sl1, bl1); // 1
    }

    private void putFloatsFlipped(final ChunkMesh mesh,
                                  float x, float y, float z,
                                  IColor col1, IColor col2, IColor col3, IColor col4,
                                  float ao1, float ao2, float ao3, float ao4,
                                  float sl1, float sl2, float sl3, float sl4,
                                  float bl1, float bl2, float bl3, float bl4){

        final float r1 = this.color.r() * col1.r();
        final float g1 = this.color.g() * col1.g();
        final float b1 = this.color.b() * col1.b();
        final float a1 = this.color.a() * col1.a();

        final float r2 = this.color.r() * col2.r();
        final float g2 = this.color.g() * col2.g();
        final float b2 = this.color.b() * col2.b();
        final float a2 = this.color.a() * col2.a();

        final float r3 = this.color.r() * col3.r();
        final float g3 = this.color.g() * col3.g();
        final float b3 = this.color.b() * col3.b();
        final float a3 = this.color.a() * col3.a();

        final float r4 = this.color.r() * col4.r();
        final float g4 = this.color.g() * col4.g();
        final float b4 = this.color.b() * col4.b();
        final float a4 = this.color.a() * col4.a();

        final float p1x = pos[0].x + x; final float p1y = pos[0].y + y; final float p1z = pos[0].z + z;
        final float p2x = pos[1].x + x; final float p2y = pos[1].y + y; final float p2z = pos[1].z + z;
        final float p3x = pos[2].x + x; final float p3y = pos[2].y + y; final float p3z = pos[2].z + z;
        final float p4x = pos[3].x + x; final float p4y = pos[3].y + y; final float p4z = pos[3].z + z;

        // Triangle 1
        mesh.vertex(p4x, p4y, p4z,  t4.x, t4.y,  r4, g4, b4, a4,  ao4, sl4, bl4); // 4     1 ----- 4
        mesh.vertex(p1x, p1y, p1z,  t1.x, t1.y,  r1, g1, b1, a1,  ao1, sl1, bl1); // 1     |    ╱  |
        mesh.vertex(p2x, p2y, p2z,  t2.x, t2.y,  r2, g2, b2, a2,  ao2, sl2, bl2); // 2     |  ╱    |
        // Triangle 2                                                                      2 ----- 3
        mesh.vertex(p2x, p2y, p2z,  t2.x, t2.y,  r2, g2, b2, a2,  ao2, sl2, bl2); // 2
        mesh.vertex(p3x, p3y, p3z,  t3.x, t3.y,  r3, g3, b3, a3,  ao3, sl3, bl3); // 3
        mesh.vertex(p4x, p4y, p4z,  t4.x, t4.y,  r4, g4, b4, a4,  ao4, sl4, bl4); // 4
    }


    public void putFacePacked(final ChunkMesh mesh,
                              int x, int y, int z,
                              IColor col1, IColor col2, IColor col3, IColor col4,
                              float ao1, float ao2, float ao3, float ao4,
                              float sl1, float sl2, float sl3, float sl4,
                              float bl1, float bl2, float bl3, float bl4){

        if(ao2 * sl2 * bl2  +  ao4 * sl4 * bl4  >  ao1 * sl1 * bl1  +  ao3 * sl3 * bl3)
            putPackedFlipped(mesh,  x, y, z,  col1, col2, col3, col4,  ao1, ao2, ao3, ao4,  sl1, sl2, sl3, sl4,  bl1, bl2, bl3, bl4);
        else
            putPacked(mesh,  x, y, z,  col1, col2, col3, col4,  ao1, ao2, ao3, ao4,  sl1, sl2, sl3, sl4,  bl1, bl2, bl3, bl4);
    }

    public void putPacked(final ChunkMesh mesh,
                          int x, int y, int z,
                          IColor col1, IColor col2, IColor col3, IColor col4,
                          float ao1, float ao2, float ao3, float ao4,
                          float sl1, float sl2, float sl3, float sl4,
                          float bl1, float bl2, float bl3, float bl4){

        final float r1 = this.color.r() * col1.r();
        final float g1 = this.color.g() * col1.g();
        final float b1 = this.color.b() * col1.b();
        final float a1 = this.color.a() * col1.a();

        final float r2 = this.color.r() * col2.r();
        final float g2 = this.color.g() * col2.g();
        final float b2 = this.color.b() * col2.b();
        final float a2 = this.color.a() * col2.a();

        final float r3 = this.color.r() * col3.r();
        final float g3 = this.color.g() * col3.g();
        final float b3 = this.color.b() * col3.b();
        final float a3 = this.color.a() * col3.a();

        final float r4 = this.color.r() * col4.r();
        final float g4 = this.color.g() * col4.g();
        final float b4 = this.color.b() * col4.b();
        final float a4 = this.color.a() * col4.a();

        final int p1x = (int) pos[0].x + x; final int p1y = (int) pos[0].y + y; final int p1z = (int) pos[0].z + z;
        final int p2x = (int) pos[1].x + x; final int p2y = (int) pos[1].y + y; final int p2z = (int) pos[1].z + z;
        final int p3x = (int) pos[2].x + x; final int p3y = (int) pos[2].y + y; final int p3z = (int) pos[2].z + z;
        final int p4x = (int) pos[3].x + x; final int p4y = (int) pos[3].y + y; final int p4z = (int) pos[3].z + z;

        // Triangle 1
        mesh.vertexPacked(p1x, p1y, p1z,  t1.x, t1.y,  r1, g1, b1, a1,  ao1, sl1, bl1); // 1     1 ----- 4
        mesh.vertexPacked(p2x, p2y, p2z,  t2.x, t2.y,  r2, g2, b2, a2,  ao2, sl2, bl2); // 2     |  ╲    |
        mesh.vertexPacked(p3x, p3y, p3z,  t3.x, t3.y,  r3, g3, b3, a3,  ao3, sl3, bl3); // 3     |    ╲  |
        // Triangle 2                                                                            2 ----- 3
        mesh.vertexPacked(p3x, p3y, p3z,  t3.x, t3.y,  r3, g3, b3, a3,  ao3, sl3, bl3); // 3
        mesh.vertexPacked(p4x, p4y, p4z,  t4.x, t4.y,  r4, g4, b4, a4,  ao4, sl4, bl4); // 4
        mesh.vertexPacked(p1x, p1y, p1z,  t1.x, t1.y,  r1, g1, b1, a1,  ao1, sl1, bl1); // 1
    }

    private void putPackedFlipped(final ChunkMesh mesh,
                                  int x, int y, int z,
                                  IColor col1, IColor col2, IColor col3, IColor col4,
                                  float ao1, float ao2, float ao3, float ao4,
                                  float sl1, float sl2, float sl3, float sl4,
                                  float bl1, float bl2, float bl3, float bl4){

        final float r1 = this.color.r() * col1.r();
        final float g1 = this.color.g() * col1.g();
        final float b1 = this.color.b() * col1.b();
        final float a1 = this.color.a() * col1.a();

        final float r2 = this.color.r() * col2.r();
        final float g2 = this.color.g() * col2.g();
        final float b2 = this.color.b() * col2.b();
        final float a2 = this.color.a() * col2.a();

        final float r3 = this.color.r() * col3.r();
        final float g3 = this.color.g() * col3.g();
        final float b3 = this.color.b() * col3.b();
        final float a3 = this.color.a() * col3.a();

        final float r4 = this.color.r() * col4.r();
        final float g4 = this.color.g() * col4.g();
        final float b4 = this.color.b() * col4.b();
        final float a4 = this.color.a() * col4.a();

        final int p1x = (int) pos[0].x + x; final int p1y = (int) pos[0].y + y; final int p1z = (int) pos[0].z + z;
        final int p2x = (int) pos[1].x + x; final int p2y = (int) pos[1].y + y; final int p2z = (int) pos[1].z + z;
        final int p3x = (int) pos[2].x + x; final int p3y = (int) pos[2].y + y; final int p3z = (int) pos[2].z + z;
        final int p4x = (int) pos[3].x + x; final int p4y = (int) pos[3].y + y; final int p4z = (int) pos[3].z + z;

        // Triangle 1
        mesh.vertexPacked(p4x, p4y, p4z,  t4.x, t4.y,  r4, g4, b4, a4,  ao4, sl4, bl4); // 4     1 ----- 4
        mesh.vertexPacked(p1x, p1y, p1z,  t1.x, t1.y,  r1, g1, b1, a1,  ao1, sl1, bl1); // 1     |    ╱  |
        mesh.vertexPacked(p2x, p2y, p2z,  t2.x, t2.y,  r2, g2, b2, a2,  ao2, sl2, bl2); // 2     |  ╱    |
        // Triangle 2                                                                            2 ----- 3
        mesh.vertexPacked(p2x, p2y, p2z,  t2.x, t2.y,  r2, g2, b2, a2,  ao2, sl2, bl2); // 2
        mesh.vertexPacked(p3x, p3y, p3z,  t3.x, t3.y,  r3, g3, b3, a3,  ao3, sl3, bl3); // 3
        mesh.vertexPacked(p4x, p4y, p4z,  t4.x, t4.y,  r4, g4, b4, a4,  ao4, sl4, bl4); // 4
    }
    
    
    public Face color(double r, double g, double b, double a){
        color.set(r, g, b, a);
        return this;
    }

    public Face color(double r, double g, double b){
        color.set3(r, g, b);
        return this;
    }

    public Face color(double grayScale){
        return color(grayScale, grayScale, grayScale);
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
