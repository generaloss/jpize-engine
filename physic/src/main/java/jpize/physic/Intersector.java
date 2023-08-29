package jpize.physic;

import jpize.math.vecmath.vector.Vec3f;

public class Intersector{
    
    
    public static Vec3f lineQuad(Vec3f q1, Vec3f q2, Vec3f p1, Vec3f p2, Vec3f p3, Vec3f p4){
        final Vec3f i1 = lineTriangle(q1, q2, p1, p2, p3);
        final Vec3f i2 = lineTriangle(q1, q2, p1, p4, p3);
        if(i1 == null & i2 == null)
            return null;
        if(i2 == null)
            return i1;
        else
            return i2;
    }

    public static Vec3f lineQuad(Vec3f q1, Vec3f q2, double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x4, double y4, double z4){
        final Vec3f p1 = new Vec3f(x1, y1, z1);
        final Vec3f p2 = new Vec3f(x2, y2, z2);
        final Vec3f p3 = new Vec3f(x3, y3, z3);
        final Vec3f p4 = new Vec3f(x4, y4, z4);
        final Vec3f i1 = lineTriangle(q1, q2, p1, p2, p3);
        final Vec3f i2 = lineTriangle(q1, q2, p1, p4, p3);
        if(i1 == null & i2 == null)
            return null;
        if(i2 == null)
            return i1;
        else
            return i2;
    }

    public static Vec3f lineTriangle(Vec3f q1, Vec3f q2, Vec3f p1, Vec3f p2, Vec3f p3){
        final double s1 = signedTetraVolume(q1, p1, p2, p3);
        final double s2 = signedTetraVolume(q2, p1, p2, p3);

        if(s1 != s2){
            final double s3 = signedTetraVolume(q1, q2, p1, p2);
            final double s4 = signedTetraVolume(q1, q2, p2, p3);
            final double s5 = signedTetraVolume(q1, q2, p3, p1);
            if(s3 == s4 && s4 == s5){
                final Vec3f n = Vec3f.crs(p2.copy().sub(p1), p3.copy().sub(p1));
                final double t = - Vec3f.dot(q1, n.copy().sub(p1)) / Vec3f.dot(q1, q2.copy().sub(q1));
                return q1.add((q2.copy().sub(q1)).mul(t));
            }
        }
        return null;
    }

    private static double signedTetraVolume(Vec3f a, Vec3f b, Vec3f c, Vec3f d){
        return Math.signum(Vec3f.dot(Vec3f.crs(b.copy().sub(a), c.copy().sub(a)), d.copy().sub(a)) / 6F);
    }

}
