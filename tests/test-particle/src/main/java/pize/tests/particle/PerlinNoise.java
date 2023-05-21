package pize.tests.particle;

import pize.math.util.Random;
import pize.math.vecmath.vector.Vec2f;

import static pize.math.Maths.*;

public class PerlinNoise extends Noise{
    
    private static final Vec2f[] gradientVectors = {
        new Vec2f(1,  0),
        new Vec2f(-1, 0),
        new Vec2f(0,  1),
        new Vec2f(0, -1)
    };
    
    private final byte[] permutationTable;
    private int seed = 54;
    
    public PerlinNoise(){
        permutationTable = new byte[1024];
        genPermutationTable();
    }
    
    
    @Override
    public void setSeed(int seed){
        this.seed = seed;
        genPermutationTable();
    }
    
    private void genPermutationTable(){
        new Random(seed).randomBytes(permutationTable);
    }
    
    
    private Vec2f getGradVec(int x, int y){
        int v = (int) (((x * 1836311903L) ^ (y * 2971215073L) + 4807526976L) & 1023);
        return gradientVectors[permutationTable[v] & 3];
    }
    
    @Override
    public float get(float x, float y){
        x /= scale;
        y /= scale;
        
        int left = floor(x);
        int bottom = floor(y);
        float pointInQuadX = x - left;
        float pointInQuadY = y - bottom;
        
        Vec2f gradLeftBottom  = getGradVec(left    , bottom    );
        Vec2f gradRightBottom = getGradVec(left + 1, bottom    );
        Vec2f gradLeftTop     = getGradVec(left    , bottom + 1);
        Vec2f gradRightTop    = getGradVec(left + 1, bottom + 1);
    
        Vec2f leftBottomToPoint  = new Vec2f(pointInQuadX    , pointInQuadY    );
        Vec2f rightBottomToPoint = new Vec2f(pointInQuadX - 1, pointInQuadY    );
        Vec2f leftTopToPoint     = new Vec2f(pointInQuadX    , pointInQuadY - 1);
        Vec2f rightTopToPoint    = new Vec2f(pointInQuadX - 1, pointInQuadY - 1);
    
        float rightBottom = gradRightBottom.dot(rightBottomToPoint);
        float leftBottom  =  gradLeftBottom.dot(leftBottomToPoint);
        float leftTop     =     gradLeftTop.dot(leftTopToPoint);
        float rightTop    =    gradRightTop.dot(rightTopToPoint);
    
        pointInQuadX = quinticCurve(pointInQuadX);
        pointInQuadY = quinticCurve(pointInQuadY);
        
        float erpTop    = lerp(rightTop, leftTop, pointInQuadX);
        float erpBottom = lerp(leftBottom, rightBottom, pointInQuadX);
        return            lerp(erpTop, erpBottom, pointInQuadY);
    }
    
    @Override
    public float get(float x, float y, float z){
        return 0;
    }
    
    
}
