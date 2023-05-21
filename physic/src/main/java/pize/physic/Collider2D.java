package pize.physic;

import pize.math.vecmath.vector.Vec2d;

public class Collider2D{

    public static Vec2d getCollidedMove(RectBody body, Vec2d move, RectBody... rects){
        if(move.isZero())
            return move;

        body = body.clone();

        double x = move.x;
        for(RectBody rect: rects)
            if(x != 0)
                x = offsetX(x, body, rect);
        body.pos().x += x;

        double y = move.y;
        for(RectBody rect: rects)
            if(y != 0)
                y = offsetY(y, body, rect);
        body.pos().y += y;

        return new Vec2d(x, y);
    }


    public static double offsetX(double move, RectBody body, RectBody rect){
        if(move == 0)
            return 0;
        if(rect.getMax().y > body.getMin().y && rect.getMin().y < body.getMax().y)
            if(move > 0){
                double min = Math.min(rect.getMin().x, rect.getMax().x);
                double max = Math.max(body.getMin().x, body.getMax().x);
                double offset = min - max;
                if(offset >= 0 && move > offset)
                    return offset;
            }else{
                double min = Math.min(body.getMin().x, body.getMax().x);
                double max = Math.max(rect.getMin().x, rect.getMax().x);
                double offset = max - min;
                if(offset <= 0 && move < offset)
                    return offset;
            }
        return move;
    }

    public static double offsetY(double move, RectBody body, RectBody rect){
        if(move == 0)
            return 0;
        if(rect.getMax().x > body.getMin().x && rect.getMin().x < body.getMax().x)
            if(move > 0){
                double min = Math.min(rect.getMin().y, rect.getMax().y);
                double max = Math.max(body.getMin().y, body.getMax().y);
                double offset = min - max;
                if(offset >= 0 && move > offset)
                    return offset;
            }else{
                double min = Math.min(body.getMin().y, body.getMax().y);
                double max = Math.max(rect.getMin().y, rect.getMax().y);
                double offset = max - min;
                if(offset <= 0 && move < offset)
                    return offset;
            }
        return move;
    }

}
