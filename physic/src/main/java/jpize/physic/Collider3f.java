package jpize.physic;

import jpize.math.vecmath.vector.Vec3f;

public class Collider3f{

    public static Vec3f getCollidedMotion(BoxBody body, Vec3f motion, BoxBody... boxes){
        if(motion.isZero())
            return motion;

        body = body.copy();

        float x = motion.x;
        for(BoxBody box: boxes)
            if(x != 0)
                x = distX(x, body, box);
        body.getPosition().x += x;
        
        float y = motion.y;
        for(BoxBody box: boxes)
            if(y != 0)
                y = distY(y, body, box);
        body.getPosition().y += y;
        
        float z = motion.z;
        for(BoxBody box: boxes)
            if(z != 0)
                z = distZ(z, body, box);
        body.getPosition().z += z;

        return new Vec3f(x, y, z);
    }


    private static float distX(float motion, BoxBody body, BoxBody box){
        if(motion == 0)
            return 0;
        if(box.getMax().y > body.getMin().y && box.getMin().y < body.getMax().y && box.getMax().z > body.getMin().z && box.getMin().z < body.getMax().z)
            if(motion > 0){
                float min = Math.min(box.getMin().x, box.getMax().x);
                float max = Math.max(body.getMin().x, body.getMax().x);
                float offset = min - max;
                if(offset >= 0 && motion > offset)
                    return offset;
            }else{
                float min = Math.min(body.getMin().x, body.getMax().x);
                float max = Math.max(box.getMin().x, box.getMax().x);
                float offset = max - min;
                if(offset <= 0 && motion < offset)
                    return offset;
            }
        return motion;
    }
    
    private static float distY(float motion, BoxBody body, BoxBody box){
        if(motion == 0)
            return 0;
        if(box.getMax().x > body.getMin().x && box.getMin().x < body.getMax().x && box.getMax().z > body.getMin().z && box.getMin().z < body.getMax().z)
            if(motion > 0){
                float min = Math.min(box.getMin().y, box.getMax().y);
                float max = Math.max(body.getMin().y, body.getMax().y);
                float offset = min - max;
                if(offset >= 0 && motion > offset)
                    return offset;
            }else{
                float min = Math.min(body.getMin().y, body.getMax().y);
                float max = Math.max(box.getMin().y, box.getMax().y);
                float offset = max - min;
                if(offset <= 0 && motion < offset)
                    return offset;
            }
        return motion;
    }
    
    private static float distZ(float motion, BoxBody body, BoxBody box){
        if(motion == 0)
            return 0;
        if(box.getMax().x > body.getMin().x && box.getMin().x < body.getMax().x && box.getMax().y > body.getMin().y && box.getMin().y < body.getMax().y)
            if(motion > 0){
                float min = Math.min(box.getMin().z, box.getMax().z);
                float max = Math.max(body.getMin().z, body.getMax().z);
                float offset = min - max;
                if(offset >= 0 && motion > offset)
                    return offset;
            }else{
                float min = Math.min(body.getMin().z, body.getMax().z);
                float max = Math.max(box.getMin().z, box.getMax().z);
                float offset = max - min;
                if(offset <= 0 && motion < offset)
                    return offset;
            }
        return motion;
    }

}