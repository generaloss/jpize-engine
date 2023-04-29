package pize.math.util;

import pize.math.vecmath.tuple.Tuple3f;
import pize.math.vecmath.vector.Vec3f;

public class Intersector{

    public static Tuple3f lineQuad(Vec3f q1, Vec3f q2, Vec3f p1, Vec3f p2, Vec3f p3, Vec3f p4){
        Tuple3f i1 = lineTriangle(q1, q2, p1, p2, p3);
        Tuple3f i2 = lineTriangle(q1, q2, p1, p4, p3);
        if(i1 == null & i2 == null)
            return null;
        if(i2 == null)
            return i1;
        else
            return i2;
    }

    public static Tuple3f lineQuad(Vec3f q1, Vec3f q2, double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x4, double y4, double z4){
        Vec3f p1 = new Vec3f(x1, y1, z1);
        Vec3f p2 = new Vec3f(x2, y2, z2);
        Vec3f p3 = new Vec3f(x3, y3, z3);
        Vec3f p4 = new Vec3f(x4, y4, z4);
        Tuple3f i1 = lineTriangle(q1, q2, p1, p2, p3);
        Tuple3f i2 = lineTriangle(q1, q2, p1, p4, p3);
        if(i1 == null & i2 == null)
            return null;
        if(i2 == null)
            return i1;
        else
            return i2;
    }

    public static Tuple3f lineTriangle(Vec3f q1, Vec3f q2, Vec3f p1, Vec3f p2, Vec3f p3){
        double s1 = signed_tetra_volume(q1, p1, p2, p3);
        double s2 = signed_tetra_volume(q2, p1, p2, p3);

        if(s1 != s2){
            double s3 = signed_tetra_volume(q1, q2, p1, p2);
            double s4 = signed_tetra_volume(q1, q2, p2, p3);
            double s5 = signed_tetra_volume(q1, q2, p3, p1);
            if(s3 == s4 && s4 == s5){
                Vec3f n = Vec3f.crs(p2.clone().sub(p1), p3.clone().sub(p1));
                double t = -Vec3f.dot(q1, n.clone().sub(p1)) / Vec3f.dot(q1, q2.clone().sub(q1));
                return q1.add((q2.clone().sub(q1)).mul(t));
            }
        }
        return null;
    }

    private static double signed_tetra_volume(Vec3f a, Vec3f b, Vec3f c, Vec3f d){
        return Math.signum(Vec3f.dot(Vec3f.crs(b.clone().sub(a), c.clone().sub(a)), d.clone().sub(a)) / 6F);
    }

}
