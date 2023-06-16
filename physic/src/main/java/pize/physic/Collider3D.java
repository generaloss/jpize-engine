package pize.physic;

import pize.math.vecmath.vector.Vec3d;

public class Collider3D{

    public static Vec3d getCollidedMotion(BoxBody body, Vec3d move, BoxBody... boxes){
        if(move.isZero())
            return move;

        body = body.clone();

        double x = move.x;
        for(BoxBody box: boxes)
            if(x != 0)
                x = distX(x, body, box);
        body.getPosition().x += x;

        double y = move.y;
        for(BoxBody box: boxes)
            if(y != 0)
                y = distY(y, body, box);
        body.getPosition().y += y;

        double z = move.z;
        for(BoxBody box: boxes)
            if(z != 0)
                z = distZ(z, body, box);
        body.getPosition().z += z;

        return new Vec3d(x, y, z);
    }


    private static double distX(double move, BoxBody body, BoxBody box){
        if(move == 0)
            return 0;
        if(box.getMax().y > body.getMin().y && box.getMin().y < body.getMax().y && box.getMax().z > body.getMin().z && box.getMin().z < body.getMax().z)
            if(move > 0){
                double min = Math.min(box.getMin().x, box.getMax().x);
                double max = Math.max(body.getMin().x, body.getMax().x);
                double offset = min - max;
                if(offset >= 0 && move > offset)
                    return offset;
            }else{
                double min = Math.min(body.getMin().x, body.getMax().x);
                double max = Math.max(box.getMin().x, box.getMax().x);
                double offset = max - min;
                if(offset <= 0 && move < offset)
                    return offset;
            }
        return move;
    }
    
    private static double distY(double move, BoxBody body, BoxBody box){
        if(move == 0)
            return 0;
        if(box.getMax().x > body.getMin().x && box.getMin().x < body.getMax().x && box.getMax().z > body.getMin().z && box.getMin().z < body.getMax().z)
            if(move > 0){
                double min = Math.min(box.getMin().y, box.getMax().y);
                double max = Math.max(body.getMin().y, body.getMax().y);
                double offset = min - max;
                if(offset >= 0 && move > offset)
                    return offset;
            }else{
                double min = Math.min(body.getMin().y, body.getMax().y);
                double max = Math.max(box.getMin().y, box.getMax().y);
                double offset = max - min;
                if(offset <= 0 && move < offset)
                    return offset;
            }
        return move;
    }
    
    private static double distZ(double move, BoxBody body, BoxBody box){
        if(move == 0)
            return 0;
        if(box.getMax().x > body.getMin().x && box.getMin().x < body.getMax().x && box.getMax().y > body.getMin().y && box.getMin().y < body.getMax().y)
            if(move > 0){
                double min = Math.min(box.getMin().z, box.getMax().z);
                double max = Math.max(body.getMin().z, body.getMax().z);
                double offset = min - max;
                if(offset >= 0 && move > offset)
                    return offset;
            }else{
                double min = Math.min(body.getMin().z, body.getMax().z);
                double max = Math.max(box.getMin().z, box.getMax().z);
                double offset = max - min;
                if(offset <= 0 && move < offset)
                    return offset;
            }
        return move;
    }

}