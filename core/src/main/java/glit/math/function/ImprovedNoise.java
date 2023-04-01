package glit.math.function;

public final class ImprovedNoise{
    
    static public double noise(double x, double y, double z){
        // Fine unit cube that contains point
        int X = (int) Math.floor(x) & 255;
        int Y = (int) Math.floor(y) & 255;
        int Z = (int) Math.floor(z) & 255;
    
        // Hash coordinates of the 8 cube corners
        int A  = p[X] + Y;
        int AA = p[A] + Z;
        int AB = p[A + 1] + Z;
        int B  = p[X + 1] + Y;
        int BA = p[B] + Z;
        int BB = p[B + 1] + Z;
    
        x -= Math.floor(x); // Find relative x ,y , z
        y -= Math.floor(y); // of point in cube
        z -= Math.floor(z);
        
        double u = quintic(x); // Compute fade curves for each of x ,y , z
        double v = quintic(y);
        double w = quintic(z);
        
        // ... and add blended results from 8 corners of cube
        return lerp(
            w,
            
            lerp(
                v,
                lerp(
                    u,
                    grad(p[AA], x, y, z),
                    grad(p[BA], x - 1, y, z)
                ),
                lerp(
                    u,
                    grad(p[AB], x, y - 1, z),
                    grad(p[BB], x - 1, y - 1, z)
                )
            ),
            
            lerp(
                v,
                lerp(
                    u,
                    grad(p[AA + 1], x, y, z - 1),
                    grad(p[BA + 1], x - 1, y, z - 1)
                ),
                lerp(
                    u,
                    grad(p[AB + 1], x, y - 1, z - 1),
                    grad(p[BB + 1], x - 1, y - 1, z - 1)
                )
            )
        );
    }
    
    static double quintic(double t){
        return t * t * t * (t * (t * 6 - 15) + 10);
    }
    
    static double lerp(double t, double a, double b){
        return a + t * (b - a);
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