package jpize.tests.mcose.client.block.model;

import jpize.math.vecmath.vector.Vec3f;

public class Quad{

    protected final Vec3f[] pos;

    public Quad(Vec3f p1, Vec3f p2, Vec3f p3, Vec3f p4){
        this.pos = new Vec3f[4];
        this.pos[0] = p1;
        this.pos[1] = p2;
        this.pos[2] = p3;
        this.pos[3] = p4;
    }

    public Quad(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4){
        this(
            new Vec3f(x1, y1, z1),
            new Vec3f(x2, y2, z2),
            new Vec3f(x3, y3, z3),
            new Vec3f(x4, y4, z4)
        );
    }

    public Quad(Quad quad){
        this.pos = new Vec3f[]{
            quad.pos[0].copy(),
            quad.pos[1].copy(),
            quad.pos[2].copy(),
            quad.pos[3].copy()
        };
    }

    public Quad translate(float x, float y, float z){
        this.pos[0].add(x, y, z);
        this.pos[1].add(x, y, z);
        this.pos[2].add(x, y, z);
        this.pos[3].add(x, y, z);
        return this;
    }

    public Quad scale(double x, double y, double z){
        this.pos[0].mul(x, y, z);
        this.pos[1].mul(x, y, z);
        this.pos[2].mul(x, y, z);
        this.pos[3].mul(x, y, z);
        return this;
    }

    public Quad copy(){
        return new Quad(this);
    }


    private static final Quad nxQuad = new Quad(0, 1, 1,  0, 0, 1,  0, 0, 0,  0, 1, 0);
    private static final Quad pxQuad = new Quad(1, 1, 0,  1, 0, 0,  1, 0, 1,  1, 1, 1);
    private static final Quad nyQuad = new Quad(1, 0, 1,  1, 0, 0,  0, 0, 0,  0, 0, 1);
    private static final Quad pyQuad = new Quad(1, 1, 0,  1, 1, 1,  0, 1, 1,  0, 1, 0);
    private static final Quad nzQuad = new Quad(0, 1, 0,  0, 0, 0,  1, 0, 0,  1, 1, 0);
    private static final Quad pzQuad = new Quad(1, 1, 1,  1, 0, 1,  0, 0, 1,  0, 1, 1);

    public static Quad getNxQuad(){
        return nxQuad.copy();
    }

    public static Quad getPxQuad(){
        return pxQuad.copy();
    }

    public static Quad getNyQuad(){
        return nyQuad.copy();
    }

    public static Quad getPyQuad(){
        return pyQuad.copy();
    }

    public static Quad getNzQuad(){
        return nzQuad.copy();
    }

    public static Quad getPzQuad(){
        return pzQuad.copy();
    }


    @Override
    public String toString(){
        return pos[0] + ", " + pos[1] + ", " + pos[2] + ", " + pos[3];
    }

}
