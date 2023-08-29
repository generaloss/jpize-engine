package jpize.physic;

import jpize.math.vecmath.vector.Vec2f;

public class Collider2f{

    public static Vec2f getCollidedMotion(RectBody body, Vec2f motion, RectBody... rects){
        if(motion.isZero())
            return motion;

        body = body.copy();

        float x = motion.x;
        for(RectBody rect: rects)
            if(x != 0)
                x = distX(x, body, rect);
        body.pos().x += x;
        
        float y = motion.y;
        for(RectBody rect: rects)
            if(y != 0)
                y = distY(y, body, rect);
        body.pos().y += y;

        return new Vec2f(x, y);
    }


    private static float distX(float motion, RectBody body, RectBody rect){
        if(motion == 0)
            return 0;
        if(rect.getMax().y > body.getMin().y && rect.getMin().y < body.getMax().y)
            if(motion > 0){
                float min = Math.min(rect.getMin().x, rect.getMax().x);
                float max = Math.max(body.getMin().x, body.getMax().x);
                float offset = min - max;
                if(offset >= 0 && motion > offset)
                    return offset;
            }else{
                float min = Math.min(body.getMin().x, body.getMax().x);
                float max = Math.max(rect.getMin().x, rect.getMax().x);
                float offset = max - min;
                if(offset <= 0 && motion < offset)
                    return offset;
            }
        return motion;
    }
    
    private static float distY(float motion, RectBody body, RectBody rect){
        if(motion == 0)
            return 0;
        if(rect.getMax().x > body.getMin().x && rect.getMin().x < body.getMax().x)
            if(motion > 0){
                float min = Math.min(rect.getMin().y, rect.getMax().y);
                float max = Math.max(body.getMin().y, body.getMax().y);
                float offset = min - max;
                if(offset >= 0 && motion > offset)
                    return offset;
            }else{
                float min = Math.min(body.getMin().y, body.getMax().y);
                float max = Math.max(rect.getMin().y, rect.getMax().y);
                float offset = max - min;
                if(offset <= 0 && motion < offset)
                    return offset;
            }
        return motion;
    }

}
