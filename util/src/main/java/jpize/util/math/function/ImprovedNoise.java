package jpize.util.math.function;

import jpize.util.math.Maths;

public final class ImprovedNoise{
    
    static public double noise(double x, double y, double z){
        // Fine unit cube that contains point
        int X = (int) Math.floor(x) & 255;
        int Y = (int) Math.floor(y) & 255;
        int Z = (int) Math.floor(z) & 255;
    
        // Hash coordinates of the 8 cube corners
        int A  = Y + p[X];
        int AA = Z + p[A];
        int AB = Z + p[A + 1];

        int B  = Y + p[X + 1];
        int BA = Z + p[B];
        int BB = Z + p[B + 1];
    
        x -= Math.floor(x); // Find relative x ,y , z
        y -= Math.floor(y); // of point in cube
        z -= Math.floor(z);
        
        double u = Maths.quintic((float) x); // Compute fade curves for each of x, y, z
        double v = Maths.quintic((float) y);
        double w = Maths.quintic((float) z);
        
        // ... and add blended results from 8 corners of cube
        return Maths.lerp(
            w,
            
            Maths.lerp(
                v,
                Maths.lerp(
                    u,
                    grad(p[AA], x, y, z),
                    grad(p[BA], x - 1, y, z)
                ),
                Maths.lerp(
                    u,
                    grad(p[AB], x, y - 1, z),
                    grad(p[BB], x - 1, y - 1, z)
                )
            ),
            
            Maths.lerp(
                v,
                Maths.lerp(
                    u,
                    grad(p[AA + 1], x, y, z - 1),
                    grad(p[BA + 1], x - 1, y, z - 1)
                ),
                Maths.lerp(
                    u,
                    grad(p[AB + 1], x, y - 1, z - 1),
                    grad(p[BB + 1], x - 1, y - 1, z - 1)
                )
            )
        );
    }
    
    
    static double grad(int hash, double x, double y, double z){
        // Convert low 4 bits of hash code into 12 gradient directions
        int h = hash & 15;
        double u = h < 8 ? x : y;
        double v = h < 4 ? y : h == 12 || h == 14 ? x : z;
        
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }
    
    static final int[] p = new int[512];
    static final int[] permutation = { 151, 160, 137, 91, 90, 15 };
    
    static{
        for(int i = 0; i < 256; i++)
            p[256 + i] = p[i] = permutation[i];
    }
    
}