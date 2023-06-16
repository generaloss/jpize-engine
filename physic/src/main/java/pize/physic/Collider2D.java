package pize.physic;

import pize.math.vecmath.vector.Vec2d;

public class Collider2D{

    public static Vec2d getCollidedMotion(RectBody body, Vec2d motion, RectBody... rects){
        if(motion.isZero())
            return motion;

        body = body.clone();

        double x = motion.x;
        for(RectBody rect: rects)
            if(x != 0)
                x = distX(x, body, rect);
        body.pos().x += x;

        double y = motion.y;
        for(RectBody rect: rects)
            if(y != 0)
                y = distY(y, body, rect);
        body.pos().y += y;

        return new Vec2d(x, y);
    }


    private static double distX(double motionX, RectBody body, RectBody rect){
        if(motionX == 0)
            return 0;
        if(rect.getMax().y > body.getMin().y && rect.getMin().y < body.getMax().y)
            if(motionX > 0){
                double min = Math.min(rect.getMin().x, rect.getMax().x);
                double max = Math.max(body.getMin().x, body.getMax().x);
                double offset = min - max;
                if(offset >= 0 && motionX > offset)
                    return offset;
            }else{
                double min = Math.min(body.getMin().x, body.getMax().x);
                double max = Math.max(rect.getMin().x, rect.getMax().x);
                double offset = max - min;
                if(offset <= 0 && motionX < offset)
                    return offset;
            }
        return motionX;
    }
    
    private static double distY(double motionY, RectBody body, RectBody rect){
        if(motionY == 0)
            return 0;
        if(rect.getMax().x > body.getMin().x && rect.getMin().x < body.getMax().x)
            if(motionY > 0){
                double min = Math.min(rect.getMin().y, rect.getMax().y);
                double max = Math.max(body.getMin().y, body.getMax().y);
                double offset = min - max;
                if(offset >= 0 && motionY > offset)
                    return offset;
            }else{
                double min = Math.min(body.getMin().y, body.getMax().y);
                double max = Math.max(rect.getMin().y, rect.getMax().y);
                double offset = max - min;
                if(offset <= 0 && motionY < offset)
                    return offset;
            }
        return motionY;
    }

}
